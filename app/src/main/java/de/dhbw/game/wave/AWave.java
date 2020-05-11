package de.dhbw.game.wave;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.ATimerUsage;
import de.dhbw.map.objects.enemy.Enemy;

public abstract class AWave extends ATimerUsage {
    private int count = 0;
    private final int speed;
    private List<Enemy> enemies = new ArrayList<>();

    AWave(int speed) {
        this.speed = speed;
    }

    public Enemy next() {
        return enemies.get(count++);
    }

    public boolean hasNext() {
        return count<enemies.size();
    }

    void addEnemy(Enemy e) {
        this.enemies.add(e);
    }

    public int getWaveSpeed() {
        return speed;
    }

    public abstract void generate(GameActivity gameActivity);

    @Override
    public void calculateDelay(long time){
        setDelay(speed - (time-lastTimeActionMillis));
    }
}