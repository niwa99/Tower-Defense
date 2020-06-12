package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.SnowFlake;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_FREEZER;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_FREEZER_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_FREEZER_HEAD;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_SLOWNESS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_2_COSTS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_2_DAMAGE;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_2_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_2_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_2_SLOWNESS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_3_COSTS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_3_DAMAGE;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_3_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_3_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_3_SLOWNESS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_4_COSTS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_4_DAMAGE;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_4_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_4_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_4_SLOWNESS;

public class TowerFreezer extends ATower {
    private int slowness;

    /**
     * Constructor
     * @param field
     * @param level
     * @param gameActivity
     */
    public TowerFreezer(Field field, int level, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.FREEZER, level, getFreezerCostsByLevel(level), getFreezerDamageByLevel(level),
                getFreezerRangeByLevel(level), getFreezerFirerateByLevel(level), field, gameActivity);

        ImageView baseImage = new ImageView(gameActivity);
        baseImage.setLayoutParams(TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS);
        baseImage.setImageResource(DRAWABLE_TOWER_FREEZER_BASE);
        baseImage.setX(getPositionX());
        baseImage.setY(getPositionY());
        setBaseImage(baseImage);

        this.slowness=getFreezerSlownessByLevel(level);
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param field
     * @param level
     * @param image
     * @param gameActivity
     */
    public TowerFreezer(Field field, int level, ImageView image, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.FREEZER, level, getFreezerCostsByLevel(level), getFreezerDamageByLevel(level),
                getFreezerRangeByLevel(level), getFreezerFirerateByLevel(level), field, gameActivity);
        setBaseImage(image);

        this.slowness = getFreezerSlownessByLevel(level);
    }

    @Override
    public boolean fire(List<AEnemy> enemies) {
        if (super.fire(enemies)) {
            gameActivity.setImageResource(getBaseImage(), DRAWABLE_TOWER_FREEZER_HEAD);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    gameActivity.setImageResource(getBaseImage(), DRAWABLE_TOWER_FREEZER_BASE);
                }
            }, 500);

            new SnowFlake(getPosition(), super.targetedEnemy, this.getDamage(),this.getSlowness(), DRAWABLE_BULLET_FREEZER, gameActivity).start();
            return true;
        }
        return false;
    }

    @Override
    public int getCosts(int level) {
        return getFreezerCostsByLevel(level);
    }

    @Override
    public int getDamage(int level) {
        return getFreezerDamageByLevel(level);
    }

    @Override
    public int getRange(int level) {
        return getFreezerRangeByLevel(level);
    }

    @Override
    public int getFireRate(int level) {
        return getFreezerFirerateByLevel(level);
    }

    /**
     * Get the slowness effect of this tower.
     * @return slowness
     */
    public int getSlowness(){
        return slowness;
    }

    /**
     * Get the costs of this tower by level.
     * @param level
     * @return costs
     */
    public static int getFreezerCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_COSTS;
            case 2: return TOWER_FREEZER_LEVEL_2_COSTS;
            case 3: return TOWER_FREEZER_LEVEL_3_COSTS;
            case 4: return TOWER_FREEZER_LEVEL_4_COSTS;
            default: return TOWER_FREEZER_LEVEL_4_COSTS;
        }
    }

    /**
     * Get the slowness of this tower by level.
     * @param level
     * @return slowness
     */
    private static int getFreezerSlownessByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_SLOWNESS;
            case 2: return TOWER_FREEZER_LEVEL_2_SLOWNESS;
            case 3: return TOWER_FREEZER_LEVEL_3_SLOWNESS;
            case 4: return TOWER_FREEZER_LEVEL_4_SLOWNESS;
            default: return TOWER_FREEZER_LEVEL_4_SLOWNESS;
        }
    }

    /**
     * Get the damage of this tower by level.
     * @param level
     * @return damage
     */
    private static int getFreezerDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_DAMAGE;
            case 2: return TOWER_FREEZER_LEVEL_2_DAMAGE;
            case 3: return TOWER_FREEZER_LEVEL_3_DAMAGE;
            case 4: return TOWER_FREEZER_LEVEL_4_DAMAGE;
            default: return TOWER_FREEZER_LEVEL_4_DAMAGE;
        }
    }

    /**
     * Get the range in px of this tower by level.
     * @param level
     * @return range
     */
    private static int getFreezerRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_RANGE_IN_PIXELS;
            case 2: return TOWER_FREEZER_LEVEL_2_RANGE_IN_PIXELS;
            case 3: return TOWER_FREEZER_LEVEL_3_RANGE_IN_PIXELS;
            case 4: return TOWER_FREEZER_LEVEL_4_RANGE_IN_PIXELS;
            default: return TOWER_FREEZER_LEVEL_4_RANGE_IN_PIXELS;
        }
    }

    /**
     * Get the firerate of this tower by level.
     * @param level
     * @return firerate
     */
    private static int getFreezerFirerateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_FIRERATE_IN_SECONDS;
            case 2: return TOWER_FREEZER_LEVEL_2_FIRERATE_IN_SECONDS;
            case 3: return TOWER_FREEZER_LEVEL_3_FIRERATE_IN_SECONDS;
            case 4: return TOWER_FREEZER_LEVEL_4_FIRERATE_IN_SECONDS;
            default: return TOWER_FREEZER_LEVEL_4_FIRERATE_IN_SECONDS;
        }
    }
}
