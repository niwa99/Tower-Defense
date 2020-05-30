package de.dhbw.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.stream.Stream;

import de.dhbw.game.Difficulty;
import de.dhbw.game.settings.Settings;

public class PreferenceManager {

    /**
     * Provides constant-strings for saving scores into the SharedPreferences.
     */
    public enum Statistics {
        MAX_WAVE, ENEMIES_KILLED, BUILT_TOWERS, UPGRADES, MONEY_SPENT,
        EASY_MAX_WAVE, EASY_ENEMIES_KILLED, EASY_BUILT_TOWERS, EASY_UPGRADES, EASY_MONEY_SPENT,
        MEDIUM_MAX_WAVE, MEDIUM_ENEMIES_KILLED, MEDIUM_BUILT_TOWERS, MEDIUM_UPGRADES, MEDIUM_MONEY_SPENT,
        HARD_MAX_WAVE, HARD_ENEMIES_KILLED, HARD_BUILT_TOWERS, HARD_UPGRADES, HARD_MONEY_SPENT;

        public static Statistics getStatisticsStringByDifficulty(Difficulty difficulty, Statistics statistics) {
            return Stream.of(Statistics.class.getEnumConstants()).filter(constant -> constant.toString().equalsIgnoreCase(difficulty.toString() + "_" + statistics)).findFirst().get();
        }
    }

    private static PreferenceManager preferenceManager;

    private static final String pref_DHTD_Statistics = "DHTD_Statistics";
    private static final String pref_DHTD_Settings = "DHTD_Settings";

    private Context context;
    private SharedPreferences preferencesStatistics, preferencesSettings;
    private SharedPreferences.Editor editorStatistics, editorSettings;

    /**
     * Constructor
     */
    private PreferenceManager() {
        preferenceManager = this;
    }

    /**
     * Initializes a PreferenceManager instance for being able to save and read strings from/into SharedPreferences.
     * @param context
     */
    public static void init(Context context) {
        preferenceManager = new PreferenceManager();
        preferenceManager.context = context;

        preferenceManager.preferencesStatistics =  preferenceManager.context.getSharedPreferences(pref_DHTD_Statistics, Context.MODE_PRIVATE);
        preferenceManager.preferencesSettings = preferenceManager.context.getSharedPreferences(pref_DHTD_Settings, Context.MODE_PRIVATE);

        preferenceManager.editorStatistics = preferenceManager.preferencesStatistics.edit();
        preferenceManager.editorSettings = preferenceManager.preferencesSettings.edit();
    }

    /**
     * Read a statistics value from the SharedPreferences.
     * @param statsName
     * @return value or "0" if no other value is saved.
     */
    public static String getStatisticsValue(Statistics statsName) {
        return preferenceManager.preferencesStatistics.getString(statsName.toString(), "0");
    }

    /**
     * Write a statistics value to the SharedPreferences.
     * @param statsName
     * @param statsValue
     */
    public static void setStatisticsValue(Statistics statsName, String statsValue) {
        preferenceManager.editorStatistics.putString(statsName.toString(), statsValue);
        preferenceManager.editorStatistics.apply();
    }

    /**
     * Remove the value of the specified statistics in the SharedPreferences.
     * @param statsName
     */
    public static void removeStatisticsValue(Statistics statsName) {
        preferenceManager.editorStatistics.remove(statsName.toString());
        preferenceManager.editorStatistics.apply();
    }

    /**
     * Read a settings value from the SharedPreferences.
     * @param settingsName
     * @return value or null if no other value is saved.
     */
    public static String getSettingsValue(Settings settingsName) {
        return preferenceManager.preferencesSettings.getString(settingsName.toString(), null);
    }

    /**
     * Write a settings value to the SharedPreferences.
     * @param settingsName
     * @param settingsValue
     */
    public static void setSettingsValue(Settings settingsName, String settingsValue) {
        preferenceManager.editorSettings.putString(settingsName.toString(), settingsValue);
        preferenceManager.editorSettings.apply();
    }

    /**
     * Remove the value of the specified settings in the SharedPreferences.
     * @param settingsName
     */
    public static void removeSettingsValue(Settings settingsName) {
        preferenceManager.editorSettings.remove(settingsName.toString());
        preferenceManager.editorSettings.apply();
    }

}
