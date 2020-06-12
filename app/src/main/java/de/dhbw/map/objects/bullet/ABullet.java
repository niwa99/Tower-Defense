package de.dhbw.map.objects.bullet;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.ImageElevation;
import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.util.ObjectType;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;

public abstract class ABullet {

    private ImageView bulletImage;
    Position targetPos;
    protected AEnemy targetEnemy;
    protected int x;
    protected int y;
    protected int damage;
    protected GameActivity gameActivity;
    private int bulletSpeed = BULLET_SPEED;
    private Handler handler;

    /**
     * Constructor
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param bulletImageID
     * @param gameActivity
     * @param offset
     */
    public ABullet(Position spawnPosition, AEnemy targetedEnemy, int damage, int bulletImageID, GameActivity gameActivity, int offset) {
        this.gameActivity = gameActivity;
        handler = gameActivity.getHandler();
        bulletImage = new ImageView(gameActivity);
        bulletImage.setImageResource(bulletImageID);
        bulletImage.setLayoutParams(BULLET_SIZE_PARAMS);

        this.targetEnemy = targetedEnemy;
        this.damage = damage;

        setMidpointOfPositions(targetedEnemy.getPosition(), spawnPosition);
        bulletImage.setX(x);
        bulletImage.setY(y);
        bulletImage.setRotation((float) getBulletRotation());
        bulletImage.setElevation(ImageElevation.BULLET.elevation);
        if (offset != 0) {
            applyBulletOffset(offset);
        }
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     * @param spawnPosition
     * @param targetedEnemy
     * @param damage
     * @param image
     * @param gameActivity
     */
    public ABullet(Position spawnPosition, AEnemy targetedEnemy, int damage, ImageView image, GameActivity gameActivity) {
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

    /**
     * Start the animation- and shooting-mechanism.
     */
    public void start() {
        int distanceToEnemy = MatchField.getDistance(x, y, targetPos.getX(), targetPos.getY());
        startAnimation(distanceToEnemy);
        startLogicalShooting(distanceToEnemy);
    }

    /**
     * Performs the visual animation of the bullet.
     * @param distanceToEnemy
     */
    protected void startAnimation(int distanceToEnemy) {
        GameActivity.runActionOnUI(handler, UIActions.addView, bulletImage, ObjectType.BULLET);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(bulletImage, "translationX", targetPos.getX());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(bulletImage, "translationY", targetPos.getY());
        animatorX.setDuration(bulletSpeed * distanceToEnemy);
        animatorY.setDuration(bulletSpeed * distanceToEnemy);
        GameActivity.runActionOnUI(handler, UIActions.startAnimator, animatorX);
        GameActivity.runActionOnUI(handler, UIActions.startAnimator, animatorY);
    }

    /**
     * Performs the logical stuff of shooting the bullet.
     * @param distanceToEnemy
     */
    protected void startLogicalShooting(int distanceToEnemy) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameActivity.runActionOnUI(handler, UIActions.removeView, bulletImage);
                if (targetEnemy != null) { //Check if AEnemy got killed in the meantime
                    hitEnemy();
                    System.out.println(targetEnemy.getLabel() + " was shot by " + damage + " and has " + targetEnemy.getHealthPoints() + " hp left");
                }
            }
        }, bulletSpeed * distanceToEnemy);
    }

    /**
     * Set the bullet speed.
     * @param speed
     */
    public void setBulletSpeed(int speed) {
        this.bulletSpeed = speed;
    }

    /**
     *
     * @return bullet speed
     */
    public int getBulletSpeed() {
        return this.bulletSpeed;
    }

    /**
     * Hit the targetEnemy with specified damage.
     */
    protected void hitEnemy() {
        targetEnemy.reduceHealthPoints(damage);
    }

    /**
     * Applies an offset for the start location of the bullet.
     * For example, if a tower has got a pipe, the bullet should not start in the middle of the field, it should start 50px apart.
     * This is the value to pass on to this method.
     * @param offset
     */
    void applyBulletOffset(float offset) {
        float vector_y = (float) Math.cos(Math.toRadians(bulletImage.getRotation() - 90));
        float vector_x = (float) -Math.sin(Math.toRadians(bulletImage.getRotation() - 90));
        bulletImage.setX(bulletImage.getX() + (vector_x * offset));
        bulletImage.setY(bulletImage.getY() + (vector_y * offset));
    }

    /**
     *
     * @return bullet rotation in degrees
     */
    double getBulletRotation() {
        double a = this.x - targetPos.getX();
        double b = this.y - targetPos.getY();
        double rotation = Math.toDegrees(Math.atan2(b, a));
        return rotation + 180;
    }

    /**
     * Set the midpoints of the targetPosition and the spawnPosition.
     * @param targetPos
     * @param spawnPosition
     */
    protected void setMidpointOfPositions(Position targetPos, Position spawnPosition) {
        int mid_x = targetPos.getX() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        int mid_y = targetPos.getY() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        this.targetPos = new Position(mid_x, mid_y);

        this.x = spawnPosition.getX() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
        this.y = spawnPosition.getY() + (FIELD_SIZE/2) - (BULLET_SIZE_PARAMS.height/2);
    }
}
