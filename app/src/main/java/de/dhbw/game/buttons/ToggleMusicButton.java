package de.dhbw.game.buttons;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.dhbw.R;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

public class ToggleMusicButton {
    private String settings_music;
    private final MediaPlayer mediaPlayer;

    public ToggleMusicButton(AppCompatActivity activity, Button buttonToggleMusic, MediaPlayer mediaPlayer){
        this.mediaPlayer=mediaPlayer;
        initSoundButton(buttonToggleMusic);
        initListener(buttonToggleMusic, activity);
    }

    private void initListener(Button buttonToggleMusic, AppCompatActivity activity) {
        buttonToggleMusic.setOnClickListener(view -> {
            if (buttonToggleMusic.getBackground().getConstantState() == Objects.requireNonNull(activity.getDrawable(R.drawable.sound)).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.MUSIC).equals(STATUS_ON)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.MUSIC, STATUS_OFF);
                buttonToggleMusic.setBackgroundResource(R.drawable.no_sound);
                mediaPlayer.stop();
            } else if (buttonToggleMusic.getBackground().getConstantState() == Objects.requireNonNull(activity.getDrawable(R.drawable.no_sound)).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.MUSIC).equals(STATUS_OFF)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.MUSIC, STATUS_ON);
                buttonToggleMusic.setBackgroundResource(R.drawable.sound);
                mediaPlayer.start();
            }
        });
    }

    private void initSoundButton(Button buttonToggleMusic) {
        settings_music = PreferenceManager.getSettingsValue(PreferenceManager.Settings.MUSIC);
        if (settings_music == null) {
            PreferenceManager.setSettingsValue(PreferenceManager.Settings.MUSIC, STATUS_ON);
            buttonToggleMusic.setBackgroundResource(R.drawable.sound);
            mediaPlayer.start();
        } else if (settings_music.equals(STATUS_ON)) {
            buttonToggleMusic.setBackgroundResource(R.drawable.sound);
            mediaPlayer.start();
        } else if (settings_music.equals(STATUS_OFF)) {
            buttonToggleMusic.setBackgroundResource(R.drawable.no_sound);
        }
    }
}
