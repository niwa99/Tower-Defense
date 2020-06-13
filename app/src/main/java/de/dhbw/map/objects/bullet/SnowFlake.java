package de.dhbw.map.objects.bullet;

import android.widget.ImageView;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.util.Position;

public class SnowFlake extends ABullet {

    private final int slowness;

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param slowness
     * @param bulletImageID
     * @param gameActivity
     * @param offset
     */
    public SnowFlake(Position spawnPosition, AEnemy targetedEnemy, int damage, int slowness, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
        this.slowness=slowness;
    }

    /**
     * Constructor (with offset = 0)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param slowness
     * @param bulletImageID
     * @param gameActivity
     */
    public SnowFlake(Position spawnPosition, AEnemy targetedEnemy, int damage, int slowness, int bulletImageID, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, damage, slowness, bulletImageID, gameActivity, 0);
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param slowness
     * @param bulletImage
     */
    public SnowFlake(Position spawnPosition, AEnemy targetedEnemy, int damage, int slowness, ImageView bulletImage) {
        super(spawnPosition, targetedEnemy, damage, bulletImage);
        this.slowness = slowness;
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
        targetEnemy.slowDown(slowness);
    }
}
