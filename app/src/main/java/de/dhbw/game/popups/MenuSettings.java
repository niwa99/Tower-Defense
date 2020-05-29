package de.dhbw.game.popups;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.settings.ISettingsManager;
import de.dhbw.game.settings.Settings;
import de.dhbw.game.settings.SettingsToggleButton;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.ICON_ANIMATION_OFF;
import static de.dhbw.util.Constants.ICON_ANIMATION_ON;
import static de.dhbw.util.Constants.ICON_MUSIC_OFF;
import static de.dhbw.util.Constants.ICON_MUSIC_ON;
import static de.dhbw.util.Constants.ICON_SOUND_OFF;
import static de.dhbw.util.Constants.ICON_SOUND_ON;
import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

public class MenuSettings extends AMenu implements ISettingsManager {
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

        new SettingsToggleButton(this, buttonToggleMusic, Settings.MUSIC, ICON_MUSIC_ON, ICON_MUSIC_OFF);
        new SettingsToggleButton(this, buttonToggleSound, Settings.INGAME_SOUND, ICON_SOUND_ON, ICON_SOUND_OFF);
        new SettingsToggleButton(this, buttonToggleAnimation, Settings.ANIMATIONS, ICON_ANIMATION_ON, ICON_ANIMATION_OFF);

        buttonBackToMainMenu.setOnClickListener( view -> {
            gameActivity.returnToMainMenu();
        });
    }

    @Override
    public void toggle(Settings setting, boolean on){
        switch (setting){
            case MUSIC:
                gameActivity.getGame().toggleMusic(on);
                break;
            case INGAME_SOUND:
                gameActivity.getGame().setIngameSound(on);
                break;
            case ANIMATIONS:
                gameActivity.getGame().setAnimationOn(on);
                break;
        }
    }

    @Override
    public void finish(){
        super.finish();
        gameActivity.getGame().closeMenu();
        gameActivity.getGame().getMatchField().continueTimers();
        gameActivity.getGame().continueTimers();
    }
}
