package de.dhbw.map.matchfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;
import de.dhbw.util.ObjectStorage;
import de.dhbw.util.Position;

import static de.dhbw.util.ObjectStorage.*;

public class MatchField {
	private List<Enemy> enemies;
	private List<Tower> towers;
	private boolean isGameOver = false;
	private Timer waveTimer = new Timer();
	
	public MatchField() {
		enemies = new ArrayList<>();
		towers = new ArrayList<>();
	}
	
	public void addTower(Tower tower) {
		towers.add(tower);
		startTowerFire(tower);
	}
	
	public void addEnemy(Enemy enemy) {
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
						enemy.move(getMapStructure());
						if (isGameOver){
							stopGame();
						}
					}else if(enemy.reachedTarget()){
						if(!getGame().decreaseLifePoints(enemy.getLifePointsCosts())){
							isGameOver=true;
						}
						enemy.getTimerTask().cancel();
					}
				}
			};
			enemy.setTimerTask(timerTask);
			waveTimer.scheduleAtFixedRate(timerTask, 0, 1000-enemy.getSpeed());
	}

	/**
	 * all towers are shooting in an own thread. The speed is defined by the time this task is repeated.
	 * int fireRate defines how much seconds the tower sleeps between two shoots
	 */
	public void startTowerFire(Tower tower) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					if (enemies.size() > 0){
						tower.fire(enemies);
					} else {
						if(getGame().isGameOver()){
							waveTimer.cancel();
							System.out.println("No enemies left, towers are going to sleep");
						}
					}
				}
			};
			tower.setTask(timerTask);
			waveTimer.scheduleAtFixedRate(timerTask, 1000, tower.getFireRate()*1000);
	}
	
	
	public void removeDeadEnemies() {
		List<Enemy> deadEnemies = enemies.stream().filter(enemy -> !enemy.isAlive()).collect(Collectors.toList());
		for (Enemy enemy : deadEnemies) {
			if (enemy instanceof Tank) {
				getGameActivity().runOnUiThread(() -> getMapLayout().removeView(((Tank) enemy).getTankImage()));
			}
			ObjectStorage.getGame().addMoney(enemy.getValue());
			enemy.getTimerTask().cancel();
			System.out.println(enemy.getLabel() + " is dead now");
		}
		enemies.removeAll(deadEnemies);
	}
	
	private void stopGame() {
		getGame().stop();
		waveTimer.cancel();
		System.out.println("Game is over, all enemies reached the target");
	}

	private Optional<Tower> getTower(UUID towerUUID) {
		return towers.stream().filter(tower -> tower.getId() == towerUUID).findAny();
	}

	public Optional<Tower> getTower(Field field){
		return towers.stream().filter(t -> t.getField().equals(field)).findAny();
	}

	private Optional<Enemy> getEnemy(UUID enemyUUID) {
		return enemies.stream().filter(enemy -> enemy.getUuid() == enemyUUID).findAny();
	}

	private List<UUID> getEnemyUUIDs(){
		return enemies.stream().map(Enemy::getUuid).collect(Collectors.toList());
	}

	public void removeTower(Tower tower){
		towers.remove(tower);
		tower.getTask().cancel();
	}

	//not in use yet
	private boolean removeTower(UUID towerUUID) {
		Optional<Tower> tower = getTower(towerUUID);
		if (tower.isPresent()) {
			towers.remove(tower.get());
			return true;
		}
		return false;
	}

	//not in use yet
	private boolean removeEnemy(UUID enemyUUID) {
		Optional<Enemy> enemy = getEnemy(enemyUUID);
		if (enemy.isPresent()) {
			enemies.remove(enemy.get());
			return true;
		}
		return false;
	}
}