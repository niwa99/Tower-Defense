package de.dhbw.map.matchfield;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import android.os.Handler;
import java.util.stream.Collectors;
import java.util.Optional;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;
import de.dhbw.game.EnemyType;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.BossTank;
import de.dhbw.map.objects.enemy.Car;
import de.dhbw.map.objects.enemy.EnemyView;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.structure.Field;
import de.dhbw.util.AdvancedTimer;
import de.dhbw.util.ObjectType;
import de.dhbw.util.Position;
import pl.droidsonroids.gif.GifImageView;

public class MatchField {

	private final GameActivity gameActivity;
	private List<AEnemy> enemies;
	private List<ATower> towers;
	private Handler handler;

	//responsible for all enemy movements
	private AdvancedTimer matchFieldTimer = new AdvancedTimer();

	/**
	 * Constructor
	 * @param gameActivity
	 */
	public MatchField(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		handler=gameActivity.getHandler();
		enemies = new ArrayList<>();
		towers = new ArrayList<>();
	}

	/**
	 * Add a tower to the matchField.
	 * @param tower
	 */
	public void addTower(ATower tower) {
		ImageView baseImage = tower.getBaseImage();
		Optional<ImageView> headImage = tower.getHeadImage();

		GameActivity.runActionOnUI(handler, UIActions.addView, baseImage, ObjectType.TOWER);

		if(headImage.isPresent()){
			GameActivity.runActionOnUI(handler, UIActions.addView, headImage.get(), ObjectType.TOWER);
		}
		if(tower.getLevel()>1) {
			GameActivity.runActionOnUI(handler, UIActions.addView, tower.getStarlvlTwo(), ObjectType.TOWER);
		}
		if(tower.getLevel()>2) {
			GameActivity.runActionOnUI(handler, UIActions.addView, tower.getStarlvlThree(), ObjectType.TOWER);
		}
		if(tower.getLevel()>3) {
			GameActivity.runActionOnUI(handler, UIActions.addView, tower.getStarlvlFour(), ObjectType.TOWER);
		}
		towers.add(tower);
		startTowerFire(tower);
		gameActivity.getGame().increaseNumberOfBuiltTowers();
	}

	/**
	 * Add an enemy to the matchField.
	 * @param enemy
	 */
	public void addEnemy(AEnemy enemy) {
		EnemyView enemyView = enemy.getEnemyView();
		enemyView.setPosition(new Position(-500,-500));
		GameActivity.runActionOnUI(handler, UIActions.addView, enemyView.getLayout(), ObjectType.ENEMY);
		enemies.add(enemy);
		startEnemyMovement(enemy);

		if(enemy instanceof BossTank){
		    Car car = ((BossTank) enemy).getCar();
            EnemyView carView = car.getEnemyView();
			carView.setPosition(new Position(-500,-500));
			enemies.add(car);
			GameActivity.runActionOnUI(handler, UIActions.addView, carView.getLayout(), ObjectType.ENEMY);
		}
	}

	/**
	 * all enemies are moving in an own thread. The speed is defined by the time this task is repeated.
	 * An enemy moves only one pixel each time. Speed is 1 second - (int) speed of the enemy
	 */
	public void startEnemyMovement(AEnemy enemy) {
		if(matchFieldTimer.isCanceled()){
			return;
		}
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemy.isAlive() && !enemy.reachedTarget()) {
						enemy.move(gameActivity.getGame().getMapStructure());
					} else if (enemy.reachedTarget()) {
						removeEnemiesInTarget(enemy);
					} else if(!enemy.isAlive()){
						removeDeadEnemy(enemy);
					}

				}
			};
			enemy.setTimerTask(timerTask);
			matchFieldTimer.scheduleAtFixedRate(timerTask, 0, 1000 - enemy.getSpeed());
	}

	/**
	 * Run a slow-TimerTask if the enemy has a slowness effect.
	 * @param enemy
	 */
	public void slowEnemy(AEnemy enemy){
		if(matchFieldTimer.isCanceled()){
			return;
		}
		enemy.getTimerTask().cancel();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if (enemy.isAlive() && !enemy.reachedTarget()) {
					enemy.move(gameActivity.getGame().getMapStructure());
					if(enemy.isFullSpeed()){
						cancel();
						startEnemyMovement(enemy);
					}else{
						slowEnemy(enemy);
					}
				} else if (enemy.reachedTarget()) {
					removeEnemiesInTarget(enemy);
				} else if(!enemy.isAlive()){
					removeDeadEnemy(enemy);
				}
			}
		};
		enemy.setTimerTask(timerTask);
		matchFieldTimer.schedule(timerTask, (1000 - enemy.getSpeed()) + enemy.getAndReduceSlowness());
	}

	/**
	 * all towers are shooting in an own thread. The speed is defined by the time this task is repeated.
	 * int fireRate defines how much seconds the tower sleeps between two shoots
	 */
	public void startTowerFire(ATower tower) {
		if(matchFieldTimer.isCanceled()){
			return;
		}
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if (enemies.size() > 0) {
					tower.fire(new ArrayList<>(enemies));
					tower.setLastTimeActionMillis(System.currentTimeMillis());
				}else{
					checkForFinishedGame();
				}
			}
		};
		tower.setTask(timerTask);
		matchFieldTimer.scheduleAtFixedRate(timerTask, tower.getDelay(), tower.getFireRate() * 1000);
	}

	public void checkForFinishedGame(){
		if(gameActivity.getGame().allEnemiesSpawned()){
			stopTimer(true);
		}
	}


    /**
     * Pause the game timers.
     */
	public void pauseTimers(){
		final long time = System.currentTimeMillis();
		towers.stream().forEach(t -> t.calculateDelay(time));
		enemies.stream().forEach(e -> e.setPaused(true));
		stopTimer();
	}

	/**
	 * Continue the game timers.
	 */
	public void continueTimers(){
		matchFieldTimer = new AdvancedTimer();
		enemies.stream().forEach(e -> {
			if (e.getSlowness() == 0) {
				startEnemyMovement(e);
			} else {
				e.slowDown(e.getSlowness());
			}
			e.setPaused(false);
		});
		towers.stream().forEach(t -> startTowerFire(t));
	}

	/**
	 * Stop the matchField timer and call win-/lose-actions.
	 * @param isWinner
	 */
	public void stopTimer(boolean isWinner) {
		stopTimer();
		if (isWinner) {
			gameActivity.getGame().winActions();
		} else {
			gameActivity.getGame().loseActions();
		}
	}

	/**
	 * Stop the matchField timer (if game is stopped unregularly).
	 */
	public void stopTimer() {
		if(!matchFieldTimer.isCanceled()) {
			matchFieldTimer.cancel();
		}
	}

	/**
	 * Remove an enemy from the matchField and create explosion animation.
	 * @param enemy
	 */
	public void removeDeadEnemy(AEnemy enemy) {
		if (!enemy.isAlive()) {
			enemy.getTimerTask().cancel();
			removeImageViewOfEnemy(enemy);
			explode(enemy);
			spawnCarIfEnemyIsBossTank(enemy);
			gameActivity.getGame().addMoney(enemy.getValue());
			enemies.remove(enemy);
			gameActivity.getGame().increaseNumberOfEnemiesKilled();
			System.out.println(enemy.getLabel() + " is dead now");
		}
	}

	private void spawnCarIfEnemyIsBossTank(AEnemy enemy){
		if(enemy.getType()== EnemyType.BOSS_TANK){
			BossTank boss = (BossTank) enemy;
			Car car = boss.getCar();
			car.setHealthpoints(boss.getBossTankHealthpointsByLevel(boss.getLevel())/4);
			car.moveToPosition(enemy.getPosition());
			car.setProgress(enemy.getProgress()-2);
			startEnemyMovement(car);
		}
	}

	/**
	 * Remove enemies which reached the target.
	 * @param enemy
	 */
	public void removeEnemiesInTarget(AEnemy enemy) {
		removeImageViewOfEnemy(enemy);
		enemy.getTimerTask().cancel();
		enemies.remove(enemy);
		if (!gameActivity.getGame().decreaseLifePoints(enemy.getLifePointsCosts())) {
			stopTimer(false);
		}
		System.out.println(enemy.getLabel() + " reached the target");
	}

	/**
	 * Remove the imageView of an enemy.
	 * @param enemy
	 */
	private void removeImageViewOfEnemy(AEnemy enemy) {
		if (enemies.size() == 1 && gameActivity.getGame().allEnemiesSpawned()) {
			stopTimer(true);
		}
		GameActivity.runActionOnUI(handler, UIActions.removeView, enemy.getEnemyView().getLayout());
	}

	/**
	 * Create an explosion animation for an enemy.
	 * @param enemy
	 */
	private void explode(AEnemy enemy) {
		if(gameActivity.getGame().isAnimationOn()) {
			GifImageView gif = new GifImageView(gameActivity);
			gif.setLayoutParams(gameActivity.getMapFrameLayout().getLayoutParams());
			gif.setX(enemy.getPositionX() - Math.round(gameActivity.getResources().getDisplayMetrics().widthPixels/2.05));
			gif.setY(enemy.getPositionY() - Math.round(gameActivity.getResources().getDisplayMetrics().heightPixels/2.2));
			gif.setScaleX(0.2f);
			gif.setScaleY(0.2f);
			gif.setImageResource(R.drawable.explosion_gif);
			gif.setElevation(ImageElevation.ANIMATION.elevation);
			GameActivity.runActionOnUI(handler, UIActions.addView, gif, ObjectType.BULLET);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					GameActivity.runActionOnUI(handler, UIActions.removeView, gif);
					gif.clearAnimation();
				}
			}, 500);
		}
	}

	/**
	 * Get a tower by uuid.
	 * @param towerUUID
	 * @return tower
	 */
	private Optional<ATower> getTower(UUID towerUUID) {
		return towers.stream().filter(tower -> tower.getId() == towerUUID).findAny();
	}

	/**
	 * Get a tower by field.
	 * @param field
	 * @return tower
	 */
	public Optional<ATower> getTower(Field field) {
		return towers.stream().filter(t -> t.getField().equals(field)).findAny();
	}

	/**
	 * Remove a tower by uuid.
	 * @param towerUUID
	 * @return true if tower got removed
	 */
	private boolean removeTower(UUID towerUUID) {
		Optional<ATower> tower = getTower(towerUUID);
		if (tower.isPresent()) {
			towers.remove(tower.get());
			return true;
		}
		return false;
	}

	/**
	 * Remove a tower by tower object
	 * @param tower
	 */
	public void removeTower(ATower tower) {
		tower.getTask().cancel();
		towers.remove(tower);

		ImageView baseImage = tower.getBaseImage();
		Optional<ImageView> headImage = tower.getHeadImage();
		GameActivity.runActionOnUI(handler, UIActions.removeView, baseImage);
		if(headImage.isPresent()){
			GameActivity.runActionOnUI(handler, UIActions.removeView, headImage.get());
		}
		if(tower.getLevel()>1) {
			GameActivity.runActionOnUI(handler, UIActions.removeView, tower.getStarlvlTwo());
		}
		if(tower.getLevel()>2) {
			GameActivity.runActionOnUI(handler, UIActions.removeView, tower.getStarlvlThree());
		}
		if(tower.getLevel()>3) {
			GameActivity.runActionOnUI(handler, UIActions.removeView, tower.getStarlvlFour());
		}
	}

	/**
	 * Get an enemy by uuid.
	 * @param enemyUUID
	 * @return enemy
	 */
	private Optional<AEnemy> getEnemy(UUID enemyUUID) {
		return enemies.stream().filter(enemy -> enemy.getUuid() == enemyUUID).findAny();
	}

	/**
	 * Get the uuids of all enemies as list.
	 * @return uuids as list
	 */
	private List<UUID> getEnemyUUIDs() {
		return enemies.stream().map(AEnemy::getUuid).collect(Collectors.toList());
	}

	/**
	 * Remove an enemy by uuid.
	 * @param enemyUUID
	 * @return true if enemy got removed
	 */
	private boolean removeEnemy(UUID enemyUUID) {
		Optional<AEnemy> enemy = getEnemy(enemyUUID);
		if (enemy.isPresent()) {
			enemies.remove(enemy.get());
			return true;
		}
		return false;
	}

	/**
	 * Calculate the distance between two points (x1, y1) and (x2, y2)
	 * @param firstX
	 * @param firstY
	 * @param secondX
	 * @param secondY
	 * @return the distance in px
	 */
	public static int getDistance(int firstX, int firstY, int secondX, int secondY) {
		return (int) Math.round(Math.sqrt(Math.pow(Math.abs(firstX-secondX), 2) + Math.pow(Math.abs(firstY-secondY), 2)));
	}
}