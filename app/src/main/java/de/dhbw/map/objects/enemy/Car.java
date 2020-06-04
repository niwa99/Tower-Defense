package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.structure.MapStructure;

import static de.dhbw.util.Constants.CAR_ENEMY_SIZE_PARAMS;
import static de.dhbw.util.Constants.CAR_LEVEL_1_HEALTHPOINTS;
import static de.dhbw.util.Constants.CAR_LEVEL_1_LIFE_POINT_COSTS;
import static de.dhbw.util.Constants.CAR_LEVEL_1_SPEED;
import static de.dhbw.util.Constants.CAR_LEVEL_1_VALUE;
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
        switch (level) {
            case 1: return CAR_LEVEL_1_HEALTHPOINTS;
            default: return CAR_LEVEL_1_HEALTHPOINTS;
        }
    }

    /**
     * Get speed by level.
     * @param level
     * @return speed
     */
    private static int getCarSpeedByLevel(int level) {
        switch (level) {
            case 1: return CAR_LEVEL_1_SPEED;
            default: return CAR_LEVEL_1_SPEED;
        }
    }

    /**
     * Get value by level.
     * @param level
     * @return value
     */
    private static int getCarValueByLevel(int level) {
        switch (level) {
            case 1: return CAR_LEVEL_1_VALUE;
            default: return CAR_LEVEL_1_VALUE;
        }
    }

    /**
     * Get lifepointcosts by level.
     * @param level
     * @return lifepointcosts
     */
    private static int getCarLifePointsCostsByLevel(int level) {
        switch (level) {
            case 1: return CAR_LEVEL_1_LIFE_POINT_COSTS;
            default: return CAR_LEVEL_1_LIFE_POINT_COSTS;
        }
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
