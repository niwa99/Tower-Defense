package de.dhbw.map.objects.bullet;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

public class LaserRay extends ABullet {
    public LaserRay(Position spawnPosition, Enemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
    }
}
