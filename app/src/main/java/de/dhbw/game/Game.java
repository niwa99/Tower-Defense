package de.dhbw.game;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import de.dhbw.activities.GameActivity;
import de.dhbw.R;
import de.dhbw.game.match.AMatch;
import de.dhbw.game.match.EasyMatch;
import de.dhbw.game.match.HardMatch;
import de.dhbw.game.match.MediumMatch;
import de.dhbw.game.popups.MenuSettings;
import de.dhbw.game.popups.MenuTowerSelection;
import de.dhbw.game.wave.AWave;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;
import de.dhbw.util.PreferenceManager;

public class Game {

    private GameActivity gameActivity;

    private MapStructure mapStructure;
    private MatchField matchField;
    private AMatch match;

    private Optional<Button> clickedButton = Optional.ofNullable(null);

    //responsible for organizing waves
    private Timer gameTimer = new Timer();
    //responsible for spawning enemies in waves
    private Timer waveTimer =  new Timer();
    //responsible for status bar timer
    private final StatusBarCountDownTimer countDownTimer;

    private boolean toggleSound = false;
    private boolean lastWaveOut = false;
    private boolean lastEnemyOfWaveSpawned = false;
    private IMoneyListener moneyListener = null;

    private int lifePoints = 100;
    private int money = 0;

    private int currentWaveNumber = 0;
    private int numberOfEnemiesKilled = 0;
    private int numberOfBuiltTowers = 0;
    private int numberOfUpgrades = 0;
    private int moneySpent = 0;

	public Game(GameActivity gameActivity) {
	    this.gameActivity = gameActivity;
	    mapStructure = new MapStructure();
        matchField = new MatchField(gameActivity);
        countDownTimer = new StatusBarCountDownTimer(gameActivity);
        generateButtonsOnMap();
	}

	public MapStructure getMapStructure() {
	    return mapStructure;
    }

    public MatchField getMatchField() {
	    return matchField;
    }

    public void stop(boolean isRegularStop) {
        waveTimer.cancel();
        gameTimer.cancel();
        countDownTimer.stopTimer();
        if (!isRegularStop) {
            matchField.stopTimer();
        }
    }

    public void openSettings(){
        Intent intent = new Intent(gameActivity, MenuSettings.class);
        MenuSettings.gameActivity = gameActivity;
        matchField.pauseTimers();
        pauseTimers();
        gameActivity.startActivity(intent);
    }

    public void pauseTimers(){
        final long time = System.currentTimeMillis();
        match.calculateDelay(time);
        countDownTimer.stopTimer();
        waveTimer.cancel();
        gameTimer.cancel();
    }

    public void continueTimers(){
        startWave(match.getCurrent(), Math.round(match.getDelay()/1000));
    }

	public void init(Difficulty difficulty) {
	    switch (difficulty) {
            case EASY:
                this.match = new EasyMatch();
                break;
            case MEDIUM:
                this.match = new MediumMatch();
                break;
            case HARD:
                this.match = new HardMatch();
                break;
        }
        match.create(gameActivity);
    }

    public void start() {
	    this.money = match.getStartMoney();
	    updateStatusBar();
	    startNextWave(0);
    }

    public void startNextWave(long delay){
        if (match.hasNext()) {
            gameTimer.cancel();
            gameTimer = new Timer();
            gameTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Optional<AWave> wave = match.next();
                    if(wave.isPresent()){
                        startWave(match.next().get(), match.getWaveTime());
                    }
                    if (!match.hasNext()) {
                        lastWaveOut = true;
                    }
                }
            }, delay);
        }else{
            lastWaveOut = true;
        }
    }

    private void startWave(AWave wave, int seconds) {
	    System.out.println("test ------------>" + seconds);
        //timer
	    waveTimer.cancel();
	    waveTimer = new Timer();
        countDownTimer.timer(seconds);
        
        //status
        currentWaveNumber = match.getCurrentWaveNumber()+1;
        updateStatusBar();

        //pause actions
        match.setLastTimeActionMillis(System.currentTimeMillis());
        match.setDelay(seconds*1000);

        //next wave
        startNextWave(seconds*1000);
        
        waveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                lastEnemyOfWaveSpawned = false;
                
                if (wave.hasNext()) {
                    matchField.addEnemy(wave.next());
                } else {
                    lastEnemyOfWaveSpawned = true;
                    cancel();
                }
                wave.setLastTimeActionMillis(System.currentTimeMillis());
            }
        }, wave.getDelay(), wave.getWaveSpeed());
    }

    public void createNewTowerOnField(Position pos) {
        Intent intent = new Intent(gameActivity, MenuTowerSelection.class);
        intent.putExtra(gameActivity.getString(R.string.position), pos);
        MenuTowerSelection.game = this;
        gameActivity.startActivity(intent);
    }

    public void buildTower(TowerType type, Position pos) {
        if (subMoney(type.getPrice()) && getMapStructure().getField(pos).getFieldDescription() == FieldDescription.FREE) {
            switch(type) {
                case ARTILLERY:
                    DefTower newTower = new DefTower("tower1", getMapStructure().getField(pos), 1, gameActivity);
                    getMatchField().addTower(newTower);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
                case FREEZER:
                case BOOMBASTIC:
                case PLASMARIZER:
                case ASSAULTLASER:
                    break;
            }
        }
    }

    public void sellTower(Tower tower, Field field) {
	    addMoney((int) Math.round(tower.getCosts() * 0.5));
        matchField.removeTower(tower);
        gameActivity.getMapFrameLayout().removeView(((DefTower) tower).getDefTowerImage());
        field.setFieldDescription(FieldDescription.FREE);
    }

	private void updateStatusBar() {
	    gameActivity.setLifePoints(String.valueOf(lifePoints));
	    gameActivity.setMoney(String.valueOf(money));
	    gameActivity.setCurrentWaveNumber(String.valueOf(currentWaveNumber));
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public boolean decreaseLifePoints(int lifePoints) {
        if (this.lifePoints > lifePoints) {
            this.lifePoints -= lifePoints;
            updateStatusBar();
            return true;
        }
        this.lifePoints = 0;
        updateStatusBar();
        return false;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

	public void addMoney(int money) {
	    this.money += money;
	    updateStatusBar();
	    if (moneyListener != null) {
	        moneyListener.performMoneyUpdate(this.money);
        }
    }

    public boolean subMoney(int money) {
	    if (this.money >= money) {
            this.money -= money;
            updateStatusBar();
            increaseMoneySpent(money);
            return true;
        }
	    return false;
    }

    public boolean allEnemiesSpawned() {
        return lastWaveOut && lastEnemyOfWaveSpawned;
    }

    public void loseActions() {
	    this.stop(true);
	    updateStatisticsIfHighScore();
        showGameFinishedDialog(gameActivity, "You have lost the game!");
    }

    public void winActions() {
        this.stop(true);
        updateStatisticsIfHighScore();
        showGameFinishedDialog(gameActivity, "You have won the game!");
    }

    private void showGameFinishedDialog(GameActivity gameActivity, String message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
	    builder.setTitle("Game finished!");
	    builder.setMessage(message);
	    builder.setPositiveButton("OK", (dialogInterface, i) -> gameActivity.returnToMainMenu());
	    gameActivity.runOnUiThread(() -> builder.create().show());
    }

    public void setMenu(IMoneyListener listener) {
	    moneyListener = listener;
    }

    public void increaseNumberOfEnemiesKilled(int increase) {
	    numberOfEnemiesKilled += increase;
    }

    public void increaseNumberOfBuiltTowers(int increase) {
	    numberOfBuiltTowers += increase;
    }

    public void increaseNumberOfUpgrades(int increase) {
	    numberOfUpgrades += increase;
    }

    public void increaseMoneySpent(int increase) {
	    moneySpent += increase;
    }

    public void setToggleSound(boolean toggle){
	    this.toggleSound=toggle;
    }

    public boolean getToggleSound(){
	    return toggleSound;
    }

    public void updateStatisticsIfHighScore() {
        if (match instanceof EasyMatch) {
            if (currentWaveNumber >= Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_MAX_WAVE))) {
                if (numberOfEnemiesKilled > Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_ENEMIES_KILLED))) {
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.EASY_MAX_WAVE, String.valueOf(currentWaveNumber));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.EASY_ENEMIES_KILLED, String.valueOf(numberOfEnemiesKilled));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.EASY_BUILT_TOWERS, String.valueOf(numberOfBuiltTowers));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.EASY_UPGRADES, String.valueOf(numberOfUpgrades));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.EASY_MONEY_SPENT, String.valueOf(moneySpent));
                }
            }
        } else if (match instanceof MediumMatch) {
            if (currentWaveNumber >= Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_MAX_WAVE))) {
                if (numberOfEnemiesKilled > Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_ENEMIES_KILLED))) {
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.MEDIUM_MAX_WAVE, String.valueOf(currentWaveNumber));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.MEDIUM_ENEMIES_KILLED, String.valueOf(numberOfEnemiesKilled));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.MEDIUM_BUILT_TOWERS, String.valueOf(numberOfBuiltTowers));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.MEDIUM_UPGRADES, String.valueOf(numberOfUpgrades));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.MEDIUM_MONEY_SPENT, String.valueOf(moneySpent));
                }
            }
        } else if (match instanceof HardMatch) {
            if (currentWaveNumber >= Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_MAX_WAVE))) {
                if (numberOfEnemiesKilled > Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_ENEMIES_KILLED))) {
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.HARD_MAX_WAVE, String.valueOf(currentWaveNumber));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.HARD_ENEMIES_KILLED, String.valueOf(numberOfEnemiesKilled));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.HARD_BUILT_TOWERS, String.valueOf(numberOfBuiltTowers));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.HARD_UPGRADES, String.valueOf(numberOfUpgrades));
                    PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.HARD_MONEY_SPENT, String.valueOf(moneySpent));
                }
            }
        }
    }

    public Position getPositionFromButtonId(String id) {
        String[] result = id.split("01230");
        return new Position(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
    }

    private void generateButtonsOnMap() {

        List<Button> mapButtons = new ArrayList<>();
        View.OnClickListener listener = view -> {
            Optional<Button> button = mapButtons.stream().filter(e -> view.getTransitionName().equals(e.getTransitionName())).findFirst();
            if (button.isPresent()) {
                Position pos = getPositionFromButtonId(button.get().getTransitionName());
                Field field = mapStructure.getField(pos);
                if (field.getFieldDescription() == FieldDescription.FREE) {
                    if (clickedButton.isPresent()) {
                        if (clickedButton.get().getTransitionName().equals(button.get().getTransitionName())) {
                            clickedButton = Optional.ofNullable(null);
                            button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            createNewTowerOnField(getPositionFromButtonId(button.get().getTransitionName()));
                        } else {
                            clickedButton.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                            clickedButton = button;
                        }
                    } else {
                        clickedButton = button;
                        button.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                    }
                } else if (field.getFieldDescription() == FieldDescription.TOWER) {
                    Optional<Tower> tower = matchField.getTower(field);
                    if (tower.isPresent()) {
                        sellTower(tower.get(), field);
                    }
                }
            }
        };

        View.OnClickListener spawnFieldListener = view -> {
            if(lastEnemyOfWaveSpawned && !lastWaveOut){
                startNextWave(0);
            }
        };

        LinearLayout.LayoutParams buttonSizeParams = new LinearLayout.LayoutParams(MapStructure.getSizeField(), MapStructure.getSizeField());
        mapStructure.getFields().stream().forEach(field -> {
            Button fieldButton = new Button(gameActivity);
            fieldButton.setX(field.getSpawnPoint().getX());
            fieldButton.setY(field.getSpawnPoint().getY());
            fieldButton.setLayoutParams(buttonSizeParams);
            fieldButton.setTransitionName(field.getId());
            if (field.getFieldDescription().equals(FieldDescription.PATH)) {
                fieldButton.setEnabled(false);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                gameActivity.getMapFrameLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if(field.getFieldDescription().equals(FieldDescription.SPAWN)) {
                fieldButton.setOnClickListener(spawnFieldListener);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                gameActivity.getMapFrameLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if (field.getFieldDescription().equals(FieldDescription.FREE)) {
                fieldButton.setOnClickListener(listener);
                fieldButton.setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                gameActivity.getMapFrameLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            }
        });
    }
}