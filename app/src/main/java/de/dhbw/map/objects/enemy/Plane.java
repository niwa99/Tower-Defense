package de.dhbw.map.objects.enemy;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.MapStructure;

import static de.dhbw.util.Constants.PLANE_LEVEL_1_HEALTHPOINTS;
import static de.dhbw.util.Constants.PLANE_LEVEL_1_LIFE_POINT_COSTS;
import static de.dhbw.util.Constants.PLANE_LEVEL_1_SPEED;
import static de.dhbw.util.Constants.PLANE_LEVEL_1_VALUE;
import static de.dhbw.util.Constants.DRAWABLE_PLANE;
import static de.dhbw.util.Constants.DRAWABLE_PLANE_HITTED;

public class Plane extends AEnemy {
    private Timer timer;

    /**
     * Constructor
     * @param label
     * @param level
     * @param gameActivity
     */
    public Plane(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getPlaneHealthpointsByLevel(level), getPlaneSpeedByLevel(level), getPlaneValueByLevel(level), getPlaneLifePointsCostsByLevel(level), gameActivity, EnemyType.PLANE, DRAWABLE_PLANE, DRAWABLE_PLANE_HITTED);
        timer = new Timer();
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param label
     * @param level
     * @param image
     * @param gameActivity
     */
    public Plane(String label, int level, ImageView image, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getPlaneHealthpointsByLevel(level), getPlaneSpeedByLevel(level), getPlaneValueByLevel(level), getPlaneLifePointsCostsByLevel(level), gameActivity, EnemyType.PLANE, DRAWABLE_PLANE, DRAWABLE_PLANE_HITTED);
        timer = new Timer();
    }
    @Override
    protected Field getEnemyField(MapStructure map){
        return map.getFieldForPlane(actualField);
    }

    /**
     * Get healthpoints by level.
     * @param level
     * @return healthpoints
     */
    private static int getPlaneHealthpointsByLevel(int level) {
        switch (level) {
            case 1: return PLANE_LEVEL_1_HEALTHPOINTS;
            default: return PLANE_LEVEL_1_HEALTHPOINTS;
        }
    }

    /**
     * Get speed by level.
     * @param level
     * @return speed
     */
    private static int getPlaneSpeedByLevel(int level) {
        switch (level) {
            case 1: return PLANE_LEVEL_1_SPEED;
            default: return PLANE_LEVEL_1_SPEED;
        }
    }

    /**
     * Get value by level.
     * @param level
     * @return value
     */
    private static int getPlaneValueByLevel(int level) {
        switch (level) {
            case 1: return PLANE_LEVEL_1_VALUE;
            default: return PLANE_LEVEL_1_VALUE;
        }
    }

    /**
     * Get lifepointcosts by level.
     * @param level
     * @return lifepointcosts
     */
    private static int getPlaneLifePointsCostsByLevel(int level) {
        switch (level) {
            case 1: return PLANE_LEVEL_1_LIFE_POINT_COSTS;
            default: return PLANE_LEVEL_1_LIFE_POINT_COSTS;
        }
    }

}
