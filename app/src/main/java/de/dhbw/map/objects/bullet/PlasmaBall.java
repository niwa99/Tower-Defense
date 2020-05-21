package de.dhbw.map.objects.bullet;

import android.graphics.drawable.Drawable;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

public class PlasmaBall extends ABullet {
    public PlasmaBall(Position spawnPosition, Enemy targetedEnemy, int damage, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, Constants.DRAWABLE_BULLET_PLASMARIZER, gameActivity, offset);
    }

    @Override
    protected void hitEnemy(){
        super.hitEnemy();
    }
}
