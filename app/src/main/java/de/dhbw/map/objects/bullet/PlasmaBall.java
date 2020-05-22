package de.dhbw.map.objects.bullet;

import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.BULLET_SIZE_PARAMS;
import static de.dhbw.util.Constants.FIELD_SIZE;

public class PlasmaBall extends ABullet {
    private List<Enemy> targetEnemies;
    private int range;

    public PlasmaBall(Position spawnPosition, Enemy targetedEnemy, int damage, int range,  List<Enemy> targetEnemies, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, Constants.DRAWABLE_BULLET_PLASMARIZER, gameActivity, offset);
        this.targetEnemies = targetEnemies;
        this.range = range;
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();

        targetEnemies.remove(targetEnemy);

        Enemy nextEnemy = null;
        for (int i = 0; i < targetEnemies.size(); i++) {
            if (targetEnemies.get(i) != null && isNextEnemyInRange(targetEnemy, targetEnemies.get(i))) {
                nextEnemy = targetEnemies.get(i);
                break;
            }
        }
        if (nextEnemy != null && damage/2 != 0) {
            int newSpawnPositionX = targetPos.getX() - FIELD_SIZE/2 + BULLET_SIZE_PARAMS.height/2;
            int newSpawnPositionY = targetPos.getY() - FIELD_SIZE/2 + BULLET_SIZE_PARAMS.height/2;
            Position newSpawnPosition = new Position(newSpawnPositionX, newSpawnPositionY);
            new PlasmaBall(newSpawnPosition, nextEnemy, damage/2, range, targetEnemies, gameActivity, 0);
        }

    }

    private boolean isNextEnemyInRange(Enemy oldEnemy, Enemy nextEnemy) {
        return (Math.abs(oldEnemy.getProgress() - nextEnemy.getProgress()) < range);
    }
}
