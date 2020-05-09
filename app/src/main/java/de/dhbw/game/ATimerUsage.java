package de.dhbw.game;

public abstract class ATimerUsage {
    private long delay = 0;
    protected long lastTimeActionMillis = 0;

    public abstract void calculateDelay(long time);

    public long getDelay(){
        return delay>0? delay:0;
    }

    protected void setDelay(long delay){
        this.delay=delay;
    }

    public void setLastTimeActionMillis(long millis){
        this.lastTimeActionMillis=millis;
    }
}
