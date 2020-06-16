package de.dhbw.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import de.dhbw.ImageElevation;
import de.dhbw.R;
import de.dhbw.game.Difficulty;
import de.dhbw.game.Game;
import de.dhbw.game.IStatusBar;
import de.dhbw.game.StatusBar;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.util.ObjectType;

import static de.dhbw.util.ObjectType.BULLET;
import static de.dhbw.util.ObjectType.ENEMY;
import static de.dhbw.util.ObjectType.FIELD;
import static de.dhbw.util.ObjectType.TOWER;

public class GameActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler handler = getUiHandler();
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private FrameLayout mapLayout;
    private FrameLayout mapLayoutEnemies;
    private FrameLayout mapLayoutTowers;
    private FrameLayout mapLayoutBullets;

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
        TextView textLifePoints = findViewById(R.id.textLivePoints);
        TextView textMoney = findViewById(R.id.textMoney);
        TextView textCurrentWave = findViewById(R.id.textCurrentWave);
        TextView textWaveRemaining = findViewById(R.id.textWaveRemaining);
        StatusBar status = new StatusBar(textLifePoints, textMoney, textCurrentWave, textWaveRemaining, getStatusBarHandler());

        //Initialize Home Button
        Button buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.openSettings();
            }
        });

        mapLayout = findViewById(R.id.map);
        mapLayout.setElevation(ImageElevation.FIELD.elevation);
        mapLayoutEnemies = findViewById(R.id.map_enemies);
        mapLayoutEnemies.setElevation(ImageElevation.ENEMIES.elevation);
        mapLayoutTowers = findViewById(R.id.map_towers);
        mapLayoutTowers.setElevation(ImageElevation.TOWER.elevation);
        mapLayoutBullets = findViewById(R.id.map_bullets);
        mapLayoutBullets.setElevation(ImageElevation.BULLET.elevation);

        game = new Game(GameActivity.this, status);

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

    public void showBuilder(AlertDialog.Builder builder){
        runOnUiThread(() -> builder.create().show());
    }

    public Handler getHandler() {
        return handler;
    }

    @SuppressLint("HandlerLeak")
    private Handler getUiHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message message){
                if(message.what==UIActions.moveImage.getId()){
                    View view = ((View)message.obj);
                    view.setX(message.arg1);
                    view.setY(message.arg2);
                }else if(message.what==UIActions.rotateImage.getId()){
                    View view = ((View)message.obj);
                    view.setRotation(message.arg1);
                }else if(message.what==UIActions.setImageResource.getId()){
                    ImageView view = ((ImageView)message.obj);
                    view.setImageResource(message.arg1);
                }else if(message.what==UIActions.addView.getId()){
                    View view = ((View)message.obj);
                    switch(message.arg1) {
                        case FIELD:
                            view.setTag(FIELD);
                            mapLayout.addView(view);
                            mapLayout.invalidate();
                            break;
                        case ENEMY:
                            view.setTag(ENEMY);
                            mapLayoutEnemies.addView(view);
                            mapLayoutEnemies.invalidate();
                            break;
                        case TOWER:
                            view.setTag(TOWER);
                            mapLayoutTowers.addView(view);
                            mapLayoutTowers.invalidate();
                            break;
                        case BULLET:
                            view.setTag(BULLET);
                            mapLayoutBullets.addView(view);
                            mapLayoutBullets.invalidate();
                            break;
                        default:
                            view.setTag(FIELD);
                            mapLayout.addView(view);
                            mapLayout.invalidate();
                    }
                }else if(message.what==UIActions.removeView.getId()){
                    View view = ((View)message.obj);
                    switch((int) view.getTag()) {
                        case FIELD:
                            mapLayout.removeView(view);
                            mapLayout.invalidate();
                            break;
                        case ENEMY:
                            mapLayoutEnemies.removeView(view);
                            mapLayoutEnemies.invalidate();
                            break;
                        case TOWER:
                            mapLayoutTowers.removeView(view);
                            mapLayoutTowers.invalidate();
                            break;
                        case BULLET:
                            mapLayoutBullets.removeView(view);
                            mapLayoutBullets.invalidate();
                            break;
                        default:
                            mapLayout.removeView(view);
                            mapLayout.invalidate();
                    }
                } else if (message.what == UIActions.setForeGound.getId()) {
                    View view = (View) message.obj;
                    view.setForeground(getDrawable(message.arg1));
                } else if (message.what == UIActions.startAnimator.getId()) {
                    ValueAnimator animator = (ValueAnimator) message.obj;
                    animator.start();
                }
                super.handleMessage(message);
            }
        };
    }

    @SuppressLint("HandlerLeak")
    public Handler getStatusBarHandler(){
        return new Handler() {
            @Override
            public void handleMessage(Message message) {
                if(message.what == UIActions.setText.getId()){
                    TextView view = ((TextView)message.obj);
                    view.setText(String.valueOf(message.arg1));
                }
            }
        };
    }

    public static void runActionOnUI(Handler handler, UIActions action, Object object, int... args) {
        Message message = new Message();
        message.what = action.getId();
        message.obj = object;
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                message.arg1 = args[0];
            } else if (i == 1) {
                message.arg2 = args[1];
            } else {
                break;
            }
        }
        handler.sendMessage(message);
    }
}