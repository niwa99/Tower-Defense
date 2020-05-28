package de.dhbw.map.objects.bullet;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;

public abstract class ABullet {

    private ImageView bulletImage;
    private int x;
    private int y;
    private Position targetPos;
    protected AEnemy targetEnemy;
    protected int x;
    protected int y;
    Position targetPos;
    final Enemy targetEnemy;
    protected int damage;
    protected GameActivity gameActivity;
    private int bulletSpeed = BULLET_SPEED;

    public ABullet(Position spawnPosition, AEnemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        this.gameActivity = gameActivity;
        bulletImage = new ImageView(gameActivity);
        bulletImage.setImageResource(bulletImageID);
        bulletImage.setLayoutParams(BULLET_SIZE_PARAMS);

        this.targetEnemy = targetedEnemy;
        this.damage = damage;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);
        bulletImage.setRotation((float) getBulletRotation());
        if (offset != 0) {
            applyBulletOffset(offset);
        }
    }

    /**
     * Bullet Constructor for Test-Purpose only!
     */
    public ABullet(Position spawnPosition, AEnemy targetedEnemy, int damage, ImageView image, GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.bulletImage = image;

        this.targetEnemy = targetedEnemy;
        this.damage = damage;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);
        bulletImage.setRotation((float) getBulletRotation());

        int distanceToEnemy = MatchField.getDistance(x, y, targetPos.getX(), targetPos.getY());
        startLogicalShooting(distanceToEnemy);
    }

    public void start() {
        int distanceToEnemy = MatchField.getDistance(x, y, targetPos.getX(), targetPos.getY());
        startAnimation(distanceToEnemy);
        startLogicalShooting(distanceToEnemy);
    }

    protected void startAnimation(int distanceToEnemy) {
        gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().addView(bulletImage));
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(bulletImage, "translationX", targetPos.getX());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(bulletImage, "translationY", targetPos.getY());
        animatorX.setDuration(bulletSpeed * distanceToEnemy);
        animatorY.setDuration(bulletSpeed * distanceToEnemy);
        gameActivity.runOnUiThread(() -> {
            animatorX.start();
            animatorY.start();
        });
    }

    protected void startLogicalShooting(int distanceToEnemy) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(() -> gameActivity.getMapFrameLayout().removeView(bulletImage));
                if (targetEnemy != null) { //Check if AEnemy got killed in the meantime
                    hitEnemy();
                    System.out.println(targetEnemy.getLabel() + " was shot by " + damage + " and has " + targetEnemy.getHealthPoints() + " hp left");
                }
            }
        }, bulletSpeed * distanceToEnemy);
    }

    public void setBulletSpeed(int speed) {
        this.bulletSpeed = speed;
    }

    public int getBulletSpeed() {
        return this.bulletSpeed;
    }

    protected void hitEnemy() {
        targetEnemy.hit(damage);
    }

    void applyBulletOffset(float offset) {
        float vector_y = (float) Math.cos(Math.toRadians(bulletImage.getRotation() - 90));
        float vector_x = (float) -Math.sin(Math.toRadians(bulletImage.getRotation() - 90));
        bulletImage.setX(bulletImage.getX() + (vector_x * offset));
        bulletImage.setY(bulletImage.getY() + (vector_y * offset));
    }

    double getBulletRotation() {
        double a = this.x - targetPos.getX();
        double b = this.y - targetPos.getY();
        double rotation = Math.toDegrees(Math.atan2(b, a));
        return rotation + 180;
    }

    protected void setMidpointOfPositions(Position targetPos, Position spawnPosition) {
        int mid_x = targetPos.getX() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        int mid_y = targetPos.getY() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        this.targetPos = new Position(mid_x, mid_y);

        this.x = spawnPosition.getX() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        this.y = spawnPosition.getY() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
    }
}
