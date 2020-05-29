package de.dhbw.game.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.ATimerUsage;
import de.dhbw.game.Difficulty;
import de.dhbw.game.wave.AWave;
import de.dhbw.game.wave.Wave;

public abstract class AMatch extends ATimerUsage {
    private int count = 0;
    private final int waveTime;
    private boolean extendable = true;
    private List<AWave> waves = new ArrayList<>();
    private final int startMoney;
    private Difficulty difficulty;

    AMatch(int waveTime, int startMoney, Difficulty difficulty) {
        this.waveTime = waveTime;
        this.startMoney = startMoney;
        this.difficulty = difficulty;
    }

    public abstract void create(GameActivity gameActivity);

    void addWaves(Wave... waves){
        if (extendable) {
            for (Wave w : waves) {
                this.waves.add(w);
            }
        }
        extendable = false;
    }

    public Optional<AWave> next() {
        if(!waves.get(count).hasNext() && hasNext()){
            count++;
        }
        return hasNext() ? Optional.of(waves.get(count)) : Optional.empty();
    }

    public AWave getCurrent(){
        return waves.get(count);
    }

    public boolean hasNext() {
        return count<waves.size();
    }

    public int getCurrentWaveNumber() {
        return count;
    }

    public int getWaveTime() {
        return waveTime;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void calculateDelay(long time){
        setDelay(getDelay() - (time-lastTimeActionMillis));
        getCurrent().calculateDelay(time);
    }
}