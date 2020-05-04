package de.dhbw.map.matchfield;

import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Game;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.MapStructure;

public class MatchField {
	private List<Enemy> enemies;
	private List<Tower> towers;
	private boolean isGameOver = false;
	private Timer waveTimer = new Timer();
	private final Game game;
	private final MapStructure mapStructure;
	private final GameActivity gameActivity;
	private final FrameLayout mapLayout;
	
	public MatchField(Game game, MapStructure mapStructure, GameActivity gameActivity, FrameLayout mapLayout) {
		this.game = game;
		this.mapStructure = mapStructure;
		this.gameActivity = gameActivity;
		this.mapLayout = mapLayout;
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
						enemy.move(mapStructure);
						if (isGameOver){
							lose();
						}
					}else if(enemy.reachedTarget()){
						removeEnemiesInTarget(enemy);
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
					}
				}
			};
			tower.setTask(timerTask);
			waveTimer.scheduleAtFixedRate(timerTask, 1000, tower.getFireRate()*1000);
	}
	
	
	public void removeDeadEnemy(Enemy enemy) {
		if (!enemy.isAlive()) {
			removeImageViewOfEnemy(enemy);
			game.addMoney(enemy.getValue());
			enemy.getTimerTask().cancel();
			enemies.remove(enemy);
			System.out.println(enemy.getLabel() + " is dead now");
		}
	}

	public void removeEnemiesInTarget(Enemy enemy) {
		if(!game.decreaseLifePoints(enemy.getLifePointsCosts())){
			isGameOver=true;
		}
		removeImageViewOfEnemy(enemy);
		enemy.getTimerTask().cancel();
		enemies.remove(enemy);
		System.out.println(enemy.getLabel() + " reached the target");
	}

	private void removeImageViewOfEnemy(Enemy enemy) {
		if (enemies.size() <= 1 && game.allEnemiesSpawned()) {
			win();
		}
		switch (enemy.getType()) {
			case TANK: gameActivity.runOnUiThread(() -> mapLayout.removeView(((Tank) enemy).getTankImage()));
		}
	}
	
	public void stopGame() {
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

	public void lose() {
		stopGame();
		game.loseGame();
	}

	public void win() {
		stopGame();
		game.winGame();
	}
}