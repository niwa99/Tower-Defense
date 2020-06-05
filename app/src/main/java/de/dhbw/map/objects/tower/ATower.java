package de.dhbw.map.objects.tower;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import java.lang.Math;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.ATimerUsage;
import de.dhbw.game.EnemyType;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.util.Position;
import de.dhbw.util.SortingUtil;
import de.dhbw.map.structure.Field;

public abstract class ATower extends ATimerUsage {

	private UUID id;
	private TowerType towerType;
	private int level;
	private int costs;
	protected int damage;
	private int range;
	private int fireRate;
	private ImageView baseImage;
	protected Optional<ImageView> headImage = Optional.empty();
	private ImageView starlvlTwo;
	private ImageView starlvlThree;
	private ImageView starlvlFour;
	private int x;
	private int y;
	private Field field;
	private TimerTask task;
	protected AEnemy targetedEnemy;
	protected GameActivity gameActivity;

	/**
	 * Constructor
	 * @param id
	 * @param towerType
	 * @param level
	 * @param costs
	 * @param damage
	 * @param range
	 * @param fireRate
	 * @param field
	 * @param gameActivity
	 */
	public ATower(UUID id, TowerType towerType, int level, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
		this.id = id;
		this.towerType = towerType;
		this.level = level;
		this.costs = costs;
		this.damage = damage;
		this.range = range;
		this.fireRate = fireRate;
		this.field = field;
		this.x = field.getSpawnPoint().getX();
		this.y = field.getSpawnPoint().getY();
		this.gameActivity = gameActivity;

		switch (level) {
			case 4:
				starlvlFour = new ImageView(gameActivity);
				starlvlFour.setImageResource(R.drawable.star);
				starlvlFour.setX(field.getSpawnPoint().getX() + 100);
				starlvlFour.setY(field.getSpawnPoint().getY());
				starlvlFour.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
			case 3:
				starlvlThree = new ImageView(gameActivity);
				starlvlThree.setImageResource(R.drawable.star);
				starlvlThree.setX(field.getSpawnPoint().getX() + 50);
				starlvlThree.setY(field.getSpawnPoint().getY());
				starlvlThree.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
			case 2:
				starlvlTwo= new ImageView(gameActivity);
				starlvlTwo.setImageResource(R.drawable.star);
				starlvlTwo.setX(field.getSpawnPoint().getX());
				starlvlTwo.setY(field.getSpawnPoint().getY());
				starlvlTwo.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
		}
	}

	/**
	 * The tower shoots the nearest enemy if it is in range
	 * @param enemies
	 * @return true if there is an enemy to shoot at
	 */
	public boolean fire(List<AEnemy> enemies) {
		List<AEnemy> enemiesInRange = enemies.stream().filter(enemy -> isEnemyInRange(enemy)).collect(Collectors.toList());
		AEnemy enemy = getNearestEnemy(getPosition(), enemiesInRange);
		if (enemy != null) {
			targetedEnemy = enemy;
			return true;
		}
		return false;
	}

	/**
	 * Checks if an enemy is in range of the tower.
	 * @param enemy
	 * @return true if the enemy is in range
	 */
	public boolean isEnemyInRange(AEnemy enemy) {
		return MatchField.getDistance(enemy.getPositionX(), enemy.getPositionY(), x, y) < range;
	}

	/**
	 * Sets the base image of the tower.
	 * @param image
	 */
	public void setBaseImage(ImageView image){
		this.baseImage=image;
	}

	/**
	 * Sets the head image of the tower.
	 * @param image
	 */
	public void setHeadImage(ImageView image){
		this.headImage= Optional.of(image);
	}

	/**
	 *
	 * @return head image (if present)
	 */
	public Optional<ImageView> getHeadImage(){
		return headImage;
	}

	/**
	 *
	 * @return base image
	 */
	public ImageView getBaseImage(){
		return baseImage;
	}

	/**
	 *
	 * @return field on which the tower is located
	 */
	public Field getField() {
		return field;
	}

	/**
	 *
	 * @return timertask of the tower
	 */
	public TimerTask getTask() {
		return task;
	}

	/**
	 * Set the task for tower.
	 * @param task
	 */
	public void setTask(TimerTask task) {
		this.task = task;
	}

	/**
	 * All enemies are saved in a map with their actual distance to the tower.
	 * The map is sorted by distance, so the nearest enemy is on the first place of the map. We don't have to care about enemies with the same distance.
	 * @param enemies
	 * @return
	 */
	public AEnemy getNearestEnemy(Position actualPosition, List<AEnemy> enemies) {
		if (!enemies.isEmpty()) {
			Map<AEnemy, Integer> distanceToEnemy = new HashMap<>();

			for (AEnemy enemy : enemies) {
				int distance = MatchField.getDistance(enemy.getPositionX(), enemy.getPositionY(), actualPosition.getX(), actualPosition.getY());
				distanceToEnemy.put(enemy, distance);
			}

			List<AEnemy> sortedByDistance = SortingUtil.getSortedListBySortingMapByValue(distanceToEnemy);

			for (AEnemy enemy : sortedByDistance) {
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
	public double rotateImage(List<AEnemy> enemies) {
		double rotation = 0;
		AEnemy enemy = getNearestEnemy(getPosition(), enemies);
		if (enemy != null) {
			double a = this.getPositionX() - enemy.getPositionX();
			double b = this.getPositionY() - enemy.getPositionY();
			rotation = Math.toDegrees(Math.atan2(b, a));

		}
		return rotation;
	}

	/**
	 *
	 * @return id of this tower
	 */
	public UUID getId() {
		return id;
	}

	/**
	 *
	 * @return towertype
	 */
	public TowerType getTowerType() { return this.towerType; }

	/**
	 *
	 * @return level of this tower
	 */
	public int getLevel() { return this.level; }

	/**
	 *
	 * @return costs of this tower
	 */
	public int getCosts() {
		return costs;
	}

	/**
	 *
	 * @return damage of this tower
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 *
	 * @return range in px of this tower
	 */
	public int getRange() {
		return range;
	}

	/**
	 *
	 * @return firerate of this tower
	 */
	public int getFireRate() {
		return fireRate;
	}

	public abstract int getCosts(int level);

	public abstract int getDamage(int level);

	public abstract int getRange(int level);

	public abstract int getFireRate(int level);

	/**
	 *
	 * @return x-position of this tower in px (midpoint)
	 */
	public int getPositionX() {
		return x;
	}

	/**
	 *
	 * @return y-position of this tower in px (midpoint)
	 */
	public int getPositionY() {
		return y;
	}

	/**
	 *
	 * @return position of this tower in px (midpoint)
	 */
	public Position getPosition() {
		return new Position(x, y);
	}

	/**
	 *
	 * @return image of level-2-star
	 */
	public ImageView getStarlvlTwo() {
		return starlvlTwo;
	}

	/**
	 *
	 * @return image of level-3-star
	 */
	public ImageView getStarlvlThree() {
		return starlvlThree;
	}

	/**
	 *
	 * @return image of level-4-star
	 */
	public ImageView getStarlvlFour() {
		return starlvlFour;
	}

	@Override
	public void calculateDelay(long time){
		setDelay(fireRate*1000 - (time-lastTimeActionMillis));
	}
}