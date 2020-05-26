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
import java.util.Vector;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.FIELD_SIZE;

public class LaserRay extends ABullet {
    private Canvas canvas;
    private LaserView laserView;

    public LaserRay(Position spawnPosition, Enemy targetedEnemy, int damage, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, 0, gameActivity, offset);
        canvas = new Canvas();
        laserView = new LaserView(gameActivity, new Position(x,y), targetPos);
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
        gameActivity.runOnUiThread(() -> {
            laserView.setBackgroundColor(Color.alpha(255));
            gameActivity.getMapFrameLayout().addView(laserView);
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!isEnemyHittedOnPosition(new Position(x,y), targetPos,targetEnemy)){
                    gameActivity.runOnUiThread(() -> {
                        gameActivity.getMapFrameLayout().removeView(laserView);
                    });
                    cancel();
                }else{
                    laserView.reDraw(canvas, Color.RED);
                }
            }
        }, 0,250);
    }

    @Override
    void applyBulletOffset(float offset) {
        float vector_y = getVectorY();
        float vector_x = getVectorX();
        this.x = (int) (x + vector_x * offset);
        this.y = (int) (y + vector_y * offset);
    }

    private float getVectorX() {
        return (float) -Math.sin(Math.toRadians(getBulletRotation() - 90));
    }

    private float getVectorY() {
        return (float) Math.cos(Math.toRadians(getBulletRotation() - 90));
    }

    public boolean isEnemyHittedOnPosition(Position bulletStartPosition, Position bulletEndPosition, Enemy enemy){
        int size = Constants.FIELD_SIZE/2;
        return lineRect(bulletStartPosition.getX(), bulletStartPosition.getY(), bulletEndPosition.getX(), bulletEndPosition.getY(), enemy.getPositionX()-size, enemy.getPositionY()-size, enemy.getPositionX()+size, enemy.getPositionY()+size);
    }


    @Override
    protected void setMidpointOfPositions(Position targetPos, Position spawnPosition) {
        int mid_x = targetPos.getX() + (FIELD_SIZE/2);
        int mid_y = targetPos.getY() + (FIELD_SIZE/2);
        this.targetPos = new Position(mid_x, mid_y);

        this.x = spawnPosition.getX() + (FIELD_SIZE/2);
        this.y = spawnPosition.getY() + (FIELD_SIZE/2);
    }

    boolean lineRect(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {

        // check if the line has hit any of the rectangle's sides
        // uses the Line/Line function below
        boolean left =   lineLine(x1,y1,x2,y2, rx,ry,rx, ry+rh);
        boolean right =  lineLine(x1,y1,x2,y2, rx+rw,ry, rx+rw,ry+rh);
        boolean top =    lineLine(x1,y1,x2,y2, rx,ry, rx+rw,ry);
        boolean bottom = lineLine(x1,y1,x2,y2, rx,ry+rh, rx+rw,ry+rh);

        // if ANY of the above are true, the line
        // has hit the rectangle
        if (left || right || top || bottom) {
            return true;
        }
        return false;
    }

    boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

        // calculate the direction of the lines
        float uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
        float uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            return true;
        }
        return false;
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
            paint.setStrokeWidth(7);
            paint.setColor(Color.BLUE);
            canvas.drawLine(from.getX(), from.getY(), to.getX(), to.getY(), paint);
        }

        public void reDraw(Canvas canvas, int color) {
            paint.setColor(color);
            canvas.drawLine(from.getX(), from.getY(), to.getX(), to.getY(), paint);
        }

    }
}
