package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.PlasmaBall;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_FREEZER;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_PLASMARIZER_BASE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_DAMAGE;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_FIRERATE_IN_SECONDS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_RANGE_IN_PIXELS;
import static de.dhbw.util.Constants.TOWER_PLASMARIZER_LEVEL_1_TOWER_SIZE_PARAMS;

public class TowerPlasmarizer extends ATower {
    private final int plasmaRange = 50;

    public TowerPlasmarizer(UUID id, TowerType towerType, int level, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
        super(id, towerType, level, costs, damage, range, fireRate, field, gameActivity);
    }

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
    public boolean fire(List<Enemy> enemies){
        ArrayList<Enemy> targetEnemies = new ArrayList();
        if(super.fire(enemies)){
                Enemy nextTarget = getNearestEnemy(targetedEnemy.getPosition(), enemies);
                targetedEnemy = nextTarget;
                if (targetedEnemy.getPosition().getX() < nextTarget.getPosition().getX()) {
                    for(Enemy e: enemies) {

                    }

                } else {

                }
            new PlasmaBall(getPosition(), targetedEnemy, this.getDamage(), gameActivity, 0);
        }
        return false;
    }

    @Override
    public int getCosts(int level) {
        return 0;
    }

    @Override
    public int getDamage(int level) {
        return 0;
    }

    @Override
    public int getRange(int level) {
        return 0;
    }

    @Override
    public int getFireRate(int level) {
        return 0;
    }

    public static int getPlasmarizerCostsByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_COSTS;
            default: return TOWER_PLASMARIZER_LEVEL_1_COSTS;
        }
    }

    private static int getPlasmarizerDamageByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_DAMAGE;
            default: return TOWER_PLASMARIZER_LEVEL_1_DAMAGE;
        }
    }

    private static int getPlasmarizerRangeByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_RANGE_IN_PIXELS;
            default: return TOWER_PLASMARIZER_LEVEL_1_RANGE_IN_PIXELS;
        }
    }

    private static int getPlasmarizerFirerateByLevel(int level) {
        switch (level) {
            case 1: return TOWER_PLASMARIZER_LEVEL_1_FIRERATE_IN_SECONDS;
            default: return TOWER_PLASMARIZER_LEVEL_1_FIRERATE_IN_SECONDS;
        }
    }
}
