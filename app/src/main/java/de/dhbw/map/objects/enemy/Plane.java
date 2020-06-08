package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.MapStructure;

import static de.dhbw.util.Constants.PLANE_ENEMY_SIZE_PARAMS;
import static de.dhbw.util.Constants.PLANE_HEALTHPOINTS;
import static de.dhbw.util.Constants.PLANE_LIFE_POINT_COSTS;
import static de.dhbw.util.Constants.PLANE_SPEED;
import static de.dhbw.util.Constants.PLANE_VALUE;
import static de.dhbw.util.Constants.DRAWABLE_PLANE;
import static de.dhbw.util.Constants.DRAWABLE_PLANE_HITTED;

public class Plane extends AEnemy {
    private Timer timer;
    private Field actualPlaneField;

    /**
     * Constructor
     * @param label
     * @param level
     * @param gameActivity
     */
    public Plane(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getPlaneHealthpointsByLevel(level), getPlaneSpeedByLevel(level), getPlaneValueByLevel(level), getPlaneLifePointsCostsByLevel(level), gameActivity, EnemyType.PLANE, createPlaneImage(gameActivity));
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
        super(label, UUID.randomUUID(), getPlaneHealthpointsByLevel(level), getPlaneSpeedByLevel(level), getPlaneValueByLevel(level), getPlaneLifePointsCostsByLevel(level), gameActivity, EnemyType.PLANE, image);
        timer = new Timer();
    }

    @Override
    public void hit(int damage) {
        super.reduceHealthPoints(damage);
        gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_PLANE_HITTED));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_PLANE));
            }
        }, 100);
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
        return PLANE_HEALTHPOINTS * level;
    }

    /**
     * Get speed by level.
     * @param level
     * @return speed
     */
    private static int getPlaneSpeedByLevel(int level) {
        return PLANE_SPEED;

    }

    /**
     * Get value by level.
     * @param level
     * @return value
     */
    private static int getPlaneValueByLevel(int level) {
        return PLANE_VALUE * level;
    }

    /**
     * Get lifepointcosts by level.
     * @param level
     * @return lifepointcosts
     */
    private static int getPlaneLifePointsCostsByLevel(int level) {
        return PLANE_LIFE_POINT_COSTS * level;
    }

    /**
     * Creates the car image with gameActivity context.
     * @param gameActivity
     * @return a car imageview
     */
    private static ImageView createPlaneImage(GameActivity gameActivity) {
        ImageView planeImage = new ImageView(gameActivity);
        planeImage.setLayoutParams(PLANE_ENEMY_SIZE_PARAMS);
        planeImage.setImageResource(DRAWABLE_PLANE);
        return planeImage;
    }
}
