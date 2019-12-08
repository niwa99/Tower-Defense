package de.dhbw.map.objects.tower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.lang.Math;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.util.Position;
import de.dhbw.util.SortingUtil;
import de.dhbw.map.structure.Field;

public abstract class Tower {
	private SortingUtil sortingUtil = new SortingUtil();
	
	private String label;
	private UUID id;
	private int costs;
	protected int damage;
	private int range;
	private int fireRate;
	private int x;
	private int y;
	private Field field;
	private TimerTask task;
	protected Enemy targetedEnemy;
	
	public Tower(String label, UUID id, int costs, int damage, int range, int fireRate, Field field) {
		this.label = label;
		this.id = id;
		this.costs = costs;
		this.damage = damage;
		this.range = range;
		this.fireRate = fireRate;
		this.field = field;
		this.x = field.getSpawnPoint().getX();
		this.y = field.getSpawnPoint().getY();

	}

	/**
	 * The tower shoots the nearest enemy if it is in range
	 * @param enemies
	 * @return true if there is an enemy to shoot at
	 */
	public boolean fire(List<Enemy> enemies) {
		List<Enemy> enemiesInRange = enemies.stream().filter(enemy -> isEnemyInRange(enemy)).collect(Collectors.toList());
		Enemy enemy = getNearestEnemy(enemiesInRange);
		if (enemy != null) {
			if (enemy instanceof Tank) {
				targetedEnemy = enemy;
				return true;
			}
		}
		return false;
	}
	
	public boolean isEnemyInRange(Enemy enemy) {	
		return getDistance(enemy.getPositionX(), enemy.getPositionY()) < range;
	}

	public Field getField(){
		return field;
	}

	public TimerTask getTask(){
		return task;
	}

	public void setTask(TimerTask task){
		this.task = task;
	}

	/**
	 * All enemies are saved in a map with their actual distance to the tower.
	 * The map is sorted by distance, so the nearest enemy is on the first place of the map. We don't have to care about enemies with the same distance.
	 * @param enemies
	 * @return
	 */
	public Enemy getNearestEnemy(List<Enemy> enemies) {
		if (!enemies.isEmpty()) {
			Map<Enemy, Integer> distanceToEnemy = new HashMap<Enemy, Integer>();

			for (Enemy enemy : enemies) {
				int distance = getDistance(enemy.getPositionX(), enemy.getPositionY());
				distanceToEnemy.put(enemy, distance);
			}

			List<Enemy> sortedByDistance = SortingUtil.getSortedListBySortingMapByValue(distanceToEnemy);

			for (Enemy enemy : sortedByDistance) {
				if (enemy.isAlive()) {
					return enemy;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the number necessary to rotate the tower image according to the targeted enemy
	 * @return
	 */
	public double rotateImage(List<Enemy> enemies){
		double rotation = 0;
		Enemy enemy = getNearestEnemy(enemies);
		if (enemy != null){
			double a = this.getPositionX() - enemy.getPositionX();
			double b = this.getPositionY() - enemy.getPositionY();
			rotation = Math.toDegrees(Math.atan2(b, a));

		}
		return rotation;
	}

	/**
	 *
	 * @param xEnemy
	 * @param yEnemy
	 * @return distance between Enemy or other Object and Tower
	 */
	public int getDistance(int xEnemy, int yEnemy) {
		return (int) Math.round(Math.sqrt(Math.pow(Math.abs(xEnemy-x), 2) + Math.pow(Math.abs(yEnemy-y), 2)));
	}

	public String getLabel() {
		return label;
	}

	public UUID getId() {
		return id;
	}

	public int getCosts() {
		return costs;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public int getFireRate() {
		return fireRate;
	}
	
	public int getPositionX() {
		return x;
	}
	
	public int getPositionY() {
		return y;
	}

	public Position getPosition(){
		return new Position(x, y);
	}
}