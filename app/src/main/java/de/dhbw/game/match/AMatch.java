package de.dhbw.game.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.game.wave.AWave;
import de.dhbw.game.wave.Wave;

public abstract class AMatch {
    private int count = 0;
    private boolean extendable = true;
    private List<AWave> waves = new ArrayList<>();

    public abstract void create();

    void addWaves(Wave... waves){
        if(extendable){
            for (Wave w:waves) {
                this.waves.add(w);
            }
        }
        extendable = false;
    }

    public Optional<AWave> next(){
        return hasNext()? Optional.of(waves.get(count++)) : Optional.empty();
    }

    public boolean hasNext(){
        return waves.size()>count;
    }

    public int waveNumber(){
        return count+1;
    }
}
