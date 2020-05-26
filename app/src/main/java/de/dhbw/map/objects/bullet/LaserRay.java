package de.dhbw.map.objects.bullet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

public class LaserRay extends ABullet {
    public LaserRay(Position spawnPosition, Enemy targetedEnemy, int damage, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, 0, gameActivity, offset);
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();
    }

    @Override
    protected void startLogicalShooting(int distanceToEnemy) {
    }

    @Override
    protected void startAnimation(int distanceToEnemy) {
        Canvas c = new Canvas();
        LaserView view = new LaserView(gameActivity, new Position(x,y), targetPos);
        gameActivity.runOnUiThread(() -> {
            view.setBackgroundColor(Color.alpha(255));
            gameActivity.getMapFrameLayout().addView(view);
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!targetEnemy.isHittedOnPosition(targetPos)){
                    gameActivity.runOnUiThread(() -> {
                        gameActivity.getMapFrameLayout().removeView(view);
                    });
                    cancel();
                }
            }
        }, 0,250);
    }

    @Override
    void applyBulletOffset(float offset) {
        float vector_y = (float) Math.cos(Math.toRadians(getBulletRotation() - 90));
        float vector_x = (float) -Math.sin(Math.toRadians(getBulletRotation() - 90));
        this.x = (int) (x + vector_x * offset);
        this.y = (int) (y + vector_y * offset);
    }

    private class LaserView extends View {
        Paint paint = new Paint();
        private Position from;
        private Position to;

        public LaserView(Activity a, Position from, Position to){
            super(a);
            this.from = from;
            this.to = to;
        }

        private void init() {
            paint.setColor(Color.BLACK);
        }

        public LaserView(Context context) {
            super(context);
            init();
        }

        public LaserView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public LaserView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        @Override
        public void onDraw(Canvas canvas) {
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            canvas.drawLine(from.getX(), from.getY(), to.getX(), to.getY(), paint);
        }
    }
}
