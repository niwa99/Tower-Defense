package de.dhbw.game.match;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;

public class EasyMatch extends AMatch {
    private static final int waveTime = 45;
    private static final int startMoney = 100;
    private static WaveComposition firstWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 8);
    private static WaveComposition firstWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 2);
    private static WaveComposition secondWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 5);
    private static WaveComposition secondWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 5);
    private static WaveComposition thirdWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 10);
    private static WaveComposition fourthWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 5);
    private static WaveComposition fourthWaveTankL3 = new WaveComposition(EnemyType.TANK, 3, 5);
    private static WaveComposition fithhWaveTankL3 = new WaveComposition(EnemyType.TANK, 3, 10);


    public EasyMatch() {
        super(waveTime, startMoney);
    }

    @Override
    public void create(GameActivity gameActivity) {

        Wave firstWave = new Wave(1500);
        firstWave.addWaveCompositions(firstWaveTankL1, firstWaveTankL2);
        firstWave.generate(gameActivity);

        Wave secondWave = new Wave(1500);
        secondWave.addWaveCompositions(secondWaveTankL1, secondWaveTankL2);
        secondWave.generate(gameActivity);

        Wave thirdWave = new Wave(1500);
        thirdWave.addWaveCompositions(thirdWaveTankL2);
        thirdWave.generate(gameActivity);

        Wave fourthWave = new Wave(1500);
        fourthWave.addWaveCompositions(fourthWaveTankL2, fourthWaveTankL3);
        fourthWave.generate(gameActivity);

        Wave fithWave = new Wave(1500);
        fithWave.addWaveCompositions(fithhWaveTankL3);
        fithWave.generate(gameActivity);

        addWaves(firstWave, secondWave, fithWave, fourthWave, fithWave);
    }
}
