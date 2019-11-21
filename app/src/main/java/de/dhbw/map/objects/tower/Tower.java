package de.dhbw.map.objects.tower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;
import de.dhbw.util.SortingUtil;

public abstract class Tower {
	private SortingUtil sortingUtil = new SortingUtil();
	
	private String label;
	private UUID id;
	private int costs;
	private int damage;
	private int range;
	private int fireRate;
	private int x;
	private int y;
	private TimerTask task;
	
	public Tower(String label, UUID id, int costs, int damage, int range, int fireRate, Position pos) {
		this.label=label;
		this.id=id;
		this.costs=costs;
		this.damage=damage;
		this.range=range;
		this.fireRate=fireRate;
		this.x=pos.getX();
		this.y=pos.getY();
	}

	/**
	 * The tower shoots the nearest enemy if it is in range
	 * @param enemies
	 */
	public boolean fire(List<Enemy> enemies) {
		List<Enemy> enemiesInRange = enemies.stream().filter(e -> isEnemyInRange(e)).collect(Collectors.toList());
		Enemy enemy = getNearestEnemy(enemiesInRange);
		if(enemy!=null) {
			enemy.reduceHealthPoints(damage);
			System.out.println(enemy.getLabel() + " was shot by " + label + " and has " + enemy.getHealthPoints() + " hp left");
			return true;
		}
		return false;
	}
	
	public boolean isEnemyInRange(Enemy enemy) {	
		return getDistance(enemy.getPositionX(), enemy.getPositionY())<range;
	}

	public TimerTask getTask(){
		return task;
	}

	public void setTask(TimerTask task){
		this.task=task;
	}

	/**
	 * All enemies are saved in a map with their actual distance to the tower.
	 * The map is sorted by distance, so the nearest enemy is on the first place of the map. We don't have to care about enemies with the same distance.
	 * @param enemies
	 * @return
	 */
	public Enemy getNearestEnemy(List<Enemy> enemies) {
		if(!enemies.isEmpty()) {
			Map<Enemy, Integer> distanceToEnemy = new HashMap<Enemy, Integer>();

			for (Enemy enemy : enemies) {
				int distance = getDistance(enemy.getPositionX(), enemy.getPositionY());
				distanceToEnemy.put(enemy, distance);
			}

			List<Enemy> sortedByDistance = sortingUtil.getSortedListBySortingMapByValue(distanceToEnemy);

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
	public float rotateImage(List<Enemy> enemies){

		float rotation = 0;
		Enemy enemy = getNearestEnemy(enemies);
		if (enemy != null){
			if ((this.getPositionX() - enemy.getPositionX())< 0){
				if((this.getPositionY() - enemy.getPositionY()) < 0){
					rotation = 225;
				}else if((this.getPositionY() - enemy.getPositionY()) == 0){
					rotation = 180;
				}else{
					rotation = 135;
				}
			}else if((this.getPositionX() - enemy.getPositionX())== 0){
				if((this.getPositionY() - enemy.getPositionY()) < 0) {
					rotation = 270;
				}else{
					rotation = 90;
				}
			}else{
				if((this.getPositionY() - enemy.getPositionY()) < 0){
					rotation = 315;
				}else if((this.getPositionY() - enemy.getPositionY()) == 0){
					rotation = 00;
				}else{
					rotation = 45;
				}
			}
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
		return new Position(x,y);
	}

}
