package de.dhbw.map.objects.bullet;

import android.widget.ImageView;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

public class Projectile extends ABullet {
    public Projectile(Position spawnPosition, Enemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity);
    }

    public Projectile(Position spawnPosition, Enemy targetedEnemy, int damage, ImageView bulletImage, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImage, gameActivity);
    }
}
