package de.dhbw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;
import de.dhbw.R;
import static de.dhbw.util.Constants.ICON_MUSIC_OFF;
import static de.dhbw.util.Constants.ICON_MUSIC_ON;
import de.dhbw.game.Difficulty;
import de.dhbw.game.settings.ISettingsManager;
import de.dhbw.game.settings.SettingsToggleButton;
import de.dhbw.util.DifficultyFragmentAdapter;
import de.dhbw.util.PreferenceManager;

public class MainActivity extends AppCompatActivity implements ISettingsManager{

    private MediaPlayer mediaPlayer = new MediaPlayer();

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
            mediaPlayer.reset();
            gameIntent.putExtra(getString(R.string.difficulty), Difficulty.asDifficulty(viewPager.getCurrentItem()));
            startActivity(gameIntent);
            finish();
        });

        //Setup MediaPlayer for music on the MainActivity
        mediaPlayer = MediaPlayer.create(this, R.raw.tower_defense_title_soundtrack);
        Button toggleMusicButton = findViewById(R.id.button_title_sound);
        new SettingsToggleButton(this, toggleMusicButton , PreferenceManager.Settings.MUSIC, ICON_MUSIC_ON, ICON_MUSIC_OFF);
    }

    @Override
    public void toggle(PreferenceManager.Settings setting, boolean on) {
        if(setting==PreferenceManager.Settings.MUSIC){
            if(on){
                mediaPlayer.start();
            }else{
                mediaPlayer.pause();
            }
        }
    }
}
