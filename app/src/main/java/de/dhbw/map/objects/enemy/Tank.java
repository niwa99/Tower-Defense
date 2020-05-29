package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.structure.MapStructure;

import static de.dhbw.util.Constants.*;

public class Tank extends AEnemy {

	private Timer timer;

	/**
	 * Constructor
	 * @param label
	 * @param level
	 * @param gameActivity
	 */
	public Tank(String label, int level, GameActivity gameActivity) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level), gameActivity, EnemyType.TANK, createTankImage(gameActivity));
		timer = new Timer();
	}

	/**
	 * Constructor (TEST PURPOSE ONLY!)
	 * @param label
	 * @param level
	 * @param image
	 * @param gameActivity
	 */
	public Tank(String label, int level, ImageView image, GameActivity gameActivity) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level), gameActivity, EnemyType.TANK, image);
		timer = new Timer();
	}

	@Override
	public boolean move(MapStructure map) {
		if (super.move(map)) {
			gameActivity.runOnUiThread(() -> {
				image.setX(getPositionX());
				image.setY(getPositionY());
				switch (getDirection()) {
					case UP:
						image.setRotation(0);
						break;
					case RIGHT:
						image.setRotation(90);
						break;
					case DOWN:
						image.setRotation(180);
						break;
					case LEFT:
						image.setRotation(270);
						break;
				}
			});
		}
		return false;
	}

	@Override
	public void hit(int damage) {
		super.reduceHealthPoints(damage);
		gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_TANK_HITTED));
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_TANK));
			}
		}, 100);
	}

	/**
	 * Get healthpoints by level.
	 * @param level
	 * @return healthpoints
	 */
	private static int getTankHealthpointsByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_HEALTHPOINTS;
			case 2: return TANK_LEVEL_2_HEALTHPOINTS;
			case 3: return TANK_LEVEL_3_HEALTHPOINTS;
			default: return TANK_LEVEL_1_HEALTHPOINTS;
		}
	}

	/**
	 * Get speed by level.
	 * @param level
	 * @return speed
	 */
	private static int getTankSpeedByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_SPEED;
			case 2: return TANK_LEVEL_2_SPEED;
			case 3: return TANK_LEVEL_3_SPEED;
			default: return TANK_LEVEL_1_SPEED;
		}
	}

	/**
	 * Get value by level.
	 * @param level
	 * @return value
	 */
	private static int getTankValueByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_VALUE;
			case 2: return TANK_LEVEL_2_VALUE;
			case 3: return TANK_LEVEL_3_VALUE;
			default: return TANK_LEVEL_1_VALUE;
		}
	}

	/**
	 * Get lifepointcosts by level.
	 * @param level
	 * @return lifepointcosts
	 */
	private static int getTankLifePointsCostsByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_LIFE_POINT_COSTS;
			case 2: return TANK_LEVEL_2_LIFE_POINT_COSTS;
			case 3: return TANK_LEVEL_3_LIFE_POINT_COSTS;
			default: return TANK_LEVEL_1_LIFE_POINT_COSTS;
		}
	}

	/**
	 * Creates the tank image with gameActivity context.
	 * @param gameActivity
	 * @return a tank imageview
	 */
	private static ImageView createTankImage(GameActivity gameActivity) {
		ImageView tankImage = new ImageView(gameActivity);
		tankImage.setLayoutParams(TANK_ENEMY_SIZE_PARAMS);
		tankImage.setImageResource(DRAWABLE_TANK);
		return tankImage;
	}

}
