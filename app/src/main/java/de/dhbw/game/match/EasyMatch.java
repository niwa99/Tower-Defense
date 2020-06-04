package de.dhbw.game.match;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Difficulty;
import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;

public class EasyMatch extends AMatch {
    private static final int waveTime = 45;
    private static final int waveSpeed = 2000;
    private static final int startMoney = 100;
    private static WaveComposition firstWaveCarL1 = new WaveComposition(EnemyType.CAR, 1, 3);
    private static WaveComposition firstWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 6);
    private static WaveComposition firstWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 2);
    private static WaveComposition secondWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 5);
    private static WaveComposition secondWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 5);
    private static WaveComposition thirdWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 10);
    private static WaveComposition thirdWaveCarL2 = new WaveComposition(EnemyType.CAR, 2, 5);
    private static WaveComposition fourthWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 5);
    private static WaveComposition fourthWaveTankL3 = new WaveComposition(EnemyType.TANK, 3, 5);
    private static WaveComposition fifthWaveCarL3 = new WaveComposition(EnemyType.CAR, 3, 5);
    private static WaveComposition fithWaveTankL3 = new WaveComposition(EnemyType.TANK, 3, 10);


    public EasyMatch() {
        super(waveTime, startMoney, Difficulty.EASY);
    }

    @Override
    public void create(GameActivity gameActivity) {

        Wave firstWave = new Wave(waveSpeed);
        firstWave.addWaveCompositions(firstWaveCarL1, firstWaveTankL1, firstWaveTankL2);
        firstWave.generate(gameActivity);

        Wave secondWave = new Wave(waveSpeed);
        secondWave.addWaveCompositions(secondWaveTankL1, secondWaveTankL2);
        secondWave.generate(gameActivity);

        Wave thirdWave = new Wave(waveSpeed);
        thirdWave.addWaveCompositions(thirdWaveCarL2, thirdWaveTankL2);
        thirdWave.generate(gameActivity);

        Wave fourthWave = new Wave(waveSpeed);
        fourthWave.addWaveCompositions(fourthWaveTankL2, fourthWaveTankL3);
        fourthWave.generate(gameActivity);

        Wave fithWave = new Wave(waveSpeed);
        fithWave.addWaveCompositions(fifthWaveCarL3, fithWaveTankL3);
        fithWave.generate(gameActivity);

        addWaves(firstWave, secondWave, thirdWave, fourthWave, fithWave);
    }
}
