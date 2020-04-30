package de.dhbw.game;

public interface IStatusBar {
    void setLifePoints(String points);
    void setMoney(String money);
    void setCurrentWave(String wave);
    void setWaveTimeRemaining(String sec);
}
