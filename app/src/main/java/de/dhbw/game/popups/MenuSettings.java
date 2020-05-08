package de.dhbw.game.popups;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import de.dhbw.game.Game;

public class MenuSettings extends AMenu {
    public static Game game;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader("Settings");

    }

    @Override
    public void finish(){
        super.finish();
        game.getMatchField().continueTimers();
        game.continueTimers();
    }
}
