package de.dhbw.map.objects.bullet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;
import pl.droidsonroids.gif.GifImageView;

import static de.dhbw.util.Constants.FIELD_SIZE;

public class LaserRay extends ABullet {
    private Canvas canvas;
    private LaserView laserView;
    private boolean isAlive = true;
    private boolean killBullet = false;
    private final List<AEnemy> allEnemies;
    private Handler handler;

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param allEnemies
     * @param damage
     * @param gameActivity
     * @param offset
     */
    public LaserRay(Position spawnPosition, AEnemy targetedEnemy, List<AEnemy> allEnemies, int damage, GameActivity gameActivity, int offset) {
        super(spawnPosition, targetedEnemy, damage, 0, gameActivity, offset);
        handler = gameActivity.getHandler();
        canvas = new Canvas();
        laserView = new LaserView(gameActivity, new Position(x,y), targetPos);
        laserView.setElevation(ImageElevation.BULLET.elevation);
        this.allEnemies=allEnemies;
    }

    @Override
    protected void hitEnemy() {
        super.hitEnemy();
    }

    @Override
    protected void startLogicalShooting(int distanceToEnemy) {
    }

    /**
     * If this method is called, the bullet will stop itself on next shooting.
     */
    public void killBullet() {
        this.killBullet = true;
    }

    @Override
    protected void startAnimation(int distanceToEnemy) {
        laserView.setBackgroundColor(Color.alpha(255));
        GameActivity.runActionOnUI(handler, UIActions.addView, laserView);

        allEnemies.remove(targetEnemy);
        Position bulletStartPos = new Position(x,y);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!targetEnemy.isAlive() || targetEnemy.isPaused() || killBullet || !isEnemyHitOnPosition(bulletStartPos, targetPos,targetEnemy)){
                    GameActivity.runActionOnUI(handler, UIActions.removeView, laserView);
                    isAlive=false;
                    cancel();
                }else{
                    targetEnemy.reduceHealthPoints(damage/4);
                    allEnemies.stream()
                            .filter(e -> ATower.filterPlaneIfTowerCannotFocus(TowerType.ASSAULTLASER, e))
                            .filter(e -> isEnemyHitOnPosition(bulletStartPos, targetPos, e)).forEach(e -> e.reduceHealthPoints(damage/4));
                    sparkle();
                }
            }
        }, 0,250);
    }

    private void sparkle() {
        if(gameActivity.getGame().isAnimationOn()) {
            GifImageView gif = new GifImageView(gameActivity);
            gif.setLayoutParams(gameActivity.getMapFrameLayout().getLayoutParams());
            gif.setX(targetPos.getX() - Math.round(gameActivity.getResources().getDisplayMetrics().widthPixels/2));
            gif.setY(targetPos.getY() - Math.round(gameActivity.getResources().getDisplayMetrics().heightPixels/2));
            gif.setScaleX(0.2f);
            gif.setScaleY(0.2f);
            gif.setImageResource(R.drawable.spark_animation);
            gif.setElevation(ImageElevation.ANIMATION.elevation);
            GameActivity.runActionOnUI(handler, UIActions.addView, gif);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    GameActivity.runActionOnUI(handler, UIActions.removeView, gif);
                    gif.clearAnimation();
                }
            }, 250);
        }
    }

    @Override
    void applyBulletOffset(float offset) {
        float vector_y = getVectorY();
        float vector_x = getVectorX();
        this.x = (int) (x + vector_x * offset);
        this.y = (int) (y + vector_y * offset);
    }

    /**
     *
     * @return x vector value of the laserRay
     */
    private float getVectorX() {
        return (float) -Math.sin(Math.toRadians(getBulletRotation() - 90));
    }

    /**
     *
     * @return y vector value of the laserRay
     */
    private float getVectorY() {
        return (float) Math.cos(Math.toRadians(getBulletRotation() - 90));
    }

    /**
     * Checks if an enemy could be hit by the bullet.
     * @param bulletStartPosition
     * @param bulletEndPosition
     * @param enemy
     * @return true if enemy could be hit
     */
    public static boolean isEnemyHitOnPosition(Position bulletStartPosition, Position bulletEndPosition, AEnemy enemy){
        int halfSize = Constants.FIELD_SIZE/2;
        int enemyX = enemy.getPositionX();
        int enemyY = enemy.getPositionY();

        int FIELD_SIZE = 100;

        boolean left =   doIntersect(bulletStartPosition, bulletEndPosition, new Position(enemyX, enemyY),new Position(enemyX, enemyY+FIELD_SIZE));
        boolean right =  doIntersect(bulletStartPosition, bulletEndPosition, new Position(enemyX+FIELD_SIZE, enemyY),new Position(enemyX+FIELD_SIZE, enemyY+FIELD_SIZE));
        boolean top =   doIntersect(bulletStartPosition, bulletEndPosition, new Position(enemyX, enemyY),new Position(enemyX+FIELD_SIZE, enemyY));
        boolean bottom =  doIntersect(bulletStartPosition, bulletEndPosition, new Position(enemyX, enemyY+FIELD_SIZE),new Position(enemyX+FIELD_SIZE, enemyY+FIELD_SIZE));

        // if ANY of the above are true, the line
        // has hit the rectangle
        if (left || right || top || bottom) {
            return true;
        }
        return false;

    }

    /**
     *
     * @return true if laserRay is alive
     */
    public boolean isAlive(){
        return isAlive;
    }


    @Override
    protected void setMidpointOfPositions(Position targetPos, Position spawnPosition) {
        int mid_x = targetPos.getX() + (FIELD_SIZE/2);
        int mid_y = targetPos.getY() + (FIELD_SIZE/2);
        this.targetPos = new Position(mid_x, mid_y);

        this.x = spawnPosition.getX() + (FIELD_SIZE/2);
        this.y = spawnPosition.getY() + (FIELD_SIZE/2);
    }

    /**
     * Method for hitbox calculation.
     * @param p1
     * @param q1
     * @param p2
     * @param q2
     * @return
     */
    static boolean doIntersect(Position p1, Position q1, Position p2, Position q2) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    /**
     * Method for hitbox calculation.
     * @param p
     * @param q
     * @param r
     * @return
     */
    static int orientation(Position p, Position q, Position r)
    {
        int val = (q.getY()- p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    /**
     * Method for hitbox calculation.
     * @param p
     * @param q
     * @param r
     * @return
     */
    static boolean onSegment(Position p, Position q, Position r)
    {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
            return true;

        return false;
    }

    /**
     * This View-class is responsible for the laserRay to be drawn.
     */
    private class LaserView extends View {
        Paint paint = new Paint();
        private Position from;
        private Position to;

        /**
         * Constructor
         * @param a
         * @param from
         * @param to
         */
        public LaserView(Activity a, Position from, Position to){
            super(a);
            this.from = from;
            this.to = to;
        }

        /**
         * Set an initial color to the view.
         */
        private void init() {
            paint.setColor(Color.BLACK);
        }

        /**
         * Constructor
         * @param context
         */
        public LaserView(Context context) {
            super(context);
            init();
        }

        /**
         * Constructor
         * @param context
         * @param attrs
         */
        public LaserView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        /**
         * Constructor
         * @param context
         * @param attrs
         * @param defStyle
         */
        public LaserView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        @Override
        public void onDraw(Canvas canvas) {
            paint.setStrokeWidth(7);
            switch(new Random().nextInt(5)){
                case 0:
                    paint.setColor(Color.BLUE);
                    break;
                case 1:
                    paint.setColor(Color.RED);
                    break;
                case 2:
                    paint.setColor(Color.YELLOW);
                    break;
                case 3:
                    paint.setColor(Color.GREEN);
                    break;
                case 4:
                    paint.setColor(Color.WHITE);
                    break;
            }
            canvas.drawLine(from.getX(), from.getY(), to.getX(), to.getY(), paint);
        }

        /**
         * Redraw the view.
         * @param canvas
         * @param color
         */
        public void reDraw(Canvas canvas, int color) {
            paint.setColor(color);
            canvas.drawLine(from.getX(), from.getY(), to.getX(), to.getY(), paint);
        }

    }
}
