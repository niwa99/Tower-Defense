package de.dhbw.map.objects.tower;

import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.util.Position;

public class Bullet {
    private ImageView image;
    private int x;
    private int y;
    private Position targetPos;
    private int speed;

    public Bullet( ImageView image, Position spawnPos, Position targetPos, int speed){
        this.image=image;
        this.x=spawnPos.getX();
        this.y=spawnPos.getY();
        this.targetPos=targetPos;
        this.speed=speed;
        image.setX(x);
        image.setY(y);
    }

    public void visualizeShot(){
        Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        if (move()) {
                            image.setX(x);
                            image.setY(y);
                        } else {
                            image.setVisibility(View.INVISIBLE);
                            timer.cancel();
                        }


                    }},0, 1000 - speed
                );}

    public boolean move(){
        if(!getPosition().equals(targetPos)) {
            if (x - targetPos.getX() < 0) {
                moveTo(x - 1, y);
            } else if (x - targetPos.getX() > 0) {
                moveTo(x + 1, y);
            } else if (y - targetPos.getY() < 0) {
                moveTo(x, y - 1);
            } else {
                moveTo(x, y + 1);
            }
            return true;
        }
        return false;
    }

    public void moveTo(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public Position getPosition(){
        return new Position(x,y);
    }
}
