package de.dhbw.map.objects.bullet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.BULLET_SIZE_PARAMS;
import static de.dhbw.util.Constants.FIELD_SIZE;

public class PlasmaBall extends ABullet {

    private List<AEnemy> enemies;
    private int range;

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param range
     * @param enemies
     * @param gameActivity
     * @param offset
     */
    public PlasmaBall(Position spawnPosition, AEnemy targetedEnemy, int damage, int range,  List<AEnemy> enemies, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, Constants.DRAWABLE_BULLET_PLASMARIZER, gameActivity, offset);
        this.enemies = enemies;
        this.range = range;
    }

    /**
     * Constructor (with offset = 0)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param range
     * @param enemies
     * @param gameActivity
     */
    public PlasmaBall(Position spawnPosition, AEnemy targetedEnemy, int damage, int range,  List<AEnemy> enemies, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, damage, range, enemies, gameActivity, 0);
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();

        enemies.remove(targetEnemy);
        enemies = new ArrayList<>(enemies).stream().filter(e -> ATower.filterPlaneIfTowerCannotFocus(TowerType.PLASMARIZER, e)).collect(Collectors.toList());

        AEnemy nextEnemy = null;
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

    /**
     * Checks if the nextEnemy is in plasmaRange of the oldEnemy.
     * @param oldEnemy
     * @param nextEnemy
     * @return true if nextEnemy is in range
     */
    private boolean isNextEnemyInRange(AEnemy oldEnemy, AEnemy nextEnemy) {
        return Math.abs(oldEnemy.getMovedSteps() - nextEnemy.getMovedSteps()) < range;
    }
}
