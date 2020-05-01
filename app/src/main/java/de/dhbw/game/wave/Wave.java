package de.dhbw.game.wave;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.objects.enemy.Tank;

public class Wave extends AWave {
    private List<WaveComposition> waveCompositions = new ArrayList<>();

    public void addWaveCompositions(WaveComposition... compositions){
        for (WaveComposition c:compositions) {
            this.waveCompositions.add(c);
        }
    }

    @Override
    public void generate() {
        waveCompositions.stream().forEach(w -> {
            switch (w.getEnemyType()){
                case TANK:
                    for (int i = 0; i < w.getAmount(); i++) {
                        addEnemy(new Tank(w.getEnemyType().getLabel(), w.getLevel()));
                    }
                    break;
                case FREEZER:
                    break;
            }
        });
    }
}
