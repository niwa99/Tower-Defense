package de.dhbw.game.wave;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.objects.enemy.Enemy;

public abstract class AWave {
    private int count = 0;
    private final int speed;
    private List<Enemy> enemies = new ArrayList<>();

    AWave(int speed) {
        this.speed = speed;
    }

    public Enemy next(){
        return enemies.get(count++);
    }

    public boolean hasNext(){
        return enemies.size()>count;
    }

    void addEnemy(Enemy e){
        this.enemies.add(e);
    }

    public int getWaveSpeed(){
        return speed;
    }

    public abstract void generate();
}
