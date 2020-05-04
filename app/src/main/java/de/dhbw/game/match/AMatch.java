package de.dhbw.game.match;

import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.wave.AWave;
import de.dhbw.game.wave.Wave;

public abstract class AMatch {
    private int count = 0;
    private final int waveTime;
    private boolean extendable = true;
    private List<AWave> waves = new ArrayList<>();
    private final int startMoney;

    AMatch(int waveTime, int startMoney) {
        this.waveTime = waveTime;
        this.startMoney = startMoney;
    }

    public abstract void create(FrameLayout mapLayout, GameActivity gameActivity);

    void addWaves(Wave... waves){
        if(extendable){
            for (Wave w : waves) {
                this.waves.add(w);
            }
        }
        extendable = false;
    }

    public Optional<AWave> next(){
        return hasNext() ? Optional.of(waves.get(count++)) : Optional.empty();
    }

    public boolean hasNext(){
        return waves.size() > count;
    }

    public int waveNumber(){
        return count+1;
    }

    public int getWaveTime(){
        return waveTime;
    }

    public int getStartMoney() {
        return startMoney;
    }
}
