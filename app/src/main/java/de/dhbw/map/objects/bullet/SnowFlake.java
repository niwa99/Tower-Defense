package de.dhbw.map.objects.bullet;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

public class SnowFlake extends ABullet {
    private final int slowness;
    public SnowFlake(Position spawnPosition, Enemy targetedEnemy, int damage, int slowness, int bulletImageID, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity);
        this.slowness=slowness;
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
        targetEnemy.slowDown(slowness);
    }
}
