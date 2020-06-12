package de.dhbw.map.objects.enemy;

import android.text.Layout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.UUID;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Direction;
import de.dhbw.util.Position;
import java.util.TimerTask;

import static de.dhbw.util.Constants.DRAWABLE_PLANE;
import static de.dhbw.util.Constants.FIELD_SIZE;

public abstract class AEnemy {
	private String label;
	private UUID uuid;
	private boolean isPaused = false;
	private int value;
	private int lifePointsCosts;
	private int healthpoints;
	private int progress = 0;
	private int movedSteps = 0;
	private int speed;
	private int x;
	private int y;
	private int slowness;
	private boolean isAlive = true;
	private boolean reachedTarget = false;
	private Direction direction = Direction.RIGHT;
	private TimerTask timerTask;
	protected Field actualField;
	private final EnemyType enemyType;
	protected GameActivity gameActivity;

	private final EnemyView enemyView;

	/**
	 * Constructor
	 * @param label
	 * @param uuid
	 * @param hp
	 * @param speed
	 * @param value
	 * @param lifePointsCosts
	 * @param gameActivity
	 * @param enemyType
	 * @param enemyImageID
	 * @param enemyHitImageID
	 */
	public AEnemy(String label, UUID uuid, int hp, int speed, int value, int lifePointsCosts, GameActivity gameActivity, EnemyType enemyType, int enemyImageID, int enemyHitImageID) {
		this.label = label;
		this.uuid = uuid;
		this.healthpoints = hp;
		this.speed = speed;
		this.value = value;
		this.lifePointsCosts = lifePointsCosts;
		this.gameActivity = gameActivity;
		this.enemyType = enemyType;
		RelativeLayout enemyLayout = (RelativeLayout) gameActivity.getLayoutInflater().inflate(R.layout.enemy_layout, null);
		this.enemyView = new EnemyView(enemyLayout, gameActivity.getUiHandler(), enemyImageID, enemyHitImageID, hp);
	}

	/**
	 *
	 * Constructor only for testing purposes
	 */
	public AEnemy(String label, UUID uuid, int hp, int speed, int value, int lifePointsCosts, GameActivity gameActivity, EnemyType enemyType, EnemyView enemyView) {
		this.label = label;
		this.uuid = uuid;
		this.healthpoints = hp;
		this.speed = speed;
		this.value = value;
		this.lifePointsCosts = lifePointsCosts;
		this.gameActivity = gameActivity;
		this.enemyType = enemyType;
		this.enemyView = enemyView;
	}

	/**
	 *
	 * @return value of this enemy
	 */
	public int getValue() {
		return value;
	}

	/**
	 *
	 * @return lifepointcosts of this enemy
	 */
	public int getLifePointsCosts() {
		return lifePointsCosts;
	}

	/**
	 *
	 * @return timertask of this enemy
	 */
	public TimerTask getTimerTask() {
		return timerTask;
	}

	/**
	 * Set the timertask for this enemy.
	 * @param task
	 */
	public void setTimerTask(TimerTask task) {
		this.timerTask = task;
	}

	/**
	 *
	 * @return healthpoints of this enemy
	 */
	public int getHealthPoints() {
		return healthpoints;
	}

	/**
	 * Set the healthpoints for this enemy.
	 * @param healthpoints
	 */
	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}

	/**
	 *
	 * @return speed of this enemy
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 *
	 * @return type of this enemy
	 */
	public EnemyType getType() {
		return enemyType;
	}

	/**
	 *
	 * @return image of this enemy
	 */
	public EnemyView getEnemyView() {
		return enemyView;
	}

	/**
	 * Reduce the healthpoints by the specified damage.
	 * If the healthpoints would be <= 0 afterwards, the isAlive attribute is set to false.
	 * @param damage
	 */
	public void reduceHealthPoints(int damage) {
		if (damage >= healthpoints) {
			healthpoints = 0;
			isAlive = false;
		} else {
			healthpoints -= damage;
			enemyView.hitAnimation();
		}
		enemyView.setHealthProgress(getHealthPoints());
	}

	/**
	 *
	 * @return true if the enemy is alive
	 */
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
			actualField = getEnemyField(map);
			progress++;
			moveToPosition(actualField.getSpawnPoint());
			return true;
		}
		if (actualField.getSpawnPoint().equals(getPosition())) {
			actualField = getEnemyField(map);
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
			enemyView.resolveRotation(getDirection(), x, y);
			return true;
		}
		System.out.println(label + " reached the target");
		reachedTarget = true;
		return false;
	}

	protected Field getEnemyField(MapStructure map){
		return map.getFieldForEnemy(progress);
	}

	/**
	 * The target is reached when an enemy reached the Spawnpoint of the last Field on the path
	 * @return true if target is reached
	 */
	public boolean reachedTarget() {
		return reachedTarget;
	}

	/**
	 *
	 * @return position of this enemy in px
	 */
	public Position getPosition() {
		return new Position(x, y);
	}

	/**
	 * Move the enemy to the specified position (in px).
	 * @param pos
	 */
	public void moveToPosition(Position pos) {
		moveTo(pos.getX(), pos.getY());
	}

	/**
	 * Move the enemy to the specified coordinates (in px).
	 * @param x
	 * @param y
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		movedSteps++;
	}

	/**
	 *
	 * @return label of this enemy
	 */
	public String getLabel() {
		return label;
	}

	/**
	 *
	 * @return uuid of this enemy
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 *
	 * @return progress of this enemy
	 */
	public int getProgress(){
		return progress;
	}

	/**
	 *
	 * @return movedSteps of this enemy
	 */
	public int getMovedSteps() {
		return movedSteps;
	}

	/**
	 *
	 * @return x coordinate of this enemy in px
	 */
	public int getPositionX() {
		return x;
	}

	/**
	 *
	 * @return y coordinate of this enemy in px
	 */
	public int getPositionY() {
		return y;
	}

	public void setProgress(int progress) {
		this.progress=progress;
	}

	/**
	 *
	 * @return direction where the enemy is looking right now
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Slow the enemy by the specified parameter.
	 * @param slowness
	 */
	public void slowDown(int slowness){
		this.slowness = slowness;
		gameActivity.getGame().getMatchField().slowEnemy(this);
	}

	/**
	 * Reduces the slowness of this enemy (--> makes it faster again).
	 * @return new slowness
	 */
	public int getAndReduceSlowness(){
		return slowness > 0 ? slowness-- : slowness;
	}

	/**
	 *
	 * @return slowness of this enemy
	 */
	public int getSlowness() {
		return slowness;
	}

	/**
	 * Set the enemy in paused mode (stops moving).
	 * @param paused
	 */
	public void setPaused(boolean paused) {
		isPaused = paused;
	}

	/**
	 *
	 * @return true if the enemy is in pause mode
	 */
	public boolean isPaused(){
		return isPaused;
	}

	/**
	 *
	 * @return true if the slowness of this enemy is 0
	 */
	public boolean isFullSpeed(){
		return slowness==0;
	}
}
