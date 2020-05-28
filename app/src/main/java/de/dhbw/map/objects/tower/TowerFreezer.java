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
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerFreezer extends ATower {
    private int slowness;

    public TowerFreezer(UUID id, int level, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
        super(id, TowerType.FREEZER, level, costs, damage, range, fireRate, field, gameActivity);
    }

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

    @Override
    public boolean fire(List<AEnemy> enemies) {
        if (super.fire(enemies)) {
            gameActivity.runOnUiThread(() -> {
                this.getBaseImage().setImageResource(DRAWABLE_TOWER_FREEZER_HEAD);
            });

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    gameActivity.runOnUiThread(() -> {
                        getBaseImage().setImageResource(DRAWABLE_TOWER_FREEZER_BASE);
                    });
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

    public int getSlowness(){
        return slowness;
    }

    public static int getFreezerCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_COSTS;
            default: return TOWER_FREEZER_LEVEL_1_COSTS;
        }
    }

    private static int getFreezerSlownessByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_SLOWNESS;
            default: return TOWER_FREEZER_LEVEL_1_SLOWNESS;
        }
    }

    private static int getFreezerDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_DAMAGE;
            default: return TOWER_FREEZER_LEVEL_1_DAMAGE;
        }
    }

    private static int getFreezerRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_RANGE_IN_PIXELS;
            default: return TOWER_FREEZER_LEVEL_1_RANGE_IN_PIXELS;
        }
    }

    private static int getFreezerFirerateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_FREEZER_LEVEL_1_FIRERATE_IN_SECONDS;
            default: return TOWER_FREEZER_LEVEL_1_FIRERATE_IN_SECONDS;
        }
    }
}
