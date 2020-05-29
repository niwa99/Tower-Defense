package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.Bomb;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_BOOMBASTIC;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC_HEAD;
import static de.dhbw.util.Constants.FIELD_SIZE;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerBoombastic extends ATower {

    public TowerBoombastic(Field field, int level, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.BOOMBASTIC,
                level, getTowerBoombasticCostsByLevel(level),
                getTowerBoombasticDamageByLevel(level), getTowerBoombasticRangeByLevel(level), getTowerBoombasticFirerateByLevel(level), field, gameActivity);

        ImageView baseImage = new ImageView(gameActivity);
        baseImage.setLayoutParams(TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS);
        baseImage.setImageResource(DRAWABLE_TOWER_BOOMBASTIC_BASE);
        baseImage.setX(getPositionX());
        baseImage.setY(getPositionY());
        setBaseImage(baseImage);

        ImageView headImage = new ImageView(gameActivity);
        headImage.setLayoutParams(TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS);
        headImage.setImageResource(DRAWABLE_TOWER_BOOMBASTIC_HEAD);
        headImage.setX(getPositionX());
        headImage.setY(getPositionY());
        setHeadImage(headImage);
    }

    /**
     * Constructor for test purpose only!
     */
    public TowerBoombastic(Field field, int level, ImageView image, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.BOOMBASTIC,
                level, getTowerBoombasticCostsByLevel(level),
                getTowerBoombasticDamageByLevel(level), getTowerBoombasticRangeByLevel(level), getTowerBoombasticFirerateByLevel(level), field, gameActivity);
        setBaseImage(image);
        setHeadImage(image);
    }

    @Override
    public boolean fire(List<AEnemy> enemies) {
        headImage.get().setRotation((float)rotateImage(enemies));
        if (super.fire(enemies)) {
            new Bomb(getPosition(), super.targetedEnemy,  enemies, this.getDamage(), DRAWABLE_BULLET_BOOMBASTIC,  gameActivity, FIELD_SIZE/2).start();
            return true;
        }
        return false;
    }

    @Override
    public int getCosts(int level) {
        return getTowerBoombasticCostsByLevel(level);
    }

    @Override
    public int getDamage(int level) {
        return getTowerBoombasticDamageByLevel(level);
    }

    @Override
    public int getRange(int level) {
        return getTowerBoombasticRangeByLevel(level);
    }

    @Override
    public int getFireRate(int level) {
        return getTowerBoombasticFirerateByLevel(level);
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
