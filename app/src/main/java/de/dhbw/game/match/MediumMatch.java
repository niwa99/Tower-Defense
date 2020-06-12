package de.dhbw.game.match;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Difficulty;
import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;

public class MediumMatch extends AMatch {
    private static final int waveTime = 45;
    private static final int waveSpeed = 1500;
    private static final int startMoney = 20;

    private static WaveComposition w1_Tank = new WaveComposition(EnemyType.TANK, 1, 3);
    private static WaveComposition w2_Tank = new WaveComposition(EnemyType.TANK, 2, 3);
    private static WaveComposition w3_Car = new WaveComposition(EnemyType.CAR, 2, 6);
    private static WaveComposition w4_Plane = new WaveComposition(EnemyType.PLANE, 1, 5);
    private static WaveComposition w5_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 1, 1);
    private static WaveComposition w6_Tank = new WaveComposition(EnemyType.TANK, 2, 4);
    private static WaveComposition w6_Car = new WaveComposition(EnemyType.CAR, 3, 4);
    private static WaveComposition w7_Tank = new WaveComposition(EnemyType.TANK, 3, 3);
    private static WaveComposition w7_Car = new WaveComposition(EnemyType.CAR, 3, 4);
    private static WaveComposition w7_Plane = new WaveComposition(EnemyType.PLANE, 3, 5);
    private static WaveComposition w8_Tank = new WaveComposition(EnemyType.TANK, 4, 9);
    private static WaveComposition w9_Car = new WaveComposition(EnemyType.CAR, 5, 10);
    private static WaveComposition w10_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 3, 1);
    private static WaveComposition w11_Tank = new WaveComposition(EnemyType.TANK, 4, 29);
    private static WaveComposition w12_Car = new WaveComposition(EnemyType.CAR, 5, 15);
    private static WaveComposition w13_Plane = new WaveComposition(EnemyType.PLANE, 6, 15);
    private static WaveComposition w14_Tank = new WaveComposition(EnemyType.TANK, 6, 7);
    private static WaveComposition w14_Car = new WaveComposition(EnemyType.CAR, 6, 8);
    private static WaveComposition w15_Tank = new WaveComposition(EnemyType.TANK, 10, 2);
    private static WaveComposition w15_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 1);
    private static WaveComposition w15_Car = new WaveComposition(EnemyType.CAR, 4, 3);
    private static WaveComposition w15_Plane = new WaveComposition(EnemyType.PLANE, 6, 5);
    private static WaveComposition w16_Car = new WaveComposition(EnemyType.CAR, 15, 5);
    private static WaveComposition w17_Tank = new WaveComposition(EnemyType.TANK, 8, 6);
    private static WaveComposition w17_Car = new WaveComposition(EnemyType.CAR, 8, 6);
    private static WaveComposition w18_Tank = new WaveComposition(EnemyType.TANK, 10, 10);
    private static WaveComposition w18_Plane = new WaveComposition(EnemyType.PLANE, 9, 5);
    private static WaveComposition w19_Tank = new WaveComposition(EnemyType.TANK, 15, 8);
    private static WaveComposition w20_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 8, 5);
    private static WaveComposition w21_Tank = new WaveComposition(EnemyType.TANK, 10, 6);
    private static WaveComposition w21_Car = new WaveComposition(EnemyType.CAR, 10, 6);
    private static WaveComposition w22_Plane = new WaveComposition(EnemyType.PLANE, 11, 15);
    private static WaveComposition w23_Tank = new WaveComposition(EnemyType.TANK, 10, 8);
    private static WaveComposition w23_Car = new WaveComposition(EnemyType.CAR, 10, 8);
    private static WaveComposition w23_Plane = new WaveComposition(EnemyType.PLANE, 10, 8);
    private static WaveComposition w24_Car = new WaveComposition(EnemyType.CAR, 15, 10);
    private static WaveComposition w25_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 10, 8);
    private static WaveComposition w26_Tank = new WaveComposition(EnemyType.TANK, 12, 20);
    private static WaveComposition w27_Tank = new WaveComposition(EnemyType.TANK, 12, 10);
    private static WaveComposition w27_Car = new WaveComposition(EnemyType.CAR, 12, 6);
    private static WaveComposition w28_Tank = new WaveComposition(EnemyType.TANK, 12, 10);
    private static WaveComposition w28_Car = new WaveComposition(EnemyType.CAR, 12, 6);
    private static WaveComposition w28_Plane = new WaveComposition(EnemyType.PLANE, 12, 10);
    private static WaveComposition w29_Tank = new WaveComposition(EnemyType.TANK, 12, 6);
    private static WaveComposition w29_Car = new WaveComposition(EnemyType.CAR, 12, 5);
    private static WaveComposition w30_Car = new WaveComposition(EnemyType.CAR, 17, 5);
    private static WaveComposition w30_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 15, 3);
    private static WaveComposition w30_Plane = new WaveComposition(EnemyType.PLANE, 15, 10);
    private static WaveComposition w31_Tank = new WaveComposition(EnemyType.TANK, 17, 10);
    private static WaveComposition w32_Car = new WaveComposition(EnemyType.CAR, 17, 29);
    private static WaveComposition w33_Tank = new WaveComposition(EnemyType.TANK, 16, 15);
    private static WaveComposition w34_Plane = new WaveComposition(EnemyType.PLANE, 20, 15);
    private static WaveComposition w35_Car = new WaveComposition(EnemyType.CAR, 20, 5);
    private static WaveComposition w35_Tank = new WaveComposition(EnemyType.TANK, 20, 5);
    private static WaveComposition w35_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 20, 1);
    private static WaveComposition w36_Plane = new WaveComposition(EnemyType.PLANE, 20, 10);
    private static WaveComposition w36_Car = new WaveComposition(EnemyType.CAR, 20, 10);
    private static WaveComposition w37_Car = new WaveComposition(EnemyType.CAR, 22, 8);
    private static WaveComposition w37_Tank = new WaveComposition(EnemyType.TANK, 22, 13);
    private static WaveComposition w38_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 30, 1);
    private static WaveComposition w39_Tank = new WaveComposition(EnemyType.TANK, 25, 8);
    private static WaveComposition w39_Car = new WaveComposition(EnemyType.CAR, 25, 5);
    private static WaveComposition w39_Plane = new WaveComposition(EnemyType.PLANE, 25, 7);
    private static WaveComposition w40_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 25, 10);
    private static WaveComposition w40_Plane = new WaveComposition(EnemyType.PLANE, 25, 7);


    public MediumMatch() {
        super(waveTime, startMoney, Difficulty.MEDIUM);
    }

    @Override
    public void create(GameActivity gameActivity) {

        Wave wave1 = new Wave(waveSpeed);
        wave1.addWaveCompositions(w1_Tank);
        wave1.generate(gameActivity);

        Wave wave2 = new Wave(waveSpeed);
        wave2.addWaveCompositions(w2_Tank);
        wave2.generate(gameActivity);

        Wave wave3 = new Wave(waveSpeed);
        wave3.addWaveCompositions(w3_Car);
        wave3.generate(gameActivity);

        Wave wave4 = new Wave(waveSpeed);
        wave4.addWaveCompositions(w4_Plane);
        wave4.generate(gameActivity);

        Wave wave5 = new Wave(waveSpeed);
        wave5.addWaveCompositions(w5_TankBoss);
        wave5.generate(gameActivity);

        Wave wave6 = new Wave(waveSpeed);
        wave6.addWaveCompositions(w6_Tank, w6_Car);
        wave6.generate(gameActivity);

        Wave wave7 = new Wave(waveSpeed);
        wave7.addWaveCompositions(w7_Tank, w7_Car, w7_Plane, w7_Car);
        wave7.generate(gameActivity);

        Wave wave8 = new Wave(waveSpeed);
        wave8.addWaveCompositions(w8_Tank);
        wave8.generate(gameActivity);

        Wave wave9 = new Wave(waveSpeed);
        wave9.addWaveCompositions(w9_Car);
        wave9.generate(gameActivity);

        Wave wave10 = new Wave(waveSpeed);
        wave10.addWaveCompositions(w10_TankBoss);
        wave10.generate(gameActivity);

        Wave wave11 = new Wave(waveSpeed);
        wave11.addWaveCompositions(w11_Tank);
        wave11.generate(gameActivity);

        Wave wave12 = new Wave(waveSpeed);
        wave12.addWaveCompositions(w12_Car);
        wave12.generate(gameActivity);

        Wave wave13 = new Wave(waveSpeed);
        wave13.addWaveCompositions(w13_Plane);
        wave13.generate(gameActivity);

        Wave wave14 = new Wave(waveSpeed);
        wave14.addWaveCompositions(w14_Tank, w14_Car);
        wave14.generate(gameActivity);

        Wave wave15 = new Wave(waveSpeed);
        wave15.addWaveCompositions(w15_Tank, w15_TankBoss, w15_Car, w15_Plane);
        wave15.generate(gameActivity);

        Wave wave16 = new Wave(waveSpeed);
        wave16.addWaveCompositions(w16_Car);
        wave16.generate(gameActivity);

        Wave wave17 = new Wave(waveSpeed);
        wave17.addWaveCompositions(w17_Car, w17_Tank, w17_Car);
        wave17.generate(gameActivity);

        Wave wave18 = new Wave(waveSpeed);
        wave18.addWaveCompositions(w18_Tank, w18_Plane);
        wave18.generate(gameActivity);

        Wave wave19 = new Wave(waveSpeed);
        wave19.addWaveCompositions(w19_Tank);
        wave19.generate(gameActivity);

        Wave wave20 = new Wave(waveSpeed);
        wave20.addWaveCompositions(w20_TankBoss);
        wave20.generate(gameActivity);

        Wave wave21 = new Wave(waveSpeed);
        wave21.addWaveCompositions(w21_Tank, w21_Car);
        wave21.generate(gameActivity);

        Wave wave22 = new Wave(waveSpeed);
        wave22.addWaveCompositions(w22_Plane);
        wave22.generate(gameActivity);

        Wave wave23 = new Wave(waveSpeed);
        wave23.addWaveCompositions(w23_Tank, w23_Car, w23_Plane);
        wave23.generate(gameActivity);

        Wave wave24 = new Wave(waveSpeed);
        wave24.addWaveCompositions(w24_Car);
        wave24.generate(gameActivity);

        Wave wave25 = new Wave(waveSpeed);
        wave25.addWaveCompositions(w25_TankBoss);
        wave25.generate(gameActivity);

        Wave wave26 = new Wave(waveSpeed);
        wave26.addWaveCompositions(w26_Tank);
        wave26.generate(gameActivity);

        Wave wave27 = new Wave(waveSpeed);
        wave27.addWaveCompositions(w27_Tank, w27_Car);
        wave27.generate(gameActivity);

        Wave wave28 = new Wave(waveSpeed);
        wave28.addWaveCompositions(w28_Tank, w28_Car, w28_Plane);
        wave28.generate(gameActivity);

        Wave wave29 = new Wave(waveSpeed);
        wave29.addWaveCompositions(w29_Car, w29_Tank, w29_Car, w29_Tank, w29_Car);
        wave29.generate(gameActivity);

        Wave wave30 = new Wave(waveSpeed);
        wave30.addWaveCompositions(w30_Car, w30_TankBoss, w30_Plane);
        wave30.generate(gameActivity);

        Wave wave31 = new Wave(waveSpeed);
        wave31.addWaveCompositions(w31_Tank);
        wave31.generate(gameActivity);

        Wave wave32 = new Wave(waveSpeed);
        wave32.addWaveCompositions(w32_Car);
        wave32.generate(gameActivity);

        Wave wave33 = new Wave(waveSpeed);
        wave33.addWaveCompositions(w33_Tank);
        wave33.generate(gameActivity);

        Wave wave34 = new Wave(waveSpeed);
        wave34.addWaveCompositions(w34_Plane);
        wave34.generate(gameActivity);

        Wave wave35 = new Wave(waveSpeed);
        wave35.addWaveCompositions(w35_Car, w35_Tank, w35_TankBoss);
        wave35.generate(gameActivity);

        Wave wave36 = new Wave(waveSpeed);
        wave36.addWaveCompositions(w36_Plane, w36_Car);
        wave36.generate(gameActivity);

        Wave wave37 = new Wave(waveSpeed);
        wave37.addWaveCompositions(w37_Car, w37_Tank);
        wave37.generate(gameActivity);

        Wave wave38 = new Wave(waveSpeed);
        wave38.addWaveCompositions(w38_TankBoss);
        wave38.generate(gameActivity);

        Wave wave39 = new Wave(waveSpeed);
        wave39.addWaveCompositions(w39_Tank, w39_Car, w39_Plane, w39_Car);
        wave39.generate(gameActivity);

        Wave wave40 = new Wave(waveSpeed);
        wave40.addWaveCompositions(w40_TankBoss, w40_Plane);
        wave40.generate(gameActivity);

        addWaves(wave1, wave2, wave3, wave4, wave5, wave6, wave7, wave8, wave9,
                wave10, wave11, wave12, wave13, wave14, wave15, wave16, wave17, wave18, wave19,
                wave20, wave21, wave22, wave23, wave24, wave25, wave26, wave27, wave28, wave29,
                wave30, wave31, wave32, wave33, wave34, wave35, wave36, wave37, wave38, wave39,
                wave40);
    }
}
