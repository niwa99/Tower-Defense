package de.dhbw.game;

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

import de.dhbw.ImageElevation;
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
import de.dhbw.map.objects.tower.TowerLaser;
import de.dhbw.map.objects.tower.TowerBoombastic;
import de.dhbw.map.objects.tower.TowerFreezer;
import de.dhbw.map.objects.tower.TowerPlasmarizer;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;
import de.dhbw.util.PreferenceManager;

import static de.dhbw.util.Constants.FIELD_SIZE;
import static de.dhbw.util.Constants.STATUS_OFF;
import static de.dhbw.util.Constants.STATUS_ON;
import static de.dhbw.util.Constants.startGameDelay;

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

    private boolean showCircle = false;
    private Position circleField = new Position(-1, -1);

    private boolean lastWaveOut = false;
    private boolean lastEnemyOfWaveSpawned = false;
    private IMoneyListener moneyListener = null;
    private boolean isMenuOpen = false;
    private int lifePoints = 100;
    private int money = 0;

    private int currentWaveNumber = 0;
    private int numberOfEnemiesKilled = 0;
    private int numberOfBuiltTowers = 0;
    private int numberOfUpgrades = 0;
    private int moneySpent = 0;

    private Button spawnButton;

    private MusicPlayer musicPLayer;

	public Game(GameActivity gameActivity) {
	    this.gameActivity = gameActivity;
	    mapStructure = new MapStructure();
        matchField = new MatchField(gameActivity);
        countDownTimer = new StatusBarCountDownTimer(gameActivity);
        generateButtonsOnMap();
        loadSettings();
        musicPLayer = new MusicPlayer(gameSettings.get(Settings.MUSIC), gameActivity);
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
	    musicPLayer.stop();
	    if(waveTimer!=null){
            waveTimer.cancel();
        }
        if(gameTimer!=null){
            gameTimer.cancel();
        }
        countDownTimer.stopTimer();
        if (!isRegularStop) {
            matchField.stopTimer();
        }
    }

    public void openSettings(){
	    if(!isMenuOpen){
            isMenuOpen = true;
            Intent intent = new Intent(gameActivity, MenuSettings.class);
            MenuSettings.gameActivity = gameActivity;
            matchField.pauseTimers();
            pauseTimers();
            gameActivity.startActivity(intent);
        }
    }

    public void closeMenu(){
	    this.isMenuOpen=false;
    }

    public void pauseTimers(){
        final long time = System.currentTimeMillis();
        match.calculateDelay(time);
        countDownTimer.stopTimer();
        waveTimer.cancel();
        waveTimer = null;
        gameTimer.cancel();
        gameTimer = null;
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
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            gameTimer = new Timer();
            gameTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Optional<AWave> wave = match.next();
                    if (wave.isPresent()) {
                        if(match.getCurrentWaveNumber()==1){
                            startWave(wave.get(), startGameDelay);
                        }else{
                            startWave(wave.get(), match.getWaveTime());
                        }
                    }
                }
            }, delay);
        }else{
            lastWaveOut = true;
        }
    }

    private void startWave(AWave wave, int seconds) {
        //timer
        if (waveTimer != null) {
	        waveTimer.cancel();
        }
	    waveTimer = new Timer();
        countDownTimer.timer(seconds);

        //status
        currentWaveNumber = match.getCurrentWaveNumber()-1;
        updateStatusBar();

        //pause actions
        match.setLastTimeActionMillis(System.currentTimeMillis());
        match.setDelay(seconds*1000);

        //next wave
        lastEnemyOfWaveSpawned = false;
        triggerSpawnButtonImageChange();
        startNextWave(seconds*1000);

        waveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (wave.hasNext()) {
                    matchField.addEnemy(wave.next());
                } else {
                    lastEnemyOfWaveSpawned = true;
                    triggerSpawnButtonImageChange();
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
                    TowerFreezer newFreezer = new TowerFreezer(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newFreezer);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
                case BOOMBASTIC:
                    TowerBoombastic newBoombastic = new TowerBoombastic(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newBoombastic);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
                case PLASMARIZER:
                    TowerPlasmarizer newPlasmarizer = new TowerPlasmarizer(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newPlasmarizer);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
                case ASSAULTLASER:
                    TowerLaser newAssaultLaser = new TowerLaser(getMapStructure().getField(pos), level, gameActivity);
                    getMatchField().addTower(newAssaultLaser);
                    getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
                    break;
            }
        }
    }

    public void sellTower(Position pos) {
        Field field = mapStructure.getField(pos);
        Optional<ATower> tower = matchField.getTower(field);
        if (tower.isPresent()) {
            ATower currrentTower = tower.get();
            if(currrentTower instanceof TowerLaser){
                ((TowerLaser) currrentTower).removeBullet();
            }
            addMoney((int) Math.round(currrentTower.getCosts() * 0.5));
            matchField.removeTower(currrentTower);
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
	    builder.setCancelable(false);
	    gameActivity.showBuilder(builder);
    }

    public void setMenu(IMoneyListener listener) {
	    moneyListener = listener;
    }

    public void increaseNumberOfEnemiesKilled() {
	    numberOfEnemiesKilled++;
    }

    public void increaseNumberOfBuiltTowers() {
	    numberOfBuiltTowers++;
    }

    public void increaseMoneySpent(int increase) {
	    moneySpent += increase;
    }

    public void toggleMusic(boolean on){
	    gameSettings.remove(Settings.MUSIC);
	    gameSettings.put(Settings.MUSIC, on);
	    musicPLayer.toggle(on, gameActivity);
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
	    Difficulty matchDifficulty = match.getDifficulty();
        if (currentWaveNumber >= Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.MAX_WAVE)))) {
            if (numberOfEnemiesKilled > Integer.parseInt(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.ENEMIES_KILLED)))) {
                PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.MAX_WAVE), String.valueOf(currentWaveNumber));
                PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.ENEMIES_KILLED), String.valueOf(numberOfEnemiesKilled));
                PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.BUILT_TOWERS), String.valueOf(numberOfBuiltTowers));
                PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.UPGRADES), String.valueOf(numberOfUpgrades));
                PreferenceManager.setStatisticsValue(PreferenceManager.Statistics.getStatisticsStringByDifficulty(matchDifficulty, PreferenceManager.Statistics.MONEY_SPENT), String.valueOf(moneySpent));
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
                    if (showCircle) {
                        ViewGroup vg = (ViewGroup)(gameActivity.findViewById(R.id.circle).getParent());
                        vg.removeView(gameActivity.findViewById(R.id.circle));
                        circleField = new Position(-1, -1);
                        showCircle = false;
                    } else {
                        if (clickedButton.isPresent()) {
                            if (clickedButton.get().getTransitionName().equals(button.get().getTransitionName())) {
                                clickedButton = Optional.empty();
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
                    }
                } else if (field.getFieldDescription() == FieldDescription.TOWER) {
                    Optional<ATower> tower = matchField.getTower(field);
                    if (tower.isPresent()) {
                        if (field.getFieldPositionX() != circleField.getX() || field.getFieldPositionY() != circleField.getY()) {
                            try {
                                if(clickedButton.isPresent()){
                                    clickedButton.get().setBackground(gameActivity.getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                                    clickedButton = Optional.empty();
                                }
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

        LinearLayout.LayoutParams buttonSizeParams = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);
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
                fieldButton.setForeground(gameActivity.getResources().getDrawable(R.drawable.arrow_spawn_button, null));
                spawnButton = fieldButton;
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

    private void triggerSpawnButtonImageChange(){
	    if(lastEnemyOfWaveSpawned && !lastWaveOut){
	        gameActivity.setForeGround(spawnButton, R.drawable.arrow_spawn_button);
        }else{
            gameActivity.setForeGround(spawnButton, R.drawable.transparent_background);
        }
    }

   public void setCircle(ATower tower, Field field) {
        ImageView image = new ImageView(gameActivity);
        image.setId(R.id.circle);
        image.setImageResource(R.drawable.circle);
        image.setX(field.getSpawnPoint().getX() + field.getSizeInPx()/2 - tower.getRange());
        image.setY(field.getSpawnPoint().getY() + field.getSizeInPx()/2 - tower.getRange());
        image.setLayoutParams(new LinearLayout.LayoutParams(tower.getRange()*2, tower.getRange()*2));
        image.setElevation(ImageElevation.RANGE_INDICATOR.elevation);
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
            numberOfUpgrades++;

            return new int[]{tower.get().getDamage(level+1), tower.get().getRange(level+1), tower.get().getFireRate(level+1), tower.get().getCosts(level+1)};
        }
        return null;
    }
}