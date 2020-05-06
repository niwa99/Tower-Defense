package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;

public class Bullet {
    private ImageView bulletImage;
    private int x;
    private int y;
    private Position targetPos;
    private Enemy targetEnemy;
    private DefTower tower;
    private GameActivity gameActivity;

    public Bullet(Position spawnPosition, Enemy targetedEnemy, DefTower tower, GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        bulletImage = new ImageView(gameActivity);
        bulletImage.setImageResource(DRAWABLE_BULLET);
        bulletImage.setLayoutParams(BULLET_SIZE_PARAMS);

        this.targetEnemy = targetedEnemy;
        this.tower = tower;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);

        visualizeShot();
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
                        if (targetEnemy instanceof Tank) {
                            ((Tank) targetEnemy).hit(tower.getDamage());
                            gameActivity.getGame().getMatchField().removeDeadEnemy(targetEnemy);
                            System.out.println(targetEnemy.getLabel() + " was shot by " + tower.getDamage() + " and has " + targetEnemy.getHealthPoints() + " hp left");
                        }
                    }
                    timer.cancel();
                }
            }
            },0, 1000-BULLET_SPEED);
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
