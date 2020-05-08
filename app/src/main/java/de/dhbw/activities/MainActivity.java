package de.dhbw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;
import de.dhbw.R;
import de.dhbw.game.Difficulty;
import de.dhbw.game.buttons.ToggleMusicButton;
import de.dhbw.util.DifficultyFragmentAdapter;
import de.dhbw.util.PreferenceManager;

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
        Button soundButton = findViewById(R.id.button_title_sound);
        new ToggleMusicButton(this, soundButton, mediaPlayer);
    }
}
