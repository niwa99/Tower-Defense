package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class EnemyTest {
    @Mock
    ImageView imageDummy = mock(ImageView.class);

    @Test
    public void ifDamageToEnemyIsLowerThanHP_EnemyShouldBeAlive() {
        //arrange
        Enemy enemy = new Tank(imageDummy,"tank1", 100, 900);

        //act
        enemy.reduceHealthPoints(50);

        //assert
        assertTrue(enemy.isAlive());
    }

    @Test
    public void ifDamageToEnemyHigherThanHP_EnemyShouldBeDead() {
        //arrange
        Enemy enemy = new Tank(imageDummy,"tank1", 100, 900);

        //act
        enemy.reduceHealthPoints(150);

        //assert
        assertFalse(enemy.isAlive());
    }

    @Test
    public void whenEnemyMoves_ThePositionShouldChange() {
        Enemy e = new Tank(imageDummy,"tank", 5, 950);
        MapStructure map = new MapStructure();

        //act
        e.move(map);
        Position oldPos = e.getPosition();
        e.move(map);

        //assert
        assertThat(oldPos, is(not(equalTo(e.getPosition()))));
    }
}
