package de.dhbw.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import de.dhbw.R;
import de.dhbw.game.Difficulty;
import de.dhbw.game.Game;
import de.dhbw.game.IStatusBar;
import de.dhbw.map.objects.tower.TowerType;

public class GameActivity extends AppCompatActivity implements IStatusBar {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    //status bar
    private TextView textLifePoints;
    private TextView textMoney;
    private TextView textCurrentWave;
    private TextView textWaveRemaining;

    private FrameLayout mapLayout;
    private Game game;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private View mControlsView;

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private boolean mVisible;

    private final Runnable mHideRunnable = () -> hide();
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener mDelayHideTouchListener = (view, motionEvent) -> {
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS);
        }
        return false;
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void setupAndroidFullscreenAndLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_game);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(view -> toggle());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnToMainMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAndroidFullscreenAndLayout();

        //identify status bar TextViews
        this.textLifePoints = findViewById(R.id.textLivePoints);
        this.textMoney = findViewById(R.id.textMoney);
        this.textCurrentWave = findViewById(R.id.textCurrentWave);
        this.textWaveRemaining = findViewById(R.id.textWaveRemaining);

        //Initialize Home Button
        Button buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.openSettings();
            }
        });

        mapLayout = findViewById(R.id.map);

        game = new Game(GameActivity.this);

        Difficulty chosenDifficulty = (Difficulty) getIntent().getSerializableExtra(getString(R.string.difficulty));
        if (chosenDifficulty != null) {
            game.init(chosenDifficulty);
            game.start();
        }
    }


    public void returnToMainMenu() {
        game.stop(false);
        Intent intentMenu = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intentMenu);
        finish();
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public FrameLayout getMapFrameLayout() {
        return mapLayout;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void setLifePoints(String points) {
        runOnUiThread(() -> textLifePoints.setText(points));
    }

    @Override
    public void setMoney(String money) {
        runOnUiThread(() -> textMoney.setText(money));
    }

    @Override
    public void setCurrentWaveNumber(String wave) {
        runOnUiThread(() -> textCurrentWave.setText((getString(R.string.wave_title) + wave)));
    }

    @Override
    public void setWaveTimeRemaining(String sec) {
        runOnUiThread(() -> textWaveRemaining.setText(sec));
    }
}