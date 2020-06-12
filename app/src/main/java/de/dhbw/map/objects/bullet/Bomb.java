package de.dhbw.map.objects.bullet;

import android.os.Handler;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.util.Position;
import pl.droidsonroids.gif.GifImageView;

import static de.dhbw.util.Constants.BOMB_RANGE;

public class Bomb extends ABullet {

    private List<AEnemy> allEnemies;
    private final int range = BOMB_RANGE;
    private Handler handler;

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param allEnemies
     * @param damage
     * @param bulletImageID
     * @param gameActivity
     * @param offset
     */
    public Bomb(Position spawnPosition, AEnemy targetedEnemy, List<AEnemy> allEnemies, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, bulletImageID, gameActivity, offset);
        handler = gameActivity.getHandler();
        this.allEnemies = allEnemies;
    }

    /**
     * Constructor (with offset = 0)
     * @param spawnPosition
     * @param targetedEnemy
     * @param allEnemies
     * @param damage
     * @param bulletImageID
     * @param gameActivity
     */
    public Bomb(Position spawnPosition, AEnemy targetedEnemy, List<AEnemy> allEnemies, int damage, int bulletImageID, GameActivity gameActivity) {
        this(spawnPosition, targetedEnemy, allEnemies, damage, bulletImageID, gameActivity, 0);
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param spawnPosition
     * @param targetedEnemy
     * @param allEnemies
     * @param damage
     * @param bulletImage
     * @param gameActivity
     */
    public Bomb(Position spawnPosition, AEnemy targetedEnemy, List<AEnemy> allEnemies, int damage, ImageView bulletImage, GameActivity gameActivity) {
        super(spawnPosition, targetedEnemy, damage, bulletImage, gameActivity);
        this.allEnemies = allEnemies;
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();
        //explode();
        allEnemies.remove(targetEnemy);
        getEnemiesToHit().entrySet().stream().forEach(e -> e.getKey().reduceHealthPoints(e.getValue()));
    }

    /**
     * Checks allEnemies for being in range of the targetedEnemy and calculates the damage for the enemies in range.
     * @return map of enemies which are in range of the targetedEnemy
     */
    public Map<AEnemy, Integer> getEnemiesToHit() {
        Map<AEnemy, Integer> enemiesInExplosionRange = new HashMap<AEnemy, Integer>();
        targetEnemy.getPosition();
        List<AEnemy> hitableEnemies = allEnemies.stream().filter(e -> ATower.filterPlaneIfTowerCannotFocus(TowerType.BOOMBASTIC, e)).collect(Collectors.toList());
        for (AEnemy e : hitableEnemies) {
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

    private void explode() {
        if(gameActivity.getGame().isAnimationOn()) {
            GifImageView gif = new GifImageView(gameActivity);
            gif.setLayoutParams(gameActivity.getMapFrameLayout().getLayoutParams());
            gif.setX(targetPos.getX() - Math.round(gameActivity.getResources().getDisplayMetrics().widthPixels/2.05));
            gif.setY(targetPos.getY() - Math.round(gameActivity.getResources().getDisplayMetrics().heightPixels/1.9));
            gif.setScaleX(0.15f);
            gif.setScaleY(0.15f);
            gif.setImageResource(R.drawable.explosion_gif);
            GameActivity.runActionOnUI(handler, UIActions.addView, gif);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    GameActivity.runActionOnUI(handler, UIActions.removeView, gif);
                }
            }, 500);
        }
    }
}
