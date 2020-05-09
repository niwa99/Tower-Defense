package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;
import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_BOOMBASTIC;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ARTILLERY;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC_HEAD;
import static de.dhbw.util.Constants.TOWER_ARTILLERY_LEVEL_1_TOWER_SIZE_PARAMS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerBoombastic extends ATower {
    private ImageView headImage;

    public TowerBoombastic(String label, Field field, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getTowerBoombasticCostsByLevel(level),
                getTowerBoombasticDamageByLevel(level), getTowerBoombasticRangeByLevel(level),
                getTowerBoombasticFirerateByLevel(level), field, gameActivity);

        ImageView image = new ImageView(gameActivity);
        image.setLayoutParams(TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS);
        image.setImageResource(DRAWABLE_TOWER_BOOMBASTIC_BASE);
        image.setX(getPositionX());
        image.setY(getPositionY());
        //setBaseImage(image);
        gameActivity.getMapFrameLayout().addView(image);

        headImage = new ImageView(gameActivity);
        headImage.setLayoutParams(TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS);
        headImage.setImageResource(DRAWABLE_TOWER_BOOMBASTIC_HEAD);
        headImage.setX(getPositionX());
        headImage.setY(getPositionY());
        setBaseImage(headImage);
        //gameActivity.getMapFrameLayout().addView(headImage);

    }

    @Override
    public boolean fire(List<Enemy> enemies) {
        headImage.setRotation((float)rotateImage(enemies));
        if (super.fire(enemies)) {
            new Bullet(getPosition(), super.targetedEnemy,  this, DRAWABLE_BULLET_BOOMBASTIC,  gameActivity);
            return true;
        }
        return false;

    }



    public static int getTowerBoombasticCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_BOOMBASTIC_LEVEL_1_COSTS;
            default: return TOWER_BOOMBASTIC_LEVEL_1_COSTS;
        }
    }

    private static int getTowerBoombasticDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_BOOMBASTIC_LEVEL_1_DAMAGE;
            default: return TOWER_BOOMBASTIC_LEVEL_1_DAMAGE;
        }
    }

    private static int getTowerBoombasticRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_BOOMBASTIC_LEVEL_1_RANGE_IN_PIXELS;
            default: return TOWER_BOOMBASTIC_LEVEL_1_RANGE_IN_PIXELS;
        }
    }

    private static int getTowerBoombasticFirerateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_BOOMBASTIC_LEVEL_1_FIRERATE_IN_SECONDS;
            default: return TOWER_BOOMBASTIC_LEVEL_1_FIRERATE_IN_SECONDS;
        }
    }

}
