package de.dhbw.game.match;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Difficulty;
import de.dhbw.game.EnemyType;
import de.dhbw.game.wave.Wave;
import de.dhbw.game.wave.WaveComposition;

public class HardMatch extends AMatch {
    private static final int waveTime = 50;
    private static final int waveSpeed = 1500;
    private static final int startMoney = 100;

    private static WaveComposition w1_Plane = new WaveComposition(EnemyType.PLANE, 1, 15);
    private static WaveComposition w2_Tank = new WaveComposition(EnemyType.TANK, 1, 10);
    private static WaveComposition w3_Car = new WaveComposition(EnemyType.CAR, 1, 10);
    private static WaveComposition w4_Tank = new WaveComposition(EnemyType.TANK, 1, 9);
    private static WaveComposition w4_Car = new WaveComposition(EnemyType.CAR, 1, 9);
    private static WaveComposition w5_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 1, 1);
    private static WaveComposition w6_Tank = new WaveComposition(EnemyType.TANK, 5, 4);
    private static WaveComposition w6_Car = new WaveComposition(EnemyType.CAR, 5, 4);
    private static WaveComposition w7_Tank = new WaveComposition(EnemyType.TANK, 5, 3);
    private static WaveComposition w7_Car = new WaveComposition(EnemyType.CAR, 5, 4);
    private static WaveComposition w7_Plane = new WaveComposition(EnemyType.PLANE, 5, 5);
    private static WaveComposition w8_Tank = new WaveComposition(EnemyType.TANK, 5, 6);
    private static WaveComposition w9_Car = new WaveComposition(EnemyType.CAR, 5, 8);
    private static WaveComposition w10_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 1);
    private static WaveComposition w11_Tank = new WaveComposition(EnemyType.TANK, 3, 30);
    private static WaveComposition w12_Car = new WaveComposition(EnemyType.CAR, 3, 15);
    private static WaveComposition w13_Plane = new WaveComposition(EnemyType.PLANE, 5, 10);
    private static WaveComposition w14_Tank = new WaveComposition(EnemyType.TANK, 5, 10);
    private static WaveComposition w14_Car = new WaveComposition(EnemyType.CAR, 5, 5);
    private static WaveComposition w15_Tank = new WaveComposition(EnemyType.TANK, 5, 4);
    private static WaveComposition w15_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 1);
    private static WaveComposition w15_Car = new WaveComposition(EnemyType.CAR, 5, 3);
    private static WaveComposition w15_Plane = new WaveComposition(EnemyType.PLANE, 5, 5);
    private static WaveComposition w16_Car = new WaveComposition(EnemyType.CAR, 10, 7);
    private static WaveComposition w17_Tank = new WaveComposition(EnemyType.TANK, 10, 6);
    private static WaveComposition w17_Car = new WaveComposition(EnemyType.CAR, 10, 6);
    private static WaveComposition w18_Tank = new WaveComposition(EnemyType.TANK, 10, 10);
    private static WaveComposition w18_Plane = new WaveComposition(EnemyType.PLANE, 10, 5);
    private static WaveComposition w19_Tank = new WaveComposition(EnemyType.TANK, 15, 5);
    private static WaveComposition w20_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 5, 5);
    private static WaveComposition w21_Tank = new WaveComposition(EnemyType.TANK, 10, 10);
    private static WaveComposition w21_Car = new WaveComposition(EnemyType.CAR, 10, 5);
    private static WaveComposition w22_Plane = new WaveComposition(EnemyType.PLANE, 12, 15);
    private static WaveComposition w23_Tank = new WaveComposition(EnemyType.TANK, 12, 8);
    private static WaveComposition w23_Car = new WaveComposition(EnemyType.CAR, 12, 8);
    private static WaveComposition w23_Plane = new WaveComposition(EnemyType.PLANE, 13, 8);
    private static WaveComposition w24_Car = new WaveComposition(EnemyType.CAR, 15, 10);
    private static WaveComposition w25_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 8, 8);
    private static WaveComposition w26_Tank = new WaveComposition(EnemyType.TANK, 10, 20);
    private static WaveComposition w27_Tank = new WaveComposition(EnemyType.TANK, 14, 10);
    private static WaveComposition w27_Car = new WaveComposition(EnemyType.CAR, 13, 6);
    private static WaveComposition w28_Tank = new WaveComposition(EnemyType.TANK, 15, 10);
    private static WaveComposition w28_Car = new WaveComposition(EnemyType.CAR, 14, 6);
    private static WaveComposition w28_Plane = new WaveComposition(EnemyType.PLANE, 12, 10);
    private static WaveComposition w29_Tank = new WaveComposition(EnemyType.TANK, 15, 6);
    private static WaveComposition w29_Car = new WaveComposition(EnemyType.CAR, 13, 5);
    private static WaveComposition w30_Car = new WaveComposition(EnemyType.CAR, 10, 5);
    private static WaveComposition w30_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 10, 1);
    private static WaveComposition w30_Plane = new WaveComposition(EnemyType.PLANE, 10, 10);
    private static WaveComposition w31_Tank = new WaveComposition(EnemyType.TANK, 16, 10);
    private static WaveComposition w32_Car = new WaveComposition(EnemyType.CAR, 10, 30);
    private static WaveComposition w33_Tank = new WaveComposition(EnemyType.TANK, 10, 15);
    private static WaveComposition w34_Plane = new WaveComposition(EnemyType.PLANE, 15, 15);
    private static WaveComposition w35_Car = new WaveComposition(EnemyType.CAR, 15, 5);
    private static WaveComposition w35_Tank = new WaveComposition(EnemyType.TANK, 14, 5);
    private static WaveComposition w35_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 10, 2);
    private static WaveComposition w36_Plane = new WaveComposition(EnemyType.PLANE, 20, 10);
    private static WaveComposition w36_Car = new WaveComposition(EnemyType.CAR, 18, 10);
    private static WaveComposition w37_Car = new WaveComposition(EnemyType.CAR, 17, 8);
    private static WaveComposition w37_Tank = new WaveComposition(EnemyType.TANK, 18, 13);
    private static WaveComposition w38_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 18, 1);
    private static WaveComposition w39_Tank = new WaveComposition(EnemyType.TANK, 20, 7);
    private static WaveComposition w39_Car = new WaveComposition(EnemyType.CAR, 20, 4);
    private static WaveComposition w39_Plane = new WaveComposition(EnemyType.PLANE, 20, 6);
    private static WaveComposition w40_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 15, 10);
    private static WaveComposition w40_Plane = new WaveComposition(EnemyType.PLANE, 20, 7);
    private static WaveComposition w41_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 15, 1);
    private static WaveComposition w41_Tank = new WaveComposition(EnemyType.TANK, 20, 9);
    private static WaveComposition w42_Car = new WaveComposition(EnemyType.CAR, 22, 15);
    private static WaveComposition w43_Tank = new WaveComposition(EnemyType.TANK, 22, 15);
    private static WaveComposition w44_Plane = new WaveComposition(EnemyType.PLANE, 24, 15);
    private static WaveComposition w45_Car = new WaveComposition(EnemyType.CAR, 22, 5);
    private static WaveComposition w45_Tank = new WaveComposition(EnemyType.TANK, 22, 5);
    private static WaveComposition w45_Plane = new WaveComposition(EnemyType.PLANE, 24, 5);
    private static WaveComposition w46_Tank = new WaveComposition(EnemyType.TANK, 22, 5);
    private static WaveComposition w46_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 20, 1);
    private static WaveComposition w46_Car = new WaveComposition(EnemyType.CAR, 22, 5);
    private static WaveComposition w47_Car = new WaveComposition(EnemyType.CAR, 25, 5);
    private static WaveComposition w47_Tank = new WaveComposition(EnemyType.TANK, 25, 5);
    private static WaveComposition w48_Plane = new WaveComposition(EnemyType.PLANE, 28, 15);
    private static WaveComposition w49_Tank = new WaveComposition(EnemyType.TANK, 25, 10);
    private static WaveComposition w49_Car = new WaveComposition(EnemyType.CAR, 25, 10);
    private static WaveComposition w50_TankBoss = new WaveComposition(EnemyType.BOSS_TANK, 20, 10);

    public HardMatch() {
        super(waveTime, startMoney, Difficulty.HARD);
    }

    @Override
    public void create(GameActivity gameActivity) {
        Wave wave1 = new Wave(waveSpeed);
        wave1.addWaveCompositions(w1_Plane);
        wave1.generate(gameActivity);

        Wave wave2 = new Wave(waveSpeed);
        wave2.addWaveCompositions(w2_Tank);
        wave2.generate(gameActivity);

        Wave wave3 = new Wave(waveSpeed);
        wave3.addWaveCompositions(w3_Car);
        wave3.generate(gameActivity);

        Wave wave4 = new Wave(waveSpeed);
        wave4.addWaveCompositions(w4_Tank, w4_Car);
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
        wave14.addWaveCompositions(w14_Car, w14_Tank, w14_Car);
        wave14.generate(gameActivity);

        Wave wave15 = new Wave(waveSpeed);
        wave15.addWaveCompositions(w15_Tank, w15_TankBoss, w15_Car, w15_Plane);
        wave15.generate(gameActivity);

        Wave wave16 = new Wave(waveSpeed);
        wave16.addWaveCompositions(w16_Car);
        wave16.generate(gameActivity);

        Wave wave17 = new Wave(waveSpeed);
        wave17.addWaveCompositions(w17_Car, w17_Tank, w17_Car, w17_Tank, w17_Car);
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
        wave28.addWaveCompositions(w28_Car, w28_Tank, w28_Plane);
        wave28.generate(gameActivity);

        Wave wave29 = new Wave(waveSpeed);
        wave29.addWaveCompositions(w29_Tank, w29_Car);
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
        wave35.addWaveCompositions(w35_Tank, w35_TankBoss, w35_Car);
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
        wave39.addWaveCompositions(w39_Tank, w39_Plane, w39_Car);
        wave39.generate(gameActivity);

        Wave wave40 = new Wave(waveSpeed);
        wave40.addWaveCompositions(w40_TankBoss, w40_Plane);
        wave40.generate(gameActivity);

        Wave wave41 = new Wave(waveSpeed);
        wave41.addWaveCompositions(w41_Tank, w41_TankBoss);
        wave41.generate(gameActivity);

        Wave wave42 = new Wave(waveSpeed);
        wave42.addWaveCompositions(w42_Car);
        wave42.generate(gameActivity);

        Wave wave43 = new Wave(waveSpeed);
        wave43.addWaveCompositions(w43_Tank);
        wave43.generate(gameActivity);

        Wave wave44 = new Wave(waveSpeed);
        wave44.addWaveCompositions(w44_Plane);
        wave44.generate(gameActivity);

        Wave wave45 = new Wave(waveSpeed);
        wave45.addWaveCompositions(w45_Tank, w45_Car, w45_Plane);
        wave45.generate(gameActivity);

        Wave wave46 = new Wave(waveSpeed);
        wave46.addWaveCompositions(w46_Tank, w46_TankBoss, w46_Car);
        wave46.generate(gameActivity);

        Wave wave47 = new Wave(waveSpeed);
        wave47.addWaveCompositions(w47_Car, w47_Tank, w47_Car, w47_Tank, w47_Car);
        wave47.generate(gameActivity);

        Wave wave48 = new Wave(waveSpeed);
        wave48.addWaveCompositions(w48_Plane);
        wave48.generate(gameActivity);

        Wave wave49 = new Wave(waveSpeed);
        wave49.addWaveCompositions(w49_Tank, w49_Car);
        wave49.generate(gameActivity);

        Wave wave50 = new Wave(waveSpeed);
        wave50.addWaveCompositions(w50_TankBoss);
        wave50.generate(gameActivity);

        addWaves(wave1, wave2, wave3, wave4, wave5, wave6, wave7, wave8, wave9,
                wave10, wave11, wave12, wave13, wave14, wave15, wave16, wave17, wave18, wave19,
                wave20, wave21, wave22, wave23, wave24, wave25, wave26, wave27, wave28, wave29,
                wave30, wave31, wave32, wave33, wave34, wave35, wave36, wave37, wave38, wave39,
                wave40, wave41, wave42, wave43, wave44, wave45, wave46, wave47, wave48, wave49,
                wave50);
    }
}
