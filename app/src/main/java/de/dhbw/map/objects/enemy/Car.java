package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;

import static de.dhbw.util.Constants.CAR_HEALTHPOINTS;
import static de.dhbw.util.Constants.CAR_LIFE_POINT_COSTS;
import static de.dhbw.util.Constants.CAR_SPEED;
import static de.dhbw.util.Constants.CAR_VALUE;
import static de.dhbw.util.Constants.DRAWABLE_CAR;
import static de.dhbw.util.Constants.DRAWABLE_CAR_HITTED;

public class Car extends AEnemy {
    /**
     * Constructor
     * @param label
     * @param level
     * @param gameActivity
     */
    public Car(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getCarHealthpointsByLevel(level), getCarSpeedByLevel(level), getCarValueByLevel(level), getCarLifePointsCostsByLevel(level), gameActivity, EnemyType.CAR, DRAWABLE_CAR, DRAWABLE_CAR_HITTED);
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param label
     * @param level
     * @param gameActivity
     * @param enemyView
     */
    public Car(String label, int level, GameActivity gameActivity, EnemyView enemyView) {
        super(label, UUID.randomUUID(), getCarHealthpointsByLevel(level), getCarSpeedByLevel(level), getCarValueByLevel(level), getCarLifePointsCostsByLevel(level), gameActivity, EnemyType.CAR, enemyView);
    }

    /**
     * Get healthpoints by level.
     * @param level
     * @return healthpoints
     */
    private static int getCarHealthpointsByLevel(int level) {
        return CAR_HEALTHPOINTS * level;
    }

    /**
     * Get speed by level.
     * @param level
     * @return speed
     */
    private static int getCarSpeedByLevel(int level) {
        return CAR_SPEED;

    }

    /**
     * Get value by level.
     * @param level
     * @return value
     */
    private static int getCarValueByLevel(int level) {
        return CAR_VALUE * (level / 5 + 1);

    }

    /**
     * Get lifepointcosts by level.
     * @param level
     * @return lifepointcosts
     */
    private static int getCarLifePointsCostsByLevel(int level) {
        return CAR_LIFE_POINT_COSTS * (level / 5 + 1);

    }
}
