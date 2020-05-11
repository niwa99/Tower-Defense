package de.dhbw.map.objects.bullet;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.util.Position;

public class Projectile extends ABullet {
    public Projectile(Position spawnPosition, Enemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity);
    }
}
