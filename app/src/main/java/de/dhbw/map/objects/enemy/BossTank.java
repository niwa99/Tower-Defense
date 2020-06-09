package de.dhbw.map.objects.enemy;

import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.DRAWABLE_TANK_BOSS;
import static de.dhbw.util.Constants.DRAWABLE_TANK_BOSS_HITTED;
import static de.dhbw.util.Constants.TANK_HEALTHPOINTS;
import static de.dhbw.util.Constants.TANK_VALUE;

public class BossTank extends AEnemy {
    private int level;
    private Car car;

    public BossTank(String label, int level, GameActivity gameActivity) {
        super(label, UUID.randomUUID(), getBossTankHealthpointsByLevel(level), Tank.getTankSpeedByLevel(level), getBossTankValueByLevel(level), Tank.getTankLifePointsCostsByLevel(level)*2, gameActivity , EnemyType.BOSS_TANK, DRAWABLE_TANK_BOSS , DRAWABLE_TANK_BOSS_HITTED);
        this.level=level;
        car = new Car(EnemyType.CAR.getLabel(), 1, gameActivity);
        car.moveToPosition(new Position(-1000, -1000));
    }

    public BossTank(String label, int level, GameActivity gameActivity, EnemyView enemyView) {
        super(label, UUID.randomUUID(), getBossTankHealthpointsByLevel(level), Tank.getTankSpeedByLevel(level), getBossTankValueByLevel(level), Tank.getTankLifePointsCostsByLevel(level)*2, gameActivity , EnemyType.BOSS_TANK, enemyView);
        this.level=level;
    }

    public int getLevel(){
        return level;
    }

    public Car getCar(){
        return car;
    }

    public static int getBossTankHealthpointsByLevel(int level) {

        return TANK_HEALTHPOINTS * level * 5;
    }

    private static int getBossTankValueByLevel(int level) {
        return TANK_VALUE * level * 5;
    }
}
