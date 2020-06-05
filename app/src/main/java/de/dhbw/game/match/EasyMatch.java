package de.dhbw.game.match;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Difficulty;
import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;

public class EasyMatch extends AMatch {
    private static final int waveTime = 50;
    private static final int waveSpeed = 1500;
    private static final int startMoney = 35;
    private static WaveComposition firstWaveCarL1 = new WaveComposition(EnemyType.CAR, 1, 5);
    private static WaveComposition secondWaveTankL1 = new WaveComposition(EnemyType.TANK, 1, 8);
    private static WaveComposition thirdWaveCarL2 = new WaveComposition(EnemyType.CAR, 2, 8);
    private static WaveComposition fourthWaveTankL2 = new WaveComposition(EnemyType.TANK, 2, 10);
    private static WaveComposition fifthWavePlaneL1 = new WaveComposition(EnemyType.PLANE, 1, 3);
    private static WaveComposition w6_TankL3 = new WaveComposition(EnemyType.TANK, 2, 15);
    private static WaveComposition w7_CarL3 = new WaveComposition(EnemyType.CAR, 3, 5);
    private static WaveComposition w7_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w8_TankL3 = new WaveComposition(EnemyType.TANK, 3, 20);
    private static WaveComposition w9_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w9_CarL3 = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w10_TankL3 = new WaveComposition(EnemyType.TANK, 3, 5);
    private static WaveComposition w10_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 1, 1);
    private static WaveComposition w10_CarL3 = new WaveComposition(EnemyType.CAR, 3, 5);
    private static WaveComposition w11_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w11_CarL3 = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w11_PlaneL3 = new WaveComposition(EnemyType.PLANE, 3, 10);
    private static WaveComposition w12_TankL3 = new WaveComposition(EnemyType.TANK, 3, 20);
    private static WaveComposition w13_CarL3 = new WaveComposition(EnemyType.CAR, 3, 20);
    private static WaveComposition w14_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w14_CarL3 = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w15_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w15_CarL3 = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w16_PlaneL3 = new WaveComposition(EnemyType.PLANE, 3, 20);
    private static WaveComposition w17_TankL3 = new WaveComposition(EnemyType.TANK, 3, 10);
    private static WaveComposition w17_CarL3 = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w30_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 3, 10);
    // MAX 20 Gegner pro waves, Enemy level generieren, Boss ändern evtl

    public EasyMatch() {
        super(waveTime, startMoney, Difficulty.EASY);
    }

    @Override
    public void create(GameActivity gameActivity) {

        Wave firstWave = new Wave(waveSpeed);
        firstWave.addWaveCompositions(firstWaveCarL1);
        firstWave.generate(gameActivity);

        Wave secondWave = new Wave(waveSpeed);
        secondWave.addWaveCompositions(secondWaveTankL1);
        secondWave.generate(gameActivity);

        Wave thirdWave = new Wave(waveSpeed);
        thirdWave.addWaveCompositions(thirdWaveCarL2);
        thirdWave.generate(gameActivity);

        Wave fourthWave = new Wave(waveSpeed);
        fourthWave.addWaveCompositions(fourthWaveTankL2);
        fourthWave.generate(gameActivity);

        Wave fithWave = new Wave(waveSpeed);
        fithWave.addWaveCompositions(fifthWavePlaneL1);
        fithWave.generate(gameActivity);

        Wave wave6 = new Wave(waveSpeed);
        wave6.addWaveCompositions(w6_TankL3);
        wave6.generate(gameActivity);

        Wave wave7 = new Wave(waveSpeed);
        wave7.addWaveCompositions(w7_CarL3, w7_TankL3, w7_CarL3);
        wave7.generate(gameActivity);

        Wave wave8 = new Wave(waveSpeed);
        wave8.addWaveCompositions(w8_TankL3);
        wave8.generate(gameActivity);

        Wave wave9 = new Wave(waveSpeed);
        wave9.addWaveCompositions(w9_TankL3, w9_CarL3);
        wave9.generate(gameActivity);

        Wave wave10 = new Wave(waveSpeed);
        wave10.addWaveCompositions(w10_TankL3, w10_TankBoss, w10_CarL3);
        wave10.generate(gameActivity);

        Wave wave11 = new Wave(waveSpeed);
        wave11.addWaveCompositions(w11_TankL3, w11_CarL3, w11_PlaneL3);
        wave11.generate(gameActivity);

        Wave wave12 = new Wave(waveSpeed);
        wave12.addWaveCompositions(w12_TankL3);
        wave12.generate(gameActivity);

        Wave wave13 = new Wave(waveSpeed);
        wave13.addWaveCompositions(w13_CarL3);
        wave13.generate(gameActivity);

        Wave wave14 = new Wave(waveSpeed);
        wave14.addWaveCompositions(w14_TankL3, w14_CarL3);
        wave14.generate(gameActivity);

        Wave wave15 = new Wave(waveSpeed);
        wave15.addWaveCompositions(w15_CarL3, w15_TankL3, w15_CarL3, w15_TankL3, w15_CarL3);
        wave15.generate(gameActivity);

        Wave wave16 = new Wave(waveSpeed);
        wave16.addWaveCompositions(w16_PlaneL3);
        wave16.generate(gameActivity);

        Wave wave17 = new Wave(waveSpeed);
        wave17.addWaveCompositions(w17_TankL3, w17_CarL3);
        wave17.generate(gameActivity);

        Wave wave18 = new Wave(waveSpeed);
        wave18.addWaveCompositions();
        wave18.generate(gameActivity);

        Wave wave19 = new Wave(waveSpeed);
        wave19.addWaveCompositions();
        wave19.generate(gameActivity);

        Wave wave20 = new Wave(waveSpeed);
        wave20.addWaveCompositions();
        wave20.generate(gameActivity);

        Wave wave21 = new Wave(waveSpeed);
        wave21.addWaveCompositions();
        wave21.generate(gameActivity);

        Wave wave22 = new Wave(waveSpeed);
        wave22.addWaveCompositions();
        wave22.generate(gameActivity);

        Wave wave23 = new Wave(waveSpeed);
        wave23.addWaveCompositions();
        wave23.generate(gameActivity);

        Wave wave24 = new Wave(waveSpeed);
        wave24.addWaveCompositions();
        wave24.generate(gameActivity);

        Wave wave25 = new Wave(waveSpeed);
        wave25.addWaveCompositions();
        wave25.generate(gameActivity);

        Wave wave26 = new Wave(waveSpeed);
        wave26.addWaveCompositions();
        wave26.generate(gameActivity);

        Wave wave27 = new Wave(waveSpeed);
        wave27.addWaveCompositions();
        wave27.generate(gameActivity);

        Wave wave28 = new Wave(waveSpeed);
        wave28.addWaveCompositions();
        wave28.generate(gameActivity);

        Wave wave29 = new Wave(waveSpeed);
        wave29.addWaveCompositions();
        wave29.generate(gameActivity);

        Wave wave30 = new Wave(waveSpeed);
        wave30.addWaveCompositions(w30_TankBoss);
        wave30.generate(gameActivity);

        addWaves(firstWave, secondWave, thirdWave, fourthWave, fithWave, wave6, wave7, wave8, wave9,
                wave10, wave11, wave12, wave13, wave14, wave15, wave16, wave17, wave18, wave19,
                wave20, wave21, wave22, wave23, wave24, wave25, wave26, wave27, wave28, wave29,
                wave30);
    }
}
