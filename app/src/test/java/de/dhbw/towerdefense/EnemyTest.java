package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.TANK_LEVEL_1_HEALTHPOINTS;
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
    private ImageView imageDummy = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void ifDamageToEnemyIsLowerThanHP_EnemyShouldBeAlive() {
        //arrange
        AEnemy enemy = new Tank("tank1",1, imageDummy, dummyGameActivity);

        //act
        enemy.reduceHealthPoints(TANK_LEVEL_1_HEALTHPOINTS-1);

        //assert
        assertTrue(enemy.isAlive());
    }

    @Test
    public void ifDamageToEnemyHigherThanHP_EnemyShouldBeDead() {
        //arrange
        AEnemy enemy = new Tank("tank1", 1, imageDummy, dummyGameActivity);

        //act
        enemy.reduceHealthPoints(TANK_LEVEL_1_HEALTHPOINTS+1);

        //assert
        assertFalse(enemy.isAlive());
    }

    @Test
    public void whenEnemyMoves_ThePositionShouldChange() {
        AEnemy enemy = new Tank("tank", 1, imageDummy, dummyGameActivity);
        MapStructure map = new MapStructure();

        //act
        enemy.move(map);
        Position oldPos = enemy.getPosition();
        enemy.move(map);

        //assert
        assertThat(oldPos, is(not(equalTo(enemy.getPosition()))));
    }
}
