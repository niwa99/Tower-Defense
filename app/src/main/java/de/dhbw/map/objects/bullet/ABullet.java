package de.dhbw.map.objects.bullet;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;

public abstract class ABullet {
    private ImageView bulletImage;
    private int x;
    private int y;
    private Position targetPos;
    protected Enemy targetEnemy;
    protected int damage;
    private GameActivity gameActivity;

    public ABullet(Position spawnPosition, Enemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        bulletImage = new ImageView(gameActivity);
        bulletImage.setImageResource(bulletImageID);
        bulletImage.setLayoutParams(BULLET_SIZE_PARAMS);

        this.targetEnemy = targetedEnemy;
        this.damage = damage;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);

        visualizeShot();
    }

    /**
     * Bullet Constructor for Test-Purpose only!
     */
    public ABullet(Position spawnPosition, Enemy targetedEnemy, int damage, ImageView image, GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.bulletImage = image;
        this.damage = damage;
        this.targetEnemy = targetedEnemy;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);
    }

    public void visualizeShot(){
        bulletImage.setRotation((float) getBulletRotation());
        gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().addView(bulletImage));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (move()) {
                    gameActivity.runOnUiThread(() -> {
                        bulletImage.setX(x);
                        bulletImage.setY(y);
                    });
                } else {
                    gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().removeView(bulletImage));
                    if (targetEnemy != null) { //Abfrage, falls in der Zwischenzeit der Tank getötet wurde
                        hitEnemy();
                        System.out.println(targetEnemy.getLabel() + " was shot by " + damage + " and has " + targetEnemy.getHealthPoints() + " hp left");
                    }
                    timer.cancel();
                }
            }
            },0, 1000-BULLET_SPEED);
    }

    protected void hitEnemy(){
        targetEnemy.hit(damage);
    }

    /**
     * The bullet path gets detected using vector-logic.
     * Just a small step (few pixels maximum) will be taken.
     * @return true if movement is successful
     */
    public boolean move(){
        if (!getPosition().equals(targetPos)) {
            int vector_x = targetPos.getX() - x;
            int vector_y = targetPos.getY() - y;
            final int vector_x_old = vector_x;
            final int vector_y_old = vector_y;

            if (Math.abs(vector_x_old) < Math.abs(vector_y_old)) {
                if (vector_x_old != 0) {
                    vector_x = vector_x_old / Math.abs(vector_x_old);
                    vector_y = vector_y_old / Math.abs(vector_x_old);
                } else {
                    if (vector_y_old > 0) {
                        vector_y--;
                        if (vector_y_old == 1) {
                            vector_y++;
                        }
                    } else {
                        vector_y++;
                        if (vector_y_old == -1) {
                            vector_y--;
                        }
                    }
                }
            } else {
                if (vector_y_old != 0) {
                    vector_y = vector_y_old / Math.abs(vector_y_old);
                    vector_x = vector_x_old / Math.abs(vector_y_old);
                } else {
                    if (vector_x_old > 0) {
                        vector_x--;
                        if (vector_x_old == 1) {
                            vector_x++;
                        }
                    } else {
                        vector_x++;
                        if (vector_x_old == -1) {
                            vector_x--;
                        }
                    }
                }
            }
            moveTo(x + vector_x, y + vector_y);
            return true;
        }
        return false;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position getPosition(){
        return new Position(x, y);
    }

    private void setMidpointOfPositions(Position targetPos, Position spawnPosition) {
        int mid_x = targetPos.getX() + (MapStructure.getSizeField()/2);
        int mid_y = targetPos.getY() + (MapStructure.getSizeField()/2);
        this.targetPos = new Position(mid_x, mid_y);

        this.x = spawnPosition.getX() + (MapStructure.getSizeField()/2);
        this.y = spawnPosition.getY() + (MapStructure.getSizeField()/2);
    }

    private double getBulletRotation(){
        double rotation = 0;
        double a = this.x - targetPos.getX();
        double b = this.y - targetPos.getY();
        rotation = Math.toDegrees(Math.atan2(b, a));
        return rotation + 180;
    }
}
