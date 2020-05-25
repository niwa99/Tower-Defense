package de.dhbw.map.objects.bullet;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.util.Position;

public class SnowFlake extends ABullet {

    private final int slowness;

    public SnowFlake(Position spawnPosition, AEnemy targetedEnemy, int damage, int slowness, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
        this.slowness=slowness;
    }

    public SnowFlake(Position spawnPosition, AEnemy targetedEnemy, int damage, int slowness, int bulletImageID, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, damage, slowness, bulletImageID, gameActivity, 0);
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
        targetEnemy.slowDown(slowness);
    }
}
