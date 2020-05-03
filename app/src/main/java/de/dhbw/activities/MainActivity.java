package de.dhbw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;
import de.dhbw.R;
import de.dhbw.util.DifficultyFragmentAdapter;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private MediaPlayer mediaPlayer;
    private ViewPager viewPager;
    private DifficultyFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.tower_defense_title_soundtrack);

        PreferenceManager.init();

        //Initialize ViewPager for Difficulties
        viewPager = findViewById(R.id.difficulty_pager);
        pagerAdapter = new DifficultyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //Initialize Play-Button
        Button playButton = findViewById(R.id.playButtonMain);
        playButton.setOnClickListener(view -> {
            Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
            gameIntent.putExtra(getString(R.string.difficulty), viewPager.getCurrentItem());
            startActivity(gameIntent);
        });

        initTitleSounds();
    }

    public static Context getAppContext() {
        return context;
    }

    private void initTitleSounds() {
        Button soundButton = findViewById(R.id.button_title_sound);

        soundButton.setOnClickListener(view -> {
            if (soundButton.getBackground().getConstantState() == getDrawable(R.drawable.sound).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND).equals(STATUS_ON)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_OFF);
                soundButton.setBackgroundResource(R.drawable.no_sound);
                mediaPlayer.pause();
            } else if (soundButton.getBackground().getConstantState() == getDrawable(R.drawable.no_sound).getConstantState() && PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND).equals(STATUS_OFF)) {
                PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_ON);
                soundButton.setBackgroundResource(R.drawable.sound);
                mediaPlayer.start();
            }
        });

        String settings_title_sound = PreferenceManager.getSettingsValue(PreferenceManager.Settings.TITLE_SOUND);
        if (settings_title_sound == null) {
            PreferenceManager.setSettingsValue(PreferenceManager.Settings.TITLE_SOUND, STATUS_ON);
            soundButton.setBackgroundResource(R.drawable.sound);
            startMediaplayer();
        } else if (settings_title_sound.equals(STATUS_ON)) {
            soundButton.setBackgroundResource(R.drawable.sound);
            startMediaplayer();
        } else {
            soundButton.setBackgroundResource(R.drawable.no_sound);
        }
    }

    public void startMediaplayer(){
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
    }
}
