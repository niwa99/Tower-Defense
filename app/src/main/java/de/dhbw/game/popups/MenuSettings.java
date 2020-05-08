package de.dhbw.game.popups;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.buttons.ToggleMusicButton;

public class MenuSettings extends AMenu {
    public static GameActivity gameActivity;
    private static LinearLayout menuSettingsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader("Settings");
        menuSettingsLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.menu_edit_settings, null);
        addViewToPopUp(menuSettingsLayout);
        initViews();
    }

    public void initViews(){
        TextView textToggleSound = menuSettingsLayout.findViewById(R.id.textToggleSound);
        TextView textToggleMusic = menuSettingsLayout.findViewById(R.id.textToggleMusic);
        TextView textToggleAnimation = menuSettingsLayout.findViewById(R.id.textToggleAnimation);
        TextView textBackToMainMenu = menuSettingsLayout.findViewById(R.id.textBackToMainMenu);

        Button buttonToggleSound = menuSettingsLayout.findViewById(R.id.buttonToggleSound);
        Button buttonToggleMusic = menuSettingsLayout.findViewById(R.id.buttonToggleMusic);
        Button buttonToggleAnimation = menuSettingsLayout.findViewById(R.id.buttonToggleAnimation);
        Button buttonBackToMainMenu = menuSettingsLayout.findViewById(R.id.buttonBackToMainMenu);

        textToggleSound.setText("Sound");
        textToggleMusic.setText("Music");
        textToggleAnimation.setText("Animation");
        textBackToMainMenu.setText("Main Menu");

        new ToggleMusicButton(gameActivity, buttonToggleMusic, gameActivity.getMediaPlayer());



        buttonBackToMainMenu.setOnClickListener( view -> {
            gameActivity.returnToMainMenu();
        });

    }

    @Override
    public void finish(){
        super.finish();
        gameActivity.getGame().getMatchField().continueTimers();
        gameActivity.getGame().continueTimers();
    }
}
