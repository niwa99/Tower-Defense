package de.dhbw.map.objects.bullet;

import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.BULLET_SIZE_PARAMS;
import static de.dhbw.util.Constants.FIELD_SIZE;

public class PlasmaBall extends ABullet {
    private List<Enemy> enemies;
    private int range;

    public PlasmaBall(Position spawnPosition, Enemy targetedEnemy, int damage, int range,  List<Enemy> enemies, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, Constants.DRAWABLE_BULLET_PLASMARIZER, gameActivity, offset);
        this.enemies = enemies;
        this.range = range;
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();

        enemies.remove(targetEnemy);

        Enemy nextEnemy = null;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) != null && isNextEnemyInRange(targetEnemy, enemies.get(i))) {
                if (nextEnemy == null) {
                    nextEnemy = enemies.get(i);
                } else if (Math.abs(nextEnemy.getMovedSteps() - targetEnemy.getMovedSteps()) > Math.abs(enemies.get(i).getMovedSteps() - targetEnemy.getMovedSteps())) {
                    nextEnemy = enemies.get(i);
                }
            }
        }
        if (nextEnemy != null && damage/2 != 0) {
            int newSpawnPositionX = targetPos.getX() - FIELD_SIZE/2 + BULLET_SIZE_PARAMS.height/2;
            int newSpawnPositionY = targetPos.getY() - FIELD_SIZE/2 + BULLET_SIZE_PARAMS.height/2;
            Position newSpawnPosition = new Position(newSpawnPositionX, newSpawnPositionY);

            ABullet plasmaBall = new PlasmaBall(newSpawnPosition, nextEnemy, damage/2, range, enemies, gameActivity, 0);
            plasmaBall.setBulletSpeed(plasmaBall.getBulletSpeed()*2);
            plasmaBall.start();
        }

    }

    private boolean isNextEnemyInRange(Enemy oldEnemy, Enemy nextEnemy) {
        return Math.abs(oldEnemy.getMovedSteps() - nextEnemy.getMovedSteps()) < range;
    }
}
