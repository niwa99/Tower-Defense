package de.dhbw.game.wave;

import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Tank;

public class Wave extends AWave {
    private List<WaveComposition> waveCompositions = new ArrayList<>();

    public Wave(int speed) {
        super(speed);
    }

    public void addWaveCompositions(WaveComposition... compositions){
        for (WaveComposition c : compositions) {
            this.waveCompositions.add(c);
        }
    }

    @Override
    public void generate(FrameLayout mapLayout, GameActivity gameActivity) {
        waveCompositions.stream().forEach(wave -> {
            switch (wave.getEnemyType()) {
                case TANK:
                    for (int i = 0; i < wave.getAmount(); i++) {
                        addEnemy(new Tank(wave.getEnemyType().getLabel(), wave.getLevel(), mapLayout, gameActivity));
                    }
                    break;
                case FREEZER:
                    break;
            }
        });
    }
}
