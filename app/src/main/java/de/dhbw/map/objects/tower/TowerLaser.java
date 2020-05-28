package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.ABullet;
import de.dhbw.map.objects.bullet.Bomb;
import de.dhbw.map.objects.bullet.LaserRay;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.Field;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_BOOMBASTIC;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ARTILLERY;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ASSAULTLASER;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ASSAULTLASER_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_ASSAULTLASER_HEAD;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC_HEAD;
import static de.dhbw.util.Constants.FIELD_SIZE;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_LASER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerLaser extends ATower {
    private LaserRay bullet = null;

    public TowerLaser(UUID id, TowerType towerType, int level, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
        super(id, towerType, level, costs, damage, range, fireRate, field, gameActivity);
    }

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
    public boolean fire(List<Enemy> enemies){
        if(bullet==null || !bullet.isAlive()) {
            headImage.get().setRotation((float)rotateImage(enemies));
            if (super.fire(enemies)) {
                bullet = new LaserRay(getPosition(), super.targetedEnemy, this.getDamage(), gameActivity, (int) (FIELD_SIZE / 2));
                bullet.start();
                return true;
            }
        }
        return false;
    }

    public void removeBullet(){
        if(bullet!=null){
            bullet.killBullet();
        }
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
