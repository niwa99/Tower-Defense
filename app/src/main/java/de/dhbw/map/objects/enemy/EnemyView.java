package de.dhbw.map.objects.enemy;

import android.text.Layout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.util.Direction;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.ENEMY_LAYOUT_SIZE_PARAMS;

public class EnemyView {
    private ProgressBar lifeBar;
    private ImageView image;
    private RelativeLayout enemyLayout;

    private GameActivity gameActivity;

    private int enemyImageID;
    private int enemyHitImageID;

    private Timer timer = new Timer();

    public EnemyView(){

    }

    public EnemyView(GameActivity gameActivity, int enemyImageID, int enemyHitImageID, int healthPoints){
        this.enemyImageID = enemyImageID;
        this.enemyHitImageID = enemyHitImageID;
        this.gameActivity = gameActivity;
        createEnemyLayoutWithLifeBar(healthPoints);
    }

    private void createEnemyLayoutWithLifeBar(int healthPoints){
        enemyLayout = (RelativeLayout) gameActivity.getLayoutInflater().inflate(R.layout.enemy_layout, null);
        enemyLayout.setLayoutParams(ENEMY_LAYOUT_SIZE_PARAMS);
        enemyLayout.setElevation(ImageElevation.ENEMIES.elevation);
        lifeBar = (ProgressBar) enemyLayout.getChildAt(0);
        lifeBar.setMax(healthPoints);
        lifeBar.setProgress(healthPoints);
        lifeBar.setElevation(ImageElevation.LIFE_BAR.elevation);
        image = (ImageView) enemyLayout.getChildAt(1);
        image.setImageResource(enemyImageID);
    }

    /**
     * Set the rotation of the enemy image according to its direction.
     */
    protected void resolveRotation(Direction direction, int x, int y) {
        enemyLayout.setX(x);
        enemyLayout.setY(y);
        switch (direction) {
            case UP:
                enemyLayout.setRotation(-90);
                break;
            case RIGHT:
                enemyLayout.setRotation(0);
                break;
            case DOWN:
                enemyLayout.setRotation(90);
                break;
            case LEFT:
                enemyLayout.setRotation(-180);
                break;
        }
    }

    public void setHealthProgress(int healthPoints){
        lifeBar.setProgress(healthPoints);
    }

    public RelativeLayout getLayout(){
        return enemyLayout;
    }

    public void setPosition(Position pos){
        enemyLayout.setX(pos.getX());
        enemyLayout.setY(pos.getY());
    }

    public void hitAnimation(){
        gameActivity.setImageResource(image, enemyHitImageID);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameActivity.setImageResource(image, enemyImageID);
            }
        }, 100);
    }
}
