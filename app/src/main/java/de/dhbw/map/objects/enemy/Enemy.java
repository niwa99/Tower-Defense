package de.dhbw.map.objects.enemy;

import java.util.UUID;

import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Direction;
import de.dhbw.util.Position;
import java.util.TimerTask;

public abstract class Enemy {
	private String label;
	private UUID uuid;
	private int value;
	private int lifePointsCosts;
	private int healthpoints;
	private int progress = 0;
	private int speed;
	private int x;
	private int y;
	private boolean isAlive = true;
	private boolean reachedTarget = false;
	private Direction direction = Direction.RIGHT;
	private TimerTask timerTask;
	private Field actualField;
	
	public Enemy(String label, UUID uuid, int hp, int speed, int value, int lifePointsCosts) {
		this.label = label;
		this.uuid = uuid;
		this.healthpoints = hp;
		this.speed = speed;
		this.value=value;
		this.lifePointsCosts = lifePointsCosts;
	}

	public int getValue(){
		return value;
	}

	public int getLifePointsCosts(){
		return lifePointsCosts;
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(TimerTask task) {
		this.timerTask = task;
	}
	
	public int getHealthPoints() {
		return healthpoints;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void reduceHealthPoints(int damage) {
		if (damage >= healthpoints) {
			healthpoints = 0;
			isAlive = false;
		} else {
			healthpoints -= damage;
		}
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Moves an enemy on the first call to the spawn position.
	 * All next calls are moving the enemy one pixel in the direction of the spawnpoint from the next Field on the path
	 * @param map
	 * @return false when the enemy reached the target
	 */
	public boolean move(MapStructure map) {
		if (actualField == null) {
			actualField = map.getFieldForEnemy(progress);
			progress++;
			moveToPosition(actualField.getSpawnPoint());
			return true;
		}
		if (actualField.getSpawnPoint().equals(getPosition())) {
			actualField = map.getFieldForEnemy(progress);
			progress++;
			if (actualField != null) {
				System.out.println(label + " is moving to a new field [" + actualField.getFieldPositionX() + actualField.getFieldPositionY() + "]");
			}
		}
		
		if (actualField != null) {
			Position pos = actualField.getSpawnPoint();
			if (pos.getX()-x < 0) {
				moveTo(x-1, y);
				direction = Direction.LEFT;
			} else if(pos.getX()-x > 0){
				moveTo(x+1, y);
				direction = Direction.RIGHT;
			} else if(pos.getY()-y < 0) {
				moveTo(x, y-1);
				direction = Direction.UP;
			} else {
				moveTo(x, y+1);
				direction = Direction.DOWN;
			}
			return true;
		}
		System.out.println(label + " reached the target");
		reachedTarget = true;
		return false;
	}

	/**
	 * The target is reached when an enemy reached the Spawnpoint of the last Field on the path
	 * @return true if target is reached
	 */
	public boolean reachedTarget() {
		return reachedTarget;
	}
	
	public Position getPosition() {
		return new Position(x, y);
	}
	
	public void moveToPosition(Position pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String getLabel() {
		return label;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public int getPositionX() {
		return x;
	}
	
	public int getPositionY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}
}
