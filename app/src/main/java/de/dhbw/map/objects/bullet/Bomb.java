package de.dhbw.map.objects.bullet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;
import static de.dhbw.util.Constants.BOMB_RANGE;
import static de.dhbw.util.Constants.FIELD_SIZE;

public class Bomb extends ABullet {
    private List<Enemy> allEnemies;
    private final int range = BOMB_RANGE;

    public Bomb(Position spawnPosition, Enemy targetedEnemy, List<Enemy> allEnemies, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
        this.allEnemies = allEnemies;
    }

    public Bomb(Position spawnPosition, Enemy targetedEnemy, List<Enemy> allEnemies, int damage, int bulletImageID, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, allEnemies, damage, bulletImageID, gameActivity, 0);
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();
        getEnemiesToHit().entrySet().stream().forEach(e -> e.getKey().hit(e.getValue()));
    }

    public Map<Enemy, Integer> getEnemiesToHit() {
        Map<Enemy, Integer> enemiesInExplosionRange = new HashMap<Enemy, Integer>();
        targetEnemy.getPosition();
        for (Enemy e : allEnemies) {
            int distance = MatchField.getDistance(targetEnemy.getPositionX(), targetEnemy.getPositionY(), e.getPositionX(), e.getPositionY());
            if(distance < range){
                if(distance < range/2){
                    enemiesInExplosionRange.put(e, Math.round(damage/2));
                }else{
                    enemiesInExplosionRange.put(e, Math.round(damage/4));
                }
            }
        }
        return enemiesInExplosionRange;
    }
}
