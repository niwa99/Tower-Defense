package de.dhbw.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.DialogFragment;
import de.dhbw.activities.GameActivity;
import de.dhbw.game.match.AMatch;
import de.dhbw.game.match.EasyMatch;
import de.dhbw.game.match.HardMatch;
import de.dhbw.game.match.MediumMatch;
import de.dhbw.game.wave.AWave;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.ObjectStorage;
import de.dhbw.util.Position;

public class Game {
    private LinearLayout.LayoutParams buttonSizeParams;
    private List<Button> mapButtons;
    private Optional<Button> clickedButton = Optional.ofNullable(null);
    private IStatusBar statusBar;
    private GameActivity gameActivity;
    //private IStatusBar statusBar = ObjectStorage.getGameActivity();
    private MapStructure mapStructure;
    private MatchField matchField;
    private FrameLayout mapLayout;
    private Timer gameTimer = new Timer();
    private Timer waveTimer =  new Timer();
    private boolean lastWaveOut = false;
    private boolean lastEnemyOfWaveOut = false;
    private CountDownTimer countDownTimer;
    private AMatch match;

    //status
    private int lifePoints = 100;
    private int money = 0;
    private int currentWave = 0;

	public Game(GameActivity gameActivity, FrameLayout mapLayout) {
	    statusBar = gameActivity;
	    this.gameActivity = gameActivity;
	    this.mapLayout = mapLayout;
	    mapStructure = new MapStructure();
		//setMapStructure(new MapStructure());
		buttonSizeParams = new LinearLayout.LayoutParams(MapStructure.getSizeField(), MapStructure.getSizeField());
		mapButtons = new ArrayList<>();
		matchField = new MatchField(this, mapStructure, gameActivity, mapLayout);
		//setMatchField(new MatchField());
        generateButtonsOnMap();
	}

	public void stop() {
	    waveTimer.cancel();
	    gameTimer.cancel();
        matchField.stopGame();
	    ObjectStorage.clear();
    }

	public void startGame(Difficulty difficulty) {
	    switch (difficulty) {
            case EASY:
                this.match = new EasyMatch();
                match.create(mapLayout, gameActivity);
                runGame();
                break;
            case MEDIUM:
                this.match = new MediumMatch();
                match.create(mapLayout, gameActivity);
                runGame();
                break;
            case HARD:
                this.match = new HardMatch();
                match.create(mapLayout, gameActivity);
                runGame();
                break;
        }
    }

    private void runGame(){
	    this.money = match.getStartMoney();
	    updateStatusBar();
	    if(match.hasNext()) {
            gameTimer.cancel();;
            gameTimer = new Timer();
            prepareCountDown(match.getWaveTime());
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (match.hasNext()) {
                        lastEnemyOfWaveOut = false;
                        currentWave = match.waveNumber();
                        updateStatusBar();
                        countDownTimer.cancel();
                        startNextWave(match.next().get());
                    } else {
                        lastWaveOut = true;
                    }
                }
            }, 0, match.getWaveTime() * 1000);
        }
    }

    private void startNextWave(AWave wave){
	    waveTimer = new Timer();
        countDownTimer.start();
        waveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(wave.hasNext()){
                    matchField.addEnemy(wave.next());
                    //getMatchField().addEnemy(wave.next());
                }else{
                    lastEnemyOfWaveOut = true;
                    cancel();
                }
            }
        }, 0, wave.getWaveSpeed());
    }

    private void generateButtonsOnMap() {
        View.OnClickListener listener = v -> {
            Optional<Button> button = mapButtons.stream().filter(e -> v.getTransitionName()==e.getTransitionName()).findFirst();
            if(button.isPresent()){
                Position pos = getPositionFromButtonId(button.get().getTransitionName());
                Field field = mapStructure.getField(pos);
                //Field field = getMapStructure().getField(pos);
                if(field.getFieldDescription()==FieldDescription.FREE){
                    if(clickedButton.isPresent()){
                        if(clickedButton.get().getTransitionName()==button.get().getTransitionName()){
                            clickedButton = Optional.ofNullable(null);
                            button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            //button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            createNewTowerOnField(getPositionFromButtonId(button.get().getTransitionName()));
                        }else{
                            clickedButton.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            //clickedButton.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                            //button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                            clickedButton = button;
                        }
                    }else{
                        clickedButton = button;
                        button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                        //button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                    }
                }else if(field.getFieldDescription()==FieldDescription.TOWER){
                    Optional<Tower> tower = matchField.getTower(field);
                    //Optional<Tower> tower = getMatchField().getTower(field);
                    if(tower.isPresent()){
                        sellTower(tower.get(), field);
                    }
                }
            }
        };

        View.OnClickListener spawnFieldListener = v -> runGame();

        int count = 0;
        mapStructure.getFields().stream().forEach(f -> {
        //getMapStructure().getFields().stream().forEach(f -> {
            Button fieldButton = new Button(gameActivity);
            //Button fieldButton = new Button(getContext());
            fieldButton.setX(f.getSpawnPoint().getX());
            fieldButton.setY(f.getSpawnPoint().getY());
            fieldButton.setLayoutParams(buttonSizeParams);
            fieldButton.setTransitionName(f.getId());
            if (f.getFieldDescription().equals(FieldDescription.PATH)) {
                fieldButton.setEnabled(false);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                //fieldButton.setBackground(getContext().getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                mapLayout.addView(fieldButton);
                //getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if(f.getFieldDescription().equals(FieldDescription.SPAWN)){
                fieldButton.setOnClickListener(spawnFieldListener);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                //fieldButton.setBackground(getContext().getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                mapLayout.addView(fieldButton);
                //getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if (f.getFieldDescription().equals(FieldDescription.FREE)) {
                fieldButton.setOnClickListener(listener);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                //fieldButton.setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                mapLayout.addView(fieldButton);
                //getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            }
        });
    }

    public void sellTower(Tower tower, Field field){
	    addMoney((int)Math.round(tower.getCosts()*0.5));
        matchField.removeTower(tower);
        //getMatchField().removeTower(tower);
        mapLayout.removeView(((DefTower)tower).getDefTowerImage());
        //getMapLayout().removeView(((DefTower)tower).getDefTowerImage());
        field.setFieldDescription(FieldDescription.FREE);
    }

    public Position getPositionFromButtonId(String id){
        String[] result = id.split("01230");
	    return new Position(Integer.valueOf(result[0]), Integer.valueOf(result[1]));
    }

    public void createNewTowerOnField(Position pos){
        if(subMoney(DefTower.getDefTowerCostsByLevel(1)) && mapStructure.getField(pos).getFieldDescription()==FieldDescription.FREE) {
	    //if(subMoney(DefTower.getDefTowerCostsByLevel(1)) && getMapStructure().getField(pos).getFieldDescription()==FieldDescription.FREE) {
            DefTower newTower = new DefTower("tower1", mapStructure.getField(pos), 1, gameActivity, mapLayout, matchField);
            //DefTower newTower = new DefTower("tower1", getMapStructure().getField(pos), 1);
            matchField.addTower(newTower);
            //getMatchField().addTower(newTower);
            mapStructure.getField(pos).setFieldDescription(FieldDescription.TOWER);
            //getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
        }
    }

	private void updateStatusBar(){
	    statusBar.setLifePoints(String.valueOf(lifePoints));
	    statusBar.setMoney(String.valueOf(money));
	    statusBar.setCurrentWave(String.valueOf(currentWave));
    }

	public void addMoney(int money){
	    this.money+=money;
	    updateStatusBar();
    }

    public boolean subMoney(int money){
	    if(this.money>=money){
            this.money-=money;
            updateStatusBar();
            return true;
        }
	    return false;
    }

    public boolean decreaseLifePoints(int lifePoints){
	    if(this.lifePoints > lifePoints){
            this.lifePoints-=lifePoints;
            updateStatusBar();
            return true;
        }
	    this.lifePoints=0;
	    updateStatusBar();
	    return false;
    }

    public void loseGame(){
        //gameActivity.runOnUiThread(() -> Toast.makeText(gameActivity, "You lost the game", Toast.LENGTH_LONG));
        //gameActivity.backToMainMenu();

        waveTimer.cancel();
        gameTimer.cancel();
        DialogFragment dialog = new MyDialogFragment("You have lost the game!", gameActivity);
        dialog.show(gameActivity.getSupportFragmentManager(), "MyDialogFragmentTag");
    }

    public void winGame(){
        //gameActivity.runOnUiThread(() -> Toast.makeText(gameActivity, "You won the game", Toast.LENGTH_LONG));
        //gameActivity.backToMainMenu();

        waveTimer.cancel();
        gameTimer.cancel();
        DialogFragment dialog = new MyDialogFragment("You have won the game!", gameActivity);
        dialog.show(gameActivity.getSupportFragmentManager(), "MyDialogFragmentTag");
    }

    private void prepareCountDown(int sec){
	    if(countDownTimer!=null){
	        countDownTimer.cancel();
        }
        this.countDownTimer = new CountDownTimer(sec*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                statusBar.setWaveTimeRemaining(String.valueOf(Math.round(millisUntilFinished/1000)));
            }

            public void onFinish() {
                if(allEnemiesSpawned()){
                    statusBar.setWaveTimeRemaining("LAST");
                }
            }
        };
    }

    public static class MyDialogFragment extends DialogFragment {

	    private final String message;
	    private final GameActivity gameActivity;

        public MyDialogFragment(String message, GameActivity gameActivity) {
            this.message = message;
            this.gameActivity = gameActivity;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("END");
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    gameActivity.backToMainMenu();
                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    public boolean allEnemiesSpawned(){
	    return lastWaveOut && lastEnemyOfWaveOut;
    }

    public int getLifePoints() {
	    return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
	    this.lifePoints = lifePoints;
    }

    public int getMoney() {
	    return money;
    }

    public void setMoney(int money) {
	    this.money = money;
    }
}