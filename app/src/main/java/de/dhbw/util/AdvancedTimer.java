package de.dhbw.util;

import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.game.ATimerUsage;

public class AdvancedTimer {
    Timer timer = new Timer();

    boolean isCanceled = false;

    public void schedule(TimerTask task, long delay){
        timer.schedule(task, delay);
    }

    public void scheduleAtFixedRate(TimerTask task, long delay, long interval){
        timer.scheduleAtFixedRate(task, delay, interval);
    }

    public void cancel(){
        this.isCanceled=true;
        timer.cancel();
    }

    public boolean isCanceled(){
        return isCanceled;
    }
}
