package de.dhbw.towerdefense;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.util.Position;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class TowerTest {

    @Test
    void towerKillsEnemyIfItsInRange() {
        //arrange
        Tower t = new DefTower(null,"t1", 100, 100, 1, new Position(0, 0));
        Enemy e = new Tank(null,"tank", 5, 950);
        e.moveToPosition(new Position(50, 50));

        //act
        t.fire(Arrays.asList(e));

        //assert
        assertFalse(e.isAlive());
    }

    @Test
    void towerDontShootEnemyIfItsNotInRange() {
        //arrange
        Tower t = new DefTower(null,"t1", 100, 100, 1, new Position(0, 0));
        Enemy e = new Tank(null,"tank", 5, 950);
        e.moveToPosition(new Position(150, 150));

        //act
        t.fire(Arrays.asList(e));

        //assert
        assertTrue(e.isAlive());
    }
}
