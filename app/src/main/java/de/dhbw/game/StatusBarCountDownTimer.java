package de.dhbw.game;

import android.os.CountDownTimer;

import java.util.Timer;
import java.util.TimerTask;

public class StatusBarCountDownTimer {
    private IStatusBar status;
    private Timer timer;
    private int seconds;

    public StatusBarCountDownTimer(IStatusBar status){
        this.status=status;
    }

    public void timer(int sec){
        if(timer!=null){
            timer.cancel();
        }
        seconds = sec;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(seconds>0){
                    setTime();
                }else{
                    cancel();
                }
            }
        },0,1000);
    }

    public void stopTimer(){
        timer.cancel();
    }

    private void setTime(){
        status.setWaveTimeRemaining(String.valueOf(seconds--));
    }
}
