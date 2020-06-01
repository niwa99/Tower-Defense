package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.ABullet;
import de.dhbw.map.objects.bullet.PlasmaBall;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_TOWER_PLASMARIZER_BASE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_2_COSTS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_2_DAMAGE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_2_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_2_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_3_COSTS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_3_DAMAGE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_3_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_3_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerPlasmarizer extends ATower {

    private final int plasmaRange = 300;

    /**
     * Constructor
     * @param field
     * @param level
     * @param gameActivity
     */
    public TowerPlasmarizer(Field field, int level, GameActivity gameActivity) {
        super(UUID.randomUUID(), TowerType.PLASMARIZER, level, getPlasmarizerCostsByLevel(level), getPlasmarizerDamageByLevel(level),
                getPlasmarizerRangeByLevel(level), getPlasmarizerFirerateByLevel(level), field, gameActivity);

        ImageView baseImage = new ImageView(gameActivity);
        baseImage.setLayoutParams(TOWER_PLASMARIZER_LEVEL_1_TOWER_SIZE_PARAMS);
        baseImage.setImageResource(DRAWABLE_TOWER_PLASMARIZER_BASE);
        baseImage.setX(getPositionX());
        baseImage.setY(getPositionY());
        setBaseImage(baseImage);
    }

    @Override
    public boolean fire(List<AEnemy> enemies){
        if(super.fire(enemies)){
            ABullet plasmaBall = new PlasmaBall(getPosition(), targetedEnemy, this.getDamage(), plasmaRange, enemies, gameActivity, 0);
            plasmaBall.setBulletSpeed((int) (plasmaBall.getBulletSpeed()*1.5));
            plasmaBall.start();
            return true;
        }
        return false;
    }

    @Override
    public int getCosts(int level) {
        return getPlasmarizerCostsByLevel(level);
    }

    @Override
    public int getDamage(int level) {
        return getPlasmarizerDamageByLevel(level);
    }

    @Override
    public int getRange(int level) {
        return getPlasmarizerRangeByLevel(level);
    }

    @Override
    public int getFireRate(int level) {
        return getPlasmarizerFirerateByLevel(level);
    }

    /**
     * Get the costs of this tower by level.
     * @param level
     * @return costs
     */
    private static int getPlasmarizerCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_COSTS;
            case 2: return TOWER_PLASMARIZER_LEVEL_2_COSTS;
            default: return TOWER_PLASMARIZER_LEVEL_3_COSTS;
        }
    }

    /**
     * Get the damage of this tower by level.
     * @param level
     * @return damage
     */
    private static int getPlasmarizerDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_DAMAGE;
            case 2: return TOWER_PLASMARIZER_LEVEL_2_DAMAGE;
            default: return TOWER_PLASMARIZER_LEVEL_3_DAMAGE;
        }
    }

    /**
     * Get the range in px of this tower by level.
     * @param level
     * @return range
     */
    private static int getPlasmarizerRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_RANGE_IN_PIXELS;
            case 2: return TOWER_PLASMARIZER_LEVEL_2_RANGE_IN_PIXELS;
            default: return TOWER_PLASMARIZER_LEVEL_3_RANGE_IN_PIXELS;
        }
    }

    /**
     * Get the firerate of this tower by level.
     * @param level
     * @return firerate
     */
    private static int getPlasmarizerFirerateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_FIRERATE_IN_SECONDS;
            case 2: return TOWER_PLASMARIZER_LEVEL_2_FIRERATE_IN_SECONDS;
            default: return TOWER_PLASMARIZER_LEVEL_3_FIRERATE_IN_SECONDS;
        }
    }
}
