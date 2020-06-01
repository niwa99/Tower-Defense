package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;

import de.dhbw.map.objects.bullet.LaserRay;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_TOWER_ASSAULTLASER_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ASSAULTLASER_HEAD;
import static de.dhbw.util.Constants.FIELD_SIZE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_2_COSTS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_2_DAMAGE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_2_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_2_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_3_COSTS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_3_DAMAGE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_3_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_3_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerLaser extends ATower {
    private LaserRay bullet = null;

    /**
     * Constructor
     * @param field
     * @param level
     * @param gameActivity
     */
    public TowerLaser(Field field, int level, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.ASSAULTLASER, level, getLaserCostsByLevel(level), getLaserDamageByLevel(level),
                getLaserRangeByLevel(level), getLaserFireRateByLevel(level), field, gameActivity);

        ImageView baseImage = new ImageView(gameActivity);
        baseImage.setLayoutParams(TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS);
        baseImage.setImageResource(DRAWABLE_TOWER_ASSAULTLASER_BASE);
        baseImage.setX(getPositionX());
        baseImage.setY(getPositionY());
        setBaseImage(baseImage);

        ImageView headImage = new ImageView(gameActivity);
        headImage.setLayoutParams(TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS);
        headImage.setImageResource(DRAWABLE_TOWER_ASSAULTLASER_HEAD);
        headImage.setX(getPositionX());
        headImage.setY(getPositionY());
        setHeadImage(headImage);


    }

    @Override
    public boolean fire(List<AEnemy> enemies){
        if(bullet==null || !bullet.isAlive()) {
            headImage.get().setRotation((float)rotateImage(enemies));
            if (super.fire(enemies)) {
                bullet = new LaserRay(getPosition(), super.targetedEnemy, enemies, this.getDamage(), gameActivity, (int) (FIELD_SIZE / 2));
                bullet.start();
                return true;
            }
        }
        return false;
    }

    /**
     * Forces the bullet of the tower to be killed.
     */
    public void removeBullet(){
        if(bullet!=null){
            bullet.killBullet();
        }
    }

    @Override
    public int getCosts(int level) {
        return getLaserCostsByLevel(level);
    }

    /**
     * Get the costs of this tower by level.
     * @param level
     * @return costs
     */
    private static int getLaserCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_COSTS;
            case 2: return TOWER_LASER_LEVEL_2_COSTS;
            default: return TOWER_LASER_LEVEL_3_COSTS;
        }
    }

    @Override
    public int getDamage(int level) {
        return getLaserDamageByLevel(level);
    }

    /**
     * Get the damage of this tower by level.
     * @param level
     * @return damage
     */
    private static int getLaserDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_DAMAGE;
            case 2: return TOWER_LASER_LEVEL_2_DAMAGE;
            default: return TOWER_LASER_LEVEL_3_DAMAGE;
        }
    }

    @Override
    public int getRange(int level) {
        return getLaserRangeByLevel(level);
    }

    /**
     * Get the range in px of this tower by level.
     * @param level
     * @return range
     */
    private static int getLaserRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
            case 2: return TOWER_LASER_LEVEL_2_RANGE_IN_PIXELS;
            default: return TOWER_LASER_LEVEL_3_RANGE_IN_PIXELS;
        }
    }

    @Override
    public int getFireRate(int level) {
        return getLaserFireRateByLevel(level);
    }

    /**
     * Get the firerate of this tower by level.
     * @param level
     * @return firerate
     */
    private static int getLaserFireRateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
            case 2: return TOWER_LASER_LEVEL_2_FIRERATE_IN_SECONDS;
            default: return TOWER_LASER_LEVEL_3_FIRERATE_IN_SECONDS;
        }
    }
}
