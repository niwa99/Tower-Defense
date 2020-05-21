package de.dhbw.map.objects.bullet;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

public class PlasmaBall extends ABullet {
    private List<Enemy> targetEnemies;
    private int range = 0;

    public PlasmaBall(Position spawnPosition, Enemy targetedEnemy, int damage, int range,  List<Enemy> targetEnemies, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, Constants.DRAWABLE_BULLET_PLASMARIZER, gameActivity, offset);
        this.targetEnemies = targetEnemies;
        this.range = range;
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
        int count = 0;
        while(targetEnemies.size()>count+1 && isNextEnemyInRange(count)){
            targetEnemy = targetEnemies.get(count++);
            super.hitEnemy();
        }

    }

    private void halfDamage(){
        this.damage=Math.round(damage/2);
    }

    private boolean isNextEnemyInRange(int count) {
        return targetEnemies.get(count).getProgress()-targetEnemies.get(count+1).getProgress() <range || targetEnemies.get(count+1).getProgress()-targetEnemies.get(count).getProgress() <range;
    }
}
