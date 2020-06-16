package de.dhbw.game;

import android.os.Handler;
import android.widget.TextView;

import de.dhbw.activities.GameActivity;
import de.dhbw.activities.UIActions;

public class StatusBar implements IStatusBar {
    private TextView textLifePoints;
    private TextView textMoney;
    private TextView textCurrentWave;
    private TextView textWaveRemaining;

    private Handler handler;

    public StatusBar(TextView lifePointsView, TextView moneyView, TextView waveNumberView, TextView waveTimeRemainingView, Handler handler){
        this.textLifePoints=lifePointsView;
        this.textMoney=moneyView;
        this.textCurrentWave=waveNumberView;
        this.textWaveRemaining=waveTimeRemainingView;
        this.handler=handler;
    }

    @Override
    public void setLifePoints(int points) {
        GameActivity.runActionOnUI(handler, UIActions.setText, textLifePoints, points);
    }

    @Override
    public void setMoney(int money) {
        GameActivity.runActionOnUI(handler, UIActions.setText, textMoney, money);
    }

    @Override
    public void setCurrentWaveNumber(int wave) {
        GameActivity.runActionOnUI(handler, UIActions.setText, textCurrentWave, wave);
    }

    @Override
    public void setWaveTimeRemaining(int sec) {
        GameActivity.runActionOnUI(handler, UIActions.setText, textWaveRemaining, sec);
    }
}
