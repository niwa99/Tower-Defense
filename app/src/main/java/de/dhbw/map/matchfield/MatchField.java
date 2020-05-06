package de.dhbw.map.matchfield;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;

public class MatchField {

	private final GameActivity gameActivity;

	private List<Enemy> enemies;
	private List<Tower> towers;

	private boolean isGameOver = false;

	//responsible for all enemy movements
	private Timer enemiesTimer = new Timer();
	
	public MatchField(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		enemies = new ArrayList<>();
		towers = new ArrayList<>();
	}
	
	public void addTower(Tower tower) {
		towers.add(tower);
		startTowerFire(tower);
		gameActivity.getGame().increaseNumberOfBuiltTowers(1);
	}
	
	public void addEnemy(Enemy enemy) {
		gameActivity.runOnUiThread(() -> enemy.getImage().setVisibility(View.VISIBLE));
		enemies.add(enemy);
		startEnemyMovement(enemy);
	}

	/**
	 * all enemies are moving in an own thread. The speed is defined by the time this task is repeated.
	 * An enemy moves only one pixel each time. Speed is 1 second - (int) speed of the enemy
	 */
	public void startEnemyMovement(Enemy enemy) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemy.isAlive() && !enemy.reachedTarget()) {
						enemy.move(gameActivity.getGame().getMapStructure());
						if (isGameOver) {
							stopActing(false);
						}
					} else if(enemy.reachedTarget()) {
						removeEnemiesInTarget(enemy);
					}

				}
			};
			enemy.setTimerTask(timerTask);
			enemiesTimer.scheduleAtFixedRate(timerTask, 0, 1000 - enemy.getSpeed());
	}

	/**
	 * all towers are shooting in an own thread. The speed is defined by the time this task is repeated.
	 * int fireRate defines how much seconds the tower sleeps between two shoots
	 */
	public void startTowerFire(Tower tower) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemies.size() > 0) {
						tower.fire(enemies);
					}
				}
			};
			tower.setTask(timerTask);
			enemiesTimer.scheduleAtFixedRate(timerTask, 1000, tower.getFireRate() * 1000);
	}

	public void stopActing(boolean isWinner) {
		enemiesTimer.cancel();
		System.out.println("Game is over");
		if (isWinner) {
			gameActivity.getGame().winActions();
		} else {
			gameActivity.getGame().loseActions();
		}
	}

	public void stopActing() {
		enemiesTimer.cancel();
		System.out.println("Game is over");
	}

	public void removeDeadEnemy(Enemy enemy) {
		if (!enemy.isAlive()) {
			removeImageViewOfEnemy(enemy);
			gameActivity.getGame().addMoney(enemy.getValue());
			enemy.getTimerTask().cancel();
			enemies.remove(enemy);
			gameActivity.getGame().increaseNumberOfEnemiesKilled(1);
			System.out.println(enemy.getLabel() + " is dead now");
		}
	}

	public void removeEnemiesInTarget(Enemy enemy) {
		if (!gameActivity.getGame().decreaseLifePoints(enemy.getLifePointsCosts())){
			isGameOver = true;
		}
		removeImageViewOfEnemy(enemy);
		enemy.getTimerTask().cancel();
		enemies.remove(enemy);
		System.out.println(enemy.getLabel() + " reached the target");
	}

	private void removeImageViewOfEnemy(Enemy enemy) {
		if (enemies.size() == 1 && gameActivity.getGame().allEnemiesSpawned()) {
			stopActing(true);
		}
		switch (enemy.getType()) {
			case TANK: gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().removeView( enemy.getImage()));
		}
	}

	private Optional<Tower> getTower(UUID towerUUID) {
		return towers.stream().filter(tower -> tower.getId() == towerUUID).findAny();
	}

	public Optional<Tower> getTower(Field field){
		return towers.stream().filter(t -> t.getField().equals(field)).findAny();
	}

	private boolean removeTower(UUID towerUUID) {
		Optional<Tower> tower = getTower(towerUUID);
		if (tower.isPresent()) {
			towers.remove(tower.get());
			return true;
		}
		return false;
	}

	public void removeTower(Tower tower) {
		towers.remove(tower);
		tower.getTask().cancel();
	}

	private Optional<Enemy> getEnemy(UUID enemyUUID) {
		return enemies.stream().filter(enemy -> enemy.getUuid() == enemyUUID).findAny();
	}

	private List<UUID> getEnemyUUIDs() {
		return enemies.stream().map(Enemy::getUuid).collect(Collectors.toList());
	}

	private boolean removeEnemy(UUID enemyUUID) {
		Optional<Enemy> enemy = getEnemy(enemyUUID);
		if (enemy.isPresent()) {
			enemies.remove(enemy.get());
			return true;
		}
		return false;
	}
}