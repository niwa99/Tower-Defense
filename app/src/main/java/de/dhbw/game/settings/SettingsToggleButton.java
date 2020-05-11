package de.dhbw.game.settings;

import android.widget.Button;

import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

public class SettingsToggleButton {

    public SettingsToggleButton(ISettingsManager settingsManager, Button button, Settings prefSettings, int idIconOn, int idIconOff){
        String settings = PreferenceManager.getSettingsValue(prefSettings);
        if (settings == null) {
            PreferenceManager.setSettingsValue(prefSettings, STATUS_ON);
            button.setBackgroundResource(idIconOn);
            settingsManager.toggle(prefSettings, true);
        } else if (settings.equals(STATUS_ON)) {
            button.setBackgroundResource(idIconOn);
            settingsManager.toggle(prefSettings, true);
        } else if (settings.equals(STATUS_OFF)) {
            button.setBackgroundResource(idIconOff);
            settingsManager.toggle(prefSettings, false);
        }

        button.setOnClickListener( view -> {
            if (PreferenceManager.getSettingsValue(prefSettings).equals(STATUS_ON)) {
                PreferenceManager.setSettingsValue(prefSettings, STATUS_OFF);
                button.setBackgroundResource(idIconOff);
                settingsManager.toggle(prefSettings, false);
            } else if (PreferenceManager.getSettingsValue(prefSettings).equals(STATUS_OFF)) {
                PreferenceManager.setSettingsValue(prefSettings, STATUS_ON);
                button.setBackgroundResource(idIconOn);
                settingsManager.toggle(prefSettings, true);
            }
        });
    }
}
