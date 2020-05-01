package de.dhbw.game.wave;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.objects.enemy.Enemy;

public abstract class AWave {
    private int count = 0;
    private List<Enemy> enemies = new ArrayList<>();

    public Enemy next(){
        return enemies.get(count++);
    }

    public boolean hasNext(){
        return enemies.size()>count;
    }

    void addEnemy(Enemy e){
        this.enemies.add(e);
    }

    public abstract void generate();
}
