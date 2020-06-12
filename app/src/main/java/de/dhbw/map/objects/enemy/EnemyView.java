package de.dhbw.map.objects.enemy;

import android.os.Message;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;
import de.dhbw.util.Direction;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.ENEMY_LAYOUT_SIZE_PARAMS;

public class EnemyView {
    private ProgressBar lifeBar;
    private ImageView image;
    private RelativeLayout enemyLayout;

    private int enemyImageID;
    private int enemyHitImageID;

    private Timer timer = new Timer();

    private Handler handler;

    public EnemyView(){

    }

    public EnemyView(RelativeLayout enemyLayout, Handler handler, int enemyImageID, int enemyHitImageID, int healthPoints){
        this.enemyImageID = enemyImageID;
        this.enemyHitImageID = enemyHitImageID;
        this.enemyLayout=enemyLayout;
        this.handler=handler;
        createEnemyLayoutWithLifeBar(healthPoints);
    }

    private void createEnemyLayoutWithLifeBar(int healthPoints){
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
        GameActivity.runActionOnUI(handler, UIActions.moveImage, enemyLayout, x, y);
        switch (direction) {
            case UP:
                GameActivity.runActionOnUI(handler, UIActions.rotateImage, enemyLayout, 270);
                break;
            case RIGHT:
                GameActivity.runActionOnUI(handler, UIActions.rotateImage, enemyLayout, 0);
                break;
            case DOWN:
                GameActivity.runActionOnUI(handler, UIActions.rotateImage, enemyLayout, 90);
                break;
            case LEFT:
                GameActivity.runActionOnUI(handler, UIActions.rotateImage, enemyLayout, 180);
                break;
        }
    }

    private void setImageResource(View layout, int id){
        Message msg = new Message();
        msg.what= UIActions.setImageResource.getId();
        msg.obj=layout;
        msg.arg1=id;
        handler.sendMessage(msg);
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
        GameActivity.runActionOnUI(handler, UIActions.setImageResource, image, enemyHitImageID);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameActivity.runActionOnUI(handler, UIActions.setImageResource, image, enemyImageID);
            }
        }, 100);
    }
}
