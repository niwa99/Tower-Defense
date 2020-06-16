package de.dhbw.game;

public interface IStatusBar {
    void setLifePoints(int points);
    void setMoney(int money);
    void setCurrentWaveNumber(int wave);
    void setWaveTimeRemaining(int sec);
}
