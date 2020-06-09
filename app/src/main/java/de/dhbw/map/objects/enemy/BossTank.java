package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.BOSS_TANK_LEVEL_1_MULTIPLIKATOR;
import static de.dhbw.util.Constants.BOSS_TANK_LEVEL_2_MULTIPLIKATOR;
import static de.dhbw.util.Constants.BOSS_TANK_LEVEL_3_MULTIPLIKATOR;
import static de.dhbw.util.Constants.CAR_LEVEL_1_HEALTHPOINTS;
import static de.dhbw.util.Constants.DRAWABLE_TANK;
import static de.dhbw.util.Constants.DRAWABLE_TANK_BOSS;
import static de.dhbw.util.Constants.DRAWABLE_TANK_BOSS_HITTED;
import static de.dhbw.util.Constants.DRAWABLE_TANK_HITTED;
import static de.dhbw.util.Constants.TANK_LEVEL_1_HEALTHPOINTS;
import static de.dhbw.util.Constants.TANK_LEVEL_1_VALUE;
import static de.dhbw.util.Constants.TANK_LEVEL_2_HEALTHPOINTS;
import static de.dhbw.util.Constants.TANK_LEVEL_2_VALUE;
import static de.dhbw.util.Constants.TANK_LEVEL_3_HEALTHPOINTS;
import static de.dhbw.util.Constants.TANK_LEVEL_3_VALUE;

public class BossTank extends AEnemy {
    private int level;
    private Car car;
    private Timer timer = new Timer();

    public BossTank(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getBossTankHealthpointsByLevel(level), Tank.getTankSpeedByLevel(level), getBossTankValueByLevel(level), Tank.getTankLifePointsCostsByLevel(level)*2, gameActivity , EnemyType.BOSS_TANK, DRAWABLE_TANK_BOSS , DRAWABLE_TANK_BOSS_HITTED);
        this.level=level;
        car = new Car(EnemyType.CAR.getLabel(), 1, gameActivity);
        car.moveToPosition(new Position(-1000, -1000));
    }

    public int getLevel(){
        return level;
    }

    public Car getCar(){
        return car;
    }

    public static int getBossTankHealthpointsByLevel(int level) {
        int multiplikator = getBossTankMultiplikator(level);
        switch (level) {
            case 1: return TANK_LEVEL_1_HEALTHPOINTS*multiplikator;
            case 2: return TANK_LEVEL_2_HEALTHPOINTS*multiplikator;
            case 3: return TANK_LEVEL_3_HEALTHPOINTS*multiplikator;
            default: return TANK_LEVEL_1_HEALTHPOINTS*multiplikator;
        }
    }

    private static int getBossTankValueByLevel(int level) {
        int multiplikator = getBossTankMultiplikator(level);
        switch (level) {
            case 1: return TANK_LEVEL_1_VALUE*multiplikator;
            case 2: return TANK_LEVEL_2_VALUE*multiplikator;
            case 3: return TANK_LEVEL_3_VALUE*multiplikator;
            default: return TANK_LEVEL_1_VALUE*multiplikator;
        }
    }

    private static int getBossTankMultiplikator(int level){
        switch (level) {
            case 1: return BOSS_TANK_LEVEL_1_MULTIPLIKATOR;
            case 2: return BOSS_TANK_LEVEL_2_MULTIPLIKATOR;
            case 3: return BOSS_TANK_LEVEL_3_MULTIPLIKATOR;
            default: return BOSS_TANK_LEVEL_1_MULTIPLIKATOR;
        }
    }
}
