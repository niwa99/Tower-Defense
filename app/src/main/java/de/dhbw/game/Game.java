package de.dhbw.game;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
import de.dhbw.game.popups.MenuUpgradeAndSell;
import de.dhbw.game.settings.Settings;
import de.dhbw.game.wave.AWave;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.objects.tower.TowerBoombastic;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;

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

    private Map<Settings, Boolean> gameSettings = new HashMap<Settings, Boolean>();
    private boolean isSoundOn = false;
    private boolean isAnimationOn = false;
    private boolean isMusicOn = false;

    private boolean showCircle = false;
    private Position circleField = new Position(-1, -1);

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
        loadSettings();
	}

	private void loadSettings(){
        for (Settings setting: Settings.values()) {
            String settings = PreferenceManager.getSettingsValue(setting);
            if (settings == null) {
                PreferenceManager.setSettingsValue(setting, STATUS_ON);
                gameSettings.put(setting, true);
            }else if(settings.equals(STATUS_ON)){
                gameSettings.put(setting, true);
            } else if(settings.equals(STATUS_OFF)) {
                gameSettings.put(setting, false);
            }
        }
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

    public void buildTower(TowerType type, int level, Position pos) {
        if (subMoney(type.getPrice()) && getMapStructure().getField(pos).getFieldDescription() == FieldDescription.FREE) {
            switch(type) {
                case ARTILLERY:
                    TowerArtillery newArtillery = new TowerArtillery(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newArtillery);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
                case FREEZER:
                case BOOMBASTIC:
                    TowerBoombastic newBoombastic = new TowerBoombastic(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newBoombastic);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;

                case PLASMARIZER:
                case ASSAULTLASER:
                    break;
            }
        }
    }

    public void sellTower(Position pos) {
        Field field = mapStructure.getField(pos);
        Optional<ATower> tower = matchField.getTower(field);
        if (tower.isPresent()) {
            addMoney((int) Math.round(tower.get().getCosts() * 0.5));
            matchField.removeTower(tower.get());
            mapStructure.getField(field.getFieldPosition()).setFieldDescription(FieldDescription.FREE);

        }
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

    public void setIngameSound(boolean on){
        gameSettings.remove(Settings.INGAME_SOUND);
        gameSettings.put(Settings.INGAME_SOUND, on);
    }

    public boolean isSoundOn(){
	    return gameSettings.get(Settings.INGAME_SOUND);
    }

    public void setAnimationOn(boolean on){
	    gameSettings.remove(Settings.ANIMATIONS);
	    gameSettings.put(Settings.ANIMATIONS, on);
    }

    public boolean isAnimationOn(){
	    return gameSettings.get(Settings.ANIMATIONS);
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
                    if (showCircle && circleField != null) {
                        ViewGroup vg = (ViewGroup)(gameActivity.findViewById(R.id.circle).getParent());
                        vg.removeView(gameActivity.findViewById(R.id.circle));
                        circleField = new Position(-1, -1);
                        showCircle = false;
                    }
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
                    Optional<ATower> tower = matchField.getTower(field);
                    if (tower.isPresent()) {
                        if (field.getFieldPositionX() != circleField.getX() || field.getFieldPositionY() != circleField.getY()) {
                            try {
                                clickedButton.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            } catch (NoSuchElementException e) {
                                e.printStackTrace();
                            }

                            if (showCircle) {
                                ViewGroup vg = (ViewGroup)(gameActivity.findViewById(R.id.circle).getParent());
                                vg.removeView(gameActivity.findViewById(R.id.circle));
                            }
                            circleField = field.getFieldPosition();
                            setCircle(tower.get(), field);
                            showCircle = true;
                        } else {
                            try {
                                ViewGroup vg = (ViewGroup)(gameActivity.findViewById(R.id.circle).getParent());
                                vg.removeView(gameActivity.findViewById(R.id.circle));
                                circleField = new Position(-1, -1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            openTowerPopup(tower.get(), field);
                            showCircle = false;
                        }
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

   public void setCircle(ATower tower, Field field) {
        ImageView image = new ImageView(gameActivity);
        image.setId(R.id.circle);
        image.setImageResource(R.drawable.circle);
        image.setX(field.getSpawnPoint().getX() + field.getSizeInPx()/2 - tower.getRange());
        image.setY(field.getSpawnPoint().getY() + field.getSizeInPx()/2 - tower.getRange());
        image.setLayoutParams(new LinearLayout.LayoutParams(tower.getRange()*2, tower.getRange()*2));
        gameActivity.getMapFrameLayout().addView(image);
    }

    public void openTowerPopup(ATower tower, Field field) {
        Intent intent = new Intent(gameActivity, MenuUpgradeAndSell.class);
        intent.putExtra(gameActivity.getString(R.string.position), field.getFieldPosition());
        intent.putExtra(gameActivity.getString(R.string.towerDamage), tower.getDamage());
        intent.putExtra(gameActivity.getString(R.string.towerRange), tower.getRange());
        intent.putExtra(gameActivity.getString(R.string.towerFireRate), tower.getFireRate());
        intent.putExtra(gameActivity.getString(R.string.towerCost), tower.getCosts());
        intent.putExtra(gameActivity.getString(R.string.towerLevel), tower.getLevel());
        intent.putExtra(gameActivity.getString(R.string.towerDrawable), tower.getTowerType().getDrawable());
        intent.putExtra(gameActivity.getString(R.string.towerType), tower.getTowerType().getType());
        intent.putExtra(gameActivity.getString(R.string.towerUpgrCost), tower.getCosts(tower.getLevel() + 1));
        intent.putExtra(gameActivity.getString(R.string.towerUpgrDamage), tower.getDamage(tower.getLevel() + 1));
        intent.putExtra(gameActivity.getString(R.string.towerUpgrRange), tower.getRange(tower.getLevel() + 1));
        intent.putExtra(gameActivity.getString(R.string.towerUpgrFireRate), tower.getFireRate(tower.getLevel() + 1));
        intent.putExtra(gameActivity.getString(R.string.towerUpgrCost), tower.getCosts(tower.getLevel() + 1));
        MenuUpgradeAndSell.game = this;
        gameActivity.startActivity(intent);
    }

    public int[] upgradeTower(Position pos, int level) {
        Field field = mapStructure.getField(pos);
        Optional<ATower> tower = matchField.getTower(field);
        if (tower.isPresent()) {
            TowerType type = tower.get().getTowerType();
            matchField.removeTower(tower.get());
            mapStructure.getField(field.getFieldPosition()).setFieldDescription(FieldDescription.FREE);
            setMoney(getMoney()+tower.get().getCosts(1));
            buildTower(type, level, pos);
            return new int[]{tower.get().getDamage(level+1), tower.get().getRange(level+1), tower.get().getFireRate(level+1), tower.get().getCosts(level+1)};
        }
        return null;
    }
}