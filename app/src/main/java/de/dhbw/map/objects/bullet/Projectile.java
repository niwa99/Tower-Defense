package de.dhbw.map.objects.bullet;

import android.widget.ImageView;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.util.Position;

public class Projectile extends ABullet {

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param bulletImageID
     * @param gameActivity
     * @param offset
     */
    public Projectile(Position spawnPosition, AEnemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
    }

    /**
     * Constructor (with offset = 0)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param bulletImageID
     * @param gameActivity
     */
    public Projectile(Position spawnPosition, AEnemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, 0);
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param bulletImage
     */
    public Projectile(Position spawnPosition, AEnemy targetedEnemy, int damage, ImageView bulletImage) {
        super(spawnPosition, targetedEnemy, damage, bulletImage);
    }
}
