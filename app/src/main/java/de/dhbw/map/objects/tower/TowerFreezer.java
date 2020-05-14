package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.Bomb;
import de.dhbw.map.objects.bullet.SnowFlake;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.Field;

import static de.dhbw.util.Constants.DRAWABLE_BULLET_BOOMBASTIC;
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
    private ImageView imageShoot;
    private int slowness;

    public TowerFreezer(String label, UUID id, int costs, int damage, int range, int fireRate, Field field, GameActivity gameActivity) {
        super(label, id, costs, damage, range, fireRate, field, gameActivity);
    }

    public TowerFreezer(String label, Field field, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getFreezerCostsByLevel(level), getFreezerDamageByLevel(level),
                getFreezerRangeByLevel(level), getFreezerFirerateByLevel(level), field, gameActivity);

        ImageView image = new ImageView(gameActivity);
        image.setLayoutParams(TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS);
        image.setImageResource(DRAWABLE_TOWER_FREEZER_BASE);
        image.setX(getPositionX());
        image.setY(getPositionY());
        setBaseImage(image);

        imageShoot = new ImageView(gameActivity);
        imageShoot.setLayoutParams(TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS);
        imageShoot.setImageResource(DRAWABLE_TOWER_FREEZER_HEAD);
        imageShoot.setX(getPositionX());
        imageShoot.setY(getPositionY());

        this.slowness=getFreezerSlownessByLevel(level);
    }

    @Override
    public boolean fire(List<Enemy> enemies) {
        if (super.fire(enemies)) {
            gameActivity.runOnUiThread(() -> {
                gameActivity.getMapFrameLayout().addView(imageShoot);
            });

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    gameActivity.runOnUiThread(() -> {
                        gameActivity.getMapFrameLayout().removeView(imageShoot);
                    });
                }
            }, 500);

            new SnowFlake(getPosition(), super.targetedEnemy, this.getDamage(),this.getSlowness(), DRAWABLE_BULLET_FREEZER,  gameActivity);
            return true;
        }
        return false;
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
