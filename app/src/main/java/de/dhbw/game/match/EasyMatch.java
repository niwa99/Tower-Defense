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
    private static WaveComposition w1_Car = new WaveComposition(EnemyType.CAR, 1, 3);
    private static WaveComposition w2_Tank = new WaveComposition(EnemyType.TANK, 1, 3);
    private static WaveComposition w3_Car = new WaveComposition(EnemyType.CAR, 2, 5);
    private static WaveComposition w4_Tank = new WaveComposition(EnemyType.TANK, 2, 4);
    private static WaveComposition w5_Plane = new WaveComposition(EnemyType.PLANE, 1, 6);
    private static WaveComposition w6_Tank = new WaveComposition(EnemyType.TANK, 1, 8);
    private static WaveComposition w7_Car = new WaveComposition(EnemyType.CAR, 2, 3);
    private static WaveComposition w7_Tank = new WaveComposition(EnemyType.TANK, 2, 3);
    private static WaveComposition w8_Tank = new WaveComposition(EnemyType.TANK, 3, 6);
    private static WaveComposition w9_Tank = new WaveComposition(EnemyType.TANK, 3, 6);
    private static WaveComposition w9_Car = new WaveComposition(EnemyType.CAR, 2, 5);
    private static WaveComposition w10_Tank = new WaveComposition(EnemyType.TANK, 2, 3);
    private static WaveComposition w10_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 2, 1);
    private static WaveComposition w10_Car = new WaveComposition(EnemyType.CAR, 2, 3);
    private static WaveComposition w11_Tank = new WaveComposition(EnemyType.TANK, 3, 5);
    private static WaveComposition w11_Car = new WaveComposition(EnemyType.CAR, 1, 5);
    private static WaveComposition w11_Plane = new WaveComposition(EnemyType.PLANE, 2, 5);
    private static WaveComposition w12_Tank = new WaveComposition(EnemyType.TANK, 3, 9);
    private static WaveComposition w13_Car = new WaveComposition(EnemyType.CAR, 3, 10);
    private static WaveComposition w14_Tank = new WaveComposition(EnemyType.TANK, 3, 7);
    private static WaveComposition w14_Car = new WaveComposition(EnemyType.CAR, 3, 6);
    private static WaveComposition w15_Tank = new WaveComposition(EnemyType.TANK, 2, 4);
    private static WaveComposition w15_Car = new WaveComposition(EnemyType.CAR, 3, 4);
    private static WaveComposition w16_Plane = new WaveComposition(EnemyType.PLANE, 3, 13);
    private static WaveComposition w17_Tank = new WaveComposition(EnemyType.TANK, 4, 6);
    private static WaveComposition w17_Car = new WaveComposition(EnemyType.CAR, 4, 7);
    private static WaveComposition w18_Tank = new WaveComposition(EnemyType.TANK, 4, 4);
    private static WaveComposition w18_Car = new WaveComposition(EnemyType.CAR, 4, 4);
    private static WaveComposition w18_Plane = new WaveComposition(EnemyType.PLANE, 3, 5);
    private static WaveComposition w19_Tank = new WaveComposition(EnemyType.TANK, 4, 12);
    private static WaveComposition w20_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 3);
    private static WaveComposition w21_Car = new WaveComposition(EnemyType.CAR, 3, 30);
    private static WaveComposition w22_Tank = new WaveComposition(EnemyType.TANK, 5, 10);
    private static WaveComposition w23_Plane = new WaveComposition(EnemyType.PLANE, 5, 10);
    private static WaveComposition w24_Tank = new WaveComposition(EnemyType.TANK, 6, 3);
    private static WaveComposition w24_Car = new WaveComposition(EnemyType.CAR, 5, 3);
    private static WaveComposition w24_Plane = new WaveComposition(EnemyType.PLANE, 7, 3);
    private static WaveComposition w25_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 1);
    private static WaveComposition w26_Car = new WaveComposition(EnemyType.CAR, 10, 5);
    private static WaveComposition w27_Tank = new WaveComposition(EnemyType.TANK, 10, 4);
    private static WaveComposition w28_Tank = new WaveComposition(EnemyType.TANK, 7, 9);
    private static WaveComposition w28_Car = new WaveComposition(EnemyType.CAR, 10, 6);
    private static WaveComposition w29_Plane = new WaveComposition(EnemyType.PLANE, 10, 10);
    private static WaveComposition w30_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 5);

    public EasyMatch() {
        super(waveTime, startMoney, Difficulty.EASY);
    }

    @Override
    public void create(GameActivity gameActivity) {

        Wave wave1 = new Wave(waveSpeed);
        wave1.addWaveCompositions(w1_Car);
        wave1.generate(gameActivity);

        Wave wave2 = new Wave(waveSpeed);
        wave2.addWaveCompositions(w2_Tank);
        wave2.generate(gameActivity);

        Wave wave3 = new Wave(waveSpeed);
        wave3.addWaveCompositions(w3_Car);
        wave3.generate(gameActivity);

        Wave wave4 = new Wave(waveSpeed);
        wave4.addWaveCompositions(w4_Tank);
        wave4.generate(gameActivity);

        Wave wave5 = new Wave(waveSpeed);
        wave5.addWaveCompositions(w5_Plane);
        wave5.generate(gameActivity);

        Wave wave6 = new Wave(waveSpeed);
        wave6.addWaveCompositions(w6_Tank);
        wave6.generate(gameActivity);

        Wave wave7 = new Wave(waveSpeed);
        wave7.addWaveCompositions(w7_Car, w7_Tank, w7_Car);
        wave7.generate(gameActivity);

        Wave wave8 = new Wave(waveSpeed);
        wave8.addWaveCompositions(w8_Tank);
        wave8.generate(gameActivity);

        Wave wave9 = new Wave(waveSpeed);
        wave9.addWaveCompositions(w9_Tank, w9_Car);
        wave9.generate(gameActivity);

        Wave wave10 = new Wave(waveSpeed);
        wave10.addWaveCompositions(w10_Tank, w10_TankBoss, w10_Car);
        wave10.generate(gameActivity);

        Wave wave11 = new Wave(waveSpeed);
        wave11.addWaveCompositions(w11_Tank, w11_Car, w11_Plane);
        wave11.generate(gameActivity);

        Wave wave12 = new Wave(waveSpeed);
        wave12.addWaveCompositions(w12_Tank);
        wave12.generate(gameActivity);

        Wave wave13 = new Wave(waveSpeed);
        wave13.addWaveCompositions(w13_Car);
        wave13.generate(gameActivity);

        Wave wave14 = new Wave(waveSpeed);
        wave14.addWaveCompositions(w14_Tank, w14_Car);
        wave14.generate(gameActivity);

        Wave wave15 = new Wave(waveSpeed);
        wave15.addWaveCompositions(w15_Tank, w15_Car, w15_Tank, w15_Car);
        wave15.generate(gameActivity);

        Wave wave16 = new Wave(waveSpeed);
        wave16.addWaveCompositions(w16_Plane);
        wave16.generate(gameActivity);

        Wave wave17 = new Wave(waveSpeed);
        wave17.addWaveCompositions(w17_Tank, w17_Car);
        wave17.generate(gameActivity);

        Wave wave18 = new Wave(waveSpeed);
        wave18.addWaveCompositions(w18_Tank, w18_Car, w18_Plane);
        wave18.generate(gameActivity);

        Wave wave19 = new Wave(waveSpeed);
        wave19.addWaveCompositions(w19_Tank);
        wave19.generate(gameActivity);

        Wave wave20 = new Wave(waveSpeed);
        wave20.addWaveCompositions(w20_TankBoss);
        wave20.generate(gameActivity);

        Wave wave21 = new Wave(waveSpeed);
        wave21.addWaveCompositions(w21_Car);
        wave21.generate(gameActivity);

        Wave wave22 = new Wave(waveSpeed);
        wave22.addWaveCompositions(w22_Tank);
        wave22.generate(gameActivity);

        Wave wave23 = new Wave(waveSpeed);
        wave23.addWaveCompositions(w23_Plane);
        wave23.generate(gameActivity);

        Wave wave24 = new Wave(waveSpeed);
        wave24.addWaveCompositions(w24_Tank, w24_Car, w24_Plane, w24_Tank, w24_Car);
        wave24.generate(gameActivity);

        Wave wave25 = new Wave(waveSpeed);
        wave25.addWaveCompositions(w25_TankBoss);
        wave25.generate(gameActivity);

        Wave wave26 = new Wave(waveSpeed);
        wave26.addWaveCompositions(w26_Car);
        wave26.generate(gameActivity);

        Wave wave27 = new Wave(waveSpeed);
        wave27.addWaveCompositions(w27_Tank);
        wave27.generate(gameActivity);

        Wave wave28 = new Wave(waveSpeed);
        wave28.addWaveCompositions(w28_Tank, w28_Car);
        wave28.generate(gameActivity);

        Wave wave29 = new Wave(waveSpeed);
        wave29.addWaveCompositions(w29_Plane);
        wave29.generate(gameActivity);

        Wave wave30 = new Wave(waveSpeed);
        wave30.addWaveCompositions(w30_TankBoss);
        wave30.generate(gameActivity);

        addWaves(wave1, wave2, wave3, wave4, wave5, wave6, wave7, wave8, wave9,
                wave10, wave11, wave12, wave13, wave14, wave15, wave16, wave17, wave18, wave19,
                wave20, wave21, wave22, wave23, wave24, wave25, wave26, wave27, wave28, wave29,
                wave30);
    }
}
