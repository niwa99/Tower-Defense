package de.dhbw.map.objects.tower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.game.util.Position;
import de.dhbw.game.util.SortingUtil;

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
	
	public Enemy getNearestEnemy(List<Enemy> enemies) {
		Map<Enemy, Integer> distanceToEnemy =  new HashMap<Enemy, Integer>();
		
		for (Enemy enemy : enemies) {
			int distance = getDistance(enemy.getPositionX(), enemy.getPositionY());
			distanceToEnemy.put(enemy, distance);
		}
		
		List<Enemy> sortedByDistance = sortingUtil.getSortedListBySortingMapByValue(distanceToEnemy);
		
		for (Enemy enemy : sortedByDistance) {
			if(enemy.isAlive()) {
				return enemy;
			}
		}
		
		return null;
	}
	
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
}
