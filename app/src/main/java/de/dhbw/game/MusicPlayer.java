package de.dhbw.game;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.R;

public class MusicPlayer extends ATimerUsage{
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private TimerTask timerTask;
    private boolean on;
    private boolean isStarted=false;
    private final int loopTime = 180000;

    public MusicPlayer(boolean on, Activity activity){
        this.on=on;
        startMusicPlayer(activity);
    }

    public void startMusicPlayer(Activity activity){
        this.isStarted=true;
        if(timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                }
                switch(rand.nextInt(4)){
                    case 0:
                        mediaPlayer = MediaPlayer.create(activity, R.raw.tower_defense_soundtrack_1);
                        break;
                    case 1:
                        mediaPlayer = MediaPlayer.create(activity, R.raw.tower_defense_soundtrack_3);
                        break;
                    case 2:
                        mediaPlayer = MediaPlayer.create(activity, R.raw.tower_defense_soundtrack_4);
                        break;
                    case 3:
                        mediaPlayer = MediaPlayer.create(activity, R.raw.tower_defense_soundtrack_5);
                        mediaPlayer.setVolume(80,80);
                        break;
                }
                if(on){
                    mediaPlayer.start();
                }
                lastTimeActionMillis = System.currentTimeMillis();
                setDelay(0);
            }
        };
        timer.scheduleAtFixedRate(timerTask, getDelay(), loopTime);
    }

    public void toggle(boolean on, Activity activity) {
        this.on=on;
        if(on && !mediaPlayer.isPlaying()){
            startMusicPlayer(activity);
        }
        if(on){
            mediaPlayer.start();
        }else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            timer.cancel();
            timerTask.cancel();
            calculateDelay(System.currentTimeMillis());
        }
    }

    public boolean isStarted(){
        return isStarted;
    }

    public void stop(){
        mediaPlayer.stop();
        timer.cancel();
        timerTask.cancel();
    }

    @Override
    public void calculateDelay(long time) {
        setDelay(loopTime - (time-lastTimeActionMillis));
    }
}
