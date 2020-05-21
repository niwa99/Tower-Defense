package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_TOWER_ARTILLERY;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerLaser extends ATower {
    public TowerLaser(UUID id, TowerType towerType, int level, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
        super(id, towerType, level, costs, damage, range, fireRate, field, gameActivity);
    }

    public TowerLaser(Field field, int level, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.ASSAULTLASER, level, getLaserCostsByLevel(level), getLaserDamageByLevel(level),
                getLaserRangeByLevel(level), getLaserFireRateByLevel(level), field, gameActivity);

        ImageView baseImage = new ImageView(gameActivity);
        baseImage.setLayoutParams(TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS);
        baseImage.setImageResource(DRAWABLE_TOWER_ARTILLERY);
        baseImage.setX(getPositionX());
        baseImage.setY(getPositionY());
        setBaseImage(baseImage);
    }

    @Override
    public boolean fire(List<Enemy> enemies){
        return super.fire(enemies);
    }

    @Override
    public int getCosts(int level) {
        return getLaserCostsByLevel(level);
    }

    private static int getLaserCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_COSTS;
            default: return TOWER_LASER_LEVEL_1_COSTS;
        }
    }

    @Override
    public int getDamage(int level) {
        return getLaserDamageByLevel(level);
    }

    private static int getLaserDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_DAMAGE;
            default: return TOWER_LASER_LEVEL_1_DAMAGE;
        }
    }

    @Override
    public int getRange(int level) {
        return getLaserRangeByLevel(level);
    }

    private static int getLaserRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
            default: return TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
        }
    }

    @Override
    public int getFireRate(int level) {
        return getLaserFireRateByLevel(level);
    }

    private static int getLaserFireRateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
            default: return TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
        }
    }
}
