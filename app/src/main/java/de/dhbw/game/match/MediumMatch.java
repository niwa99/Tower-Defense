package de.dhbw.game.match;

import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;
import de.dhbw.util.ObjectStorage;

public class MediumMatch extends AMatch {
    private static final int waveTime = 45;
    private static WaveComposition firstWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 8);
    private static WaveComposition firstWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 2);
    private static WaveComposition secondWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 5);
    private static WaveComposition secondWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 5);
    private static WaveComposition thirdWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 10);

    public MediumMatch() {
        super(waveTime);
    }

    @Override
    public void create(){
        ObjectStorage.getGame().setLifePoints(50);
        ObjectStorage.getGame().setMoney(15);

        Wave firstWave = new Wave(1500);
        firstWave.addWaveCompositions(firstWaveTankL1, firstWaveTankL2);
        firstWave.generate();

        Wave secondWave = new Wave(1500);
        secondWave.addWaveCompositions(secondWaveTankL1, secondWaveTankL2);
        secondWave.generate();

        Wave thirdWave = new Wave(1500);
        thirdWave.addWaveCompositions(thirdWaveTankL2);
        thirdWave.generate();

        addWaves(firstWave, secondWave, thirdWave);
    }
}
