package de.dhbw.map.matchfield;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.structure.Field;
import pl.droidsonroids.gif.GifImageView;

public class MatchField {

	private final GameActivity gameActivity;
	private List<AEnemy> enemies;
	private List<ATower> towers;

	private boolean isGameOver = false;

	//responsible for all enemy movements
	private Timer matchFieldTimer = new Timer();
	
	public MatchField(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		enemies = new ArrayList<>();
		towers = new ArrayList<>();
	}
	
	public void addTower(ATower tower) {
		ImageView baseImage = tower.getBaseImage();
		Optional<ImageView> headImage = tower.getHeadImage();
		gameActivity.runOnUiThread(() -> {
			gameActivity.getMapFrameLayout().addView(baseImage);
			if(headImage.isPresent()){
				gameActivity.getMapFrameLayout().addView(headImage.get());

			}
			if(tower.getLevel()>1) {
				gameActivity.getMapFrameLayout().addView(tower.getStarlvlTwo());
			}
			if(tower.getLevel()>2) {
				gameActivity.getMapFrameLayout().addView(tower.getStarlvlThree());
			}
		});
		towers.add(tower);
		startTowerFire(tower);
		gameActivity.getGame().increaseNumberOfBuiltTowers();
	}
	
	public void addEnemy(AEnemy enemy) {
		gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().addView(enemy.getImage()));
		enemies.add(enemy);
		startEnemyMovement(enemy);
	}

	/**
	 * all enemies are moving in an own thread. The speed is defined by the time this task is repeated.
	 * An enemy moves only one pixel each time. Speed is 1 second - (int) speed of the enemy
	 */
	public void startEnemyMovement(AEnemy enemy) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemy.isAlive() && !enemy.reachedTarget()) {
						enemy.move(gameActivity.getGame().getMapStructure());
						if (isGameOver) {
							stopTimer(false);
						}
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

	public void slowEnemy(AEnemy enemy){
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
					if (isGameOver) {
						stopTimer(false);
					}
				} else if (enemy.reachedTarget()) {
					removeEnemiesInTarget(enemy);
				} else if(!enemy.isAlive()){
					removeDeadEnemy(enemy);
				}
			}
		};
		enemy.setTimerTask(timerTask);
		matchFieldTimer.schedule(timerTask, (1000 - enemy.getSpeed())+enemy.getSlowness());
	}

	/**
	 * all towers are shooting in an own thread. The speed is defined by the time this task is repeated.
	 * int fireRate defines how much seconds the tower sleeps between two shoots
	 */
	public void startTowerFire(ATower tower) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemies.size() > 0) {
						tower.fire(enemies);
						tower.setLastTimeActionMillis(System.currentTimeMillis());
					}
				}
			};
			tower.setTask(timerTask);
			matchFieldTimer.scheduleAtFixedRate(timerTask, tower.getDelay(), tower.getFireRate() * 1000);
	}

	public void pauseTimers(){
		final long time = System.currentTimeMillis();
		towers.stream().forEach(t -> t.calculateDelay(time));
		matchFieldTimer.cancel();
	}

	public void continueTimers(){
		matchFieldTimer = new Timer();
		enemies.stream().forEach(e -> startEnemyMovement(e));
		towers.stream().forEach(t -> startTowerFire(t));
	}

	public void stopTimer(boolean isWinner) {
		stopTimer();
		if (isWinner) {
			gameActivity.getGame().winActions();
		} else {
			gameActivity.getGame().loseActions();
		}
	}

	public void stopTimer() {
		matchFieldTimer.cancel();
		System.out.println("Game is over");
	}

	public void removeDeadEnemy(AEnemy enemy) {
		if (!enemy.isAlive()) {
			enemy.getTimerTask().cancel();
			removeImageViewOfEnemy(enemy);
			explode(enemy);
			gameActivity.getGame().addMoney(enemy.getValue());
			enemies.remove(enemy);
			gameActivity.getGame().increaseNumberOfEnemiesKilled();
			System.out.println(enemy.getLabel() + " is dead now");
		}
	}

	public void removeEnemiesInTarget(AEnemy enemy) {
		if (!gameActivity.getGame().decreaseLifePoints(enemy.getLifePointsCosts())) {
			isGameOver = true;
		}
		removeImageViewOfEnemy(enemy);
		enemy.getTimerTask().cancel();
		enemies.remove(enemy);
		System.out.println(enemy.getLabel() + " reached the target");
	}

	private void removeImageViewOfEnemy(AEnemy enemy) {
		if (enemies.size() == 1 && gameActivity.getGame().allEnemiesSpawned()) {
			stopTimer(true);
		}
		switch (enemy.getType()) {
			case TANK: gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().removeView( enemy.getImage()));
		}
	}

	private void explode(AEnemy enemy) {
		if(gameActivity.getGame().isAnimationOn()) {
			GifImageView gif = new GifImageView(gameActivity);
			gif.setLayoutParams(gameActivity.getMapFrameLayout().getLayoutParams());
			gif.setX(enemy.getImage().getX() - Math.round(gameActivity.getResources().getDisplayMetrics().widthPixels/2.2));
			gif.setY(enemy.getImage().getY() - Math.round(gameActivity.getResources().getDisplayMetrics().heightPixels/2.2));
			gif.setScaleX(0.2f);
			gif.setScaleY(0.2f);
			gif.setImageResource(R.drawable.explosion_gif);
			gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().addView(gif));
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().removeView(gif));
				}
			}, 500);
		}
	}

	private Optional<ATower> getTower(UUID towerUUID) {
		return towers.stream().filter(tower -> tower.getId() == towerUUID).findAny();
	}

	public Optional<ATower> getTower(Field field) {
		return towers.stream().filter(t -> t.getField().equals(field)).findAny();
	}

	private boolean removeTower(UUID towerUUID) {
		Optional<ATower> tower = getTower(towerUUID);
		if (tower.isPresent()) {
			towers.remove(tower.get());
			return true;
		}
		return false;
	}

	public void removeTower(ATower tower) {
		tower.getTask().cancel();
		towers.remove(tower);

		ImageView baseImage = tower.getBaseImage();
		Optional<ImageView> headImage = tower.getHeadImage();
		gameActivity.runOnUiThread(() -> {
			gameActivity.getMapFrameLayout().removeView(baseImage);
			if(headImage.isPresent()){
				gameActivity.getMapFrameLayout().removeView(headImage.get());

			}
		});
		if(tower.getLevel()>1) {
			gameActivity.getMapFrameLayout().removeView(tower.getStarlvlTwo());
		}
		if(tower.getLevel()>2) {
			gameActivity.getMapFrameLayout().removeView(tower.getStarlvlThree());
		}
	}

	private Optional<AEnemy> getEnemy(UUID enemyUUID) {
		return enemies.stream().filter(enemy -> enemy.getUuid() == enemyUUID).findAny();
	}

	private List<UUID> getEnemyUUIDs() {
		return enemies.stream().map(AEnemy::getUuid).collect(Collectors.toList());
	}

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