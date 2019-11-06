package de.dhbw.towerdefense;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Position;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EnemyTest {

    @Test
    void ifDamageToEnemyIsLowerThanHP_EnemyShouldBeAlive() {
        //arrange
        Enemy enemy = new Tank(null,"tank1", 100, 900);

        //act
        enemy.reduceHealthPoints(50);

        //assert
        assertTrue(enemy.isAlive());
    }

    @Test
    public void ifDamageToEnemyHigherThanHP_EnemyShouldBeDead() {
        //arrange
        Enemy enemy = new Tank(null,"tank1", 100, 900);

        //act
        enemy.reduceHealthPoints(150);

        //assert
        assertFalse(enemy.isAlive());
    }

    @Test
    public void whenEnemyMoves_ThePositionShouldChange() {
        Enemy e = new Tank(null,"tank", 5, 950);
        MapStructure map = new MapStructure();

        //act
        e.move(map);
        Position oldPos = e.getPosition();
        e.move(map);

        //assert
        assertThat(oldPos, is(not(equalTo(e.getPosition()))));
    }
}
