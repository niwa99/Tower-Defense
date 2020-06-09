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

	protected Timer timer;

	/**
	 * Constructor
	 * @param label
	 * @param level
	 * @param gameActivity
	 */
	public Tank(String label, int level, GameActivity gameActivity) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level), gameActivity, EnemyType.TANK, DRAWABLE_TANK, DRAWABLE_TANK_HITTED);
		timer = new Timer();
	}

	/**
	 * Constructor
	 * @param label
	 * @param level
	 * @param gameActivity
	 * @param enemyView
	 */
	public Tank(String label, int level, GameActivity gameActivity, EnemyView enemyView) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level), gameActivity, EnemyType.TANK, enemyView);
		timer = new Timer();
	}

    /**
     * Constructor (only for test purposes)
     * @param label
     * @param level
     * @param image
     * @param gameActivity
     */
    public Tank(String label, int level, ImageView image, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level), gameActivity, EnemyType.TANK, DRAWABLE_TANK, DRAWABLE_TANK_HITTED);
        timer = new Timer();
    }

	/**
	 * Get healthpoints by level.
	 * @param level
	 * @return healthpoints
	 */
	private static int getTankHealthpointsByLevel(int level) {
		return TANK_HEALTHPOINTS * level;
	}

	/**
	 * Get speed by level.
	 * @param level
	 * @return speed
	 */
	public static int getTankSpeedByLevel(int level) {
		return TANK_SPEED;
	}

	/**
	 * Get value by level.
	 * @param level
	 * @return value
	 */
	private static int getTankValueByLevel(int level) {
		return TANK_VALUE * level;
	}

	/**
	 * Get lifepointcosts by level.
	 * @param level
	 * @return lifepointcosts
	 */
	public static int getTankLifePointsCostsByLevel(int level) {
		return TANK_LIFE_POINT_COSTS * level;
	}
}
