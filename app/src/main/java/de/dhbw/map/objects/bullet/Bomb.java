package de.dhbw.map.objects.bullet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;
import static de.dhbw.util.Constants.BOMB_RANGE;

public class Bomb extends ABullet {
    private List<Enemy> allEnemies;
    private final int range = BOMB_RANGE;

    public Bomb(Position spawnPosition, Enemy targetedEnemy, List<Enemy> allEnemies, int damage, int bulletImageID, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity);
        this.allEnemies =allEnemies;
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
        getEnemiesToHit().entrySet().stream().forEach(e -> e.getKey().hit(e.getValue()));
    }

    public Map<Enemy, Integer> getEnemiesToHit() {
        Map<Enemy, Integer> enemiesInExplosionRange = new HashMap<Enemy, Integer>();
        targetEnemy.getPosition();
        for (Enemy e : allEnemies) {
            int distance = getDistance(targetEnemy.getPosition(), e.getPosition());
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

    public int getDistance(Position pos, Position enemyPos) {
        return (int) Math.round(Math.sqrt(Math.pow(Math.abs(enemyPos.getX()-pos.getX()), 2) + Math.pow(Math.abs(enemyPos.getY()-pos.getY()), 2)));
    }
}
