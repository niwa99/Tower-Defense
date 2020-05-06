package de.dhbw.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

import androidx.viewpager.widget.ViewPager;
import de.dhbw.R;
import de.dhbw.game.Difficulty;
import de.dhbw.util.DifficultyFragmentAdapter;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.init(getApplicationContext());

        //Initialize ViewPager for Difficulty-Selection
        DifficultyFragmentAdapter pagerAdapter = new DifficultyFragmentAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.difficulty_pager);
        viewPager.setAdapter(pagerAdapter);

        Button playButton = findViewById(R.id.playButtonMain);
        playButton.setOnClickListener(view -> {
            Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
            gameIntent.putExtra(getString(R.string.difficulty), Difficulty.asDifficulty(viewPager.getCurrentItem()));
            startActivity(gameIntent);
        });

        //Setup MediaPlayer for music on the MainActivity
        mediaPlayer = MediaPlayer.create(this, R.raw.tower_defense_title_soundtrack);
        initTitleSounds();

        //Setup reset-stats button
        Button buttonResetStats = findViewById(R.id.button_reset_stats);
        buttonResetStats.setOnClickListener(view -> {
            Difficulty difficulty = Difficulty.asDifficulty(viewPager.getCurrentItem());
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Reset Statistics");
            builder.setMessage("Do you really want to reset the statistics for the difficulty " + difficulty + "?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                resetStatistics(difficulty);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(Difficulty.asNumber(difficulty), false);
            });
            builder.setNegativeButton("No", null);
            builder.create().show();
        });
    }

    private void initTitleSounds() {

        Button soundButton = findViewById(R.id.button_title_sound);
        soundButton.setOnClickListener(view -> {
            if (soundButton.getBackground().getConstantState() == Objects.requireNonNull(getDrawable(R.drawable.sound)).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND).equals(STATUS_ON)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_OFF);
                soundButton.setBackgroundResource(R.drawable.no_sound);
                mediaPlayer.pause();
            } else if (soundButton.getBackground().getConstantState() == Objects.requireNonNull(getDrawable(R.drawable.no_sound)).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND).equals(STATUS_OFF)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_ON);
                soundButton.setBackgroundResource(R.drawable.sound);
                mediaPlayer.start();
            }
        });

        //Initial check on MainActivity-Creation if sound should be played
        String settings_title_sound = PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND);
        if (settings_title_sound == null) {
            PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_ON);
            soundButton.setBackgroundResource(R.drawable.sound);
            startMediaplayer();
        } else if (settings_title_sound.equals(STATUS_ON)) {
            soundButton.setBackgroundResource(R.drawable.sound);
            startMediaplayer();
        } else if (settings_title_sound.equals(STATUS_OFF)) {
            soundButton.setBackgroundResource(R.drawable.no_sound);
        }
    }

    public void startMediaplayer() {
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
    }

    private void resetStatistics(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.EASY_MAX_WAVE);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.EASY_ENEMIES_KILLED);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.EASY_BUILT_TOWERS);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.EASY_UPGRADES);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.EASY_MONEY_SPENT);
                break;
            case MEDIUM:
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.MEDIUM_MAX_WAVE);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.MEDIUM_ENEMIES_KILLED);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.MEDIUM_BUILT_TOWERS);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.MEDIUM_UPGRADES);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.MEDIUM_MONEY_SPENT);
                break;
            case HARD:
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.HARD_MAX_WAVE);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.HARD_ENEMIES_KILLED);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.HARD_BUILT_TOWERS);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.HARD_UPGRADES);
                PreferenceManager.removeStatisticsValue(PreferenceManager.Statistics.HARD_MONEY_SPENT);
                break;
        }
    }
}
