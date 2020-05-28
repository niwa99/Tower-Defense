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
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.util.Position;
import de.dhbw.util.SortingUtil;
import de.dhbw.map.structure.Field;

public abstract class ATower extends ATimerUsage {
	private SortingUtil sortingUtil = new SortingUtil();

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
	private int x;
	private int y;
	private Field field;
	private TimerTask task;
	protected AEnemy targetedEnemy;
	protected GameActivity gameActivity;
	
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
			if (enemy instanceof Tank) {
				targetedEnemy = enemy;
				return true;
			}
		}
		return false;
	}
	
	public boolean isEnemyInRange(AEnemy enemy) {
		return MatchField.getDistance(enemy.getPositionX(), enemy.getPositionY(), x, y) < range;
	}

	public void setBaseImage(ImageView image){
		this.baseImage=image;
	}

	public void setHeadImage(ImageView image){
		this.headImage= Optional.of(image);
	}

	public Optional<ImageView> getHeadImage(){
		return headImage;
	}

	public ImageView getBaseImage(){
		return baseImage;
	}

	public Field getField() {
		return field;
	}

	public TimerTask getTask() {
		return task;
	}

	public void setTask(TimerTask task) {
		this.task = task;
	}

	/**
	 * All enemies are saved in a map with their actual distance to the tower.
	 * The map is sorted by distance, so the nearest enemy is on the first place of the map. We don't have to care about enemies with the same distance.
	 * @param enemies
	 * @return
	 */
	public Enemy getNearestEnemy(Position actualPosition, List<AEnemy> enemies) {
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
	 * @param xEnemy
	 * @param yEnemy
	 * @return distance between AEnemy or other Object and Tower
	 */
	public int getDistance(int xEnemy, int yEnemy) {
		return (int) Math.round(Math.sqrt(Math.pow(Math.abs(xEnemy-x), 2) + Math.pow(Math.abs(yEnemy-y), 2)));
	}

	public UUID getId() {
		return id;
	}

	public TowerType getTowerType() { return this.towerType; }

	public int getLevel() { return this.level; }

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

	public abstract int getCosts(int level);

	public abstract int getDamage(int level);

	public abstract int getRange(int level);

	public abstract int getFireRate(int level);

	public int getPositionX() {
		return x;
	}
	
	public int getPositionY() {
		return y;
	}

	public Position getPosition() {
		return new Position(x, y);
	}

	public ImageView getStarlvlTwo() {
		return starlvlTwo;
	}

	public ImageView getStarlvlThree() {
		return starlvlThree;
	}

	@Override
	public void calculateDelay(long time){
		setDelay(fireRate*1000 - (time-lastTimeActionMillis));
	}
}