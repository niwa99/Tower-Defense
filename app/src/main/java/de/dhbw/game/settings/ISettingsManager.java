package de.dhbw.game.settings;

import de.dhbw.util.PreferenceManager;

public interface ISettingsManager {
    void toggle(PreferenceManager.Settings setting, boolean on);
}
