package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;

import static de.dhbw.util.Constants.CAR_ENEMY_SIZE_PARAMS;
import static de.dhbw.util.Constants.CAR_HEALTHPOINTS;
import static de.dhbw.util.Constants.CAR_LIFE_POINT_COSTS;
import static de.dhbw.util.Constants.CAR_SPEED;
import static de.dhbw.util.Constants.CAR_VALUE;
import static de.dhbw.util.Constants.DRAWABLE_CAR;
import static de.dhbw.util.Constants.DRAWABLE_CAR_HITTED;

public class Car extends AEnemy {

    private Timer timer;

    /**
     * Constructor
     * @param label
     * @param level
     * @param gameActivity
     */
    public Car(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getCarHealthpointsByLevel(level), getCarSpeedByLevel(level), getCarValueByLevel(level), getCarLifePointsCostsByLevel(level), gameActivity, EnemyType.CAR, createCarImage(gameActivity));
        timer = new Timer();
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param label
     * @param level
     * @param image
     * @param gameActivity
     */
    public Car(String label, int level, ImageView image, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getCarHealthpointsByLevel(level), getCarSpeedByLevel(level), getCarValueByLevel(level), getCarLifePointsCostsByLevel(level), gameActivity, EnemyType.CAR, image);
        timer = new Timer();
    }

    @Override
    public void hit(int damage) {
        super.reduceHealthPoints(damage);
        gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_CAR_HITTED));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(() -> image.setImageResource(DRAWABLE_CAR));
            }
        }, 100);
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
        return CAR_VALUE * level;

    }

    /**
     * Get lifepointcosts by level.
     * @param level
     * @return lifepointcosts
     */
    private static int getCarLifePointsCostsByLevel(int level) {
        return CAR_LIFE_POINT_COSTS * level;

    }

    /**
     * Creates the car image with gameActivity context.
     * @param gameActivity
     * @return a car imageview
     */
    private static ImageView createCarImage(GameActivity gameActivity) {
        ImageView carImage = new ImageView(gameActivity);
        carImage.setLayoutParams(CAR_ENEMY_SIZE_PARAMS);
        carImage.setImageResource(DRAWABLE_CAR);
        return carImage;
    }
}
