package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.Car;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.CAR_LEVEL_1_HEALTHPOINTS;
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
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void ifDamageToEnemiesIsLowerThanHP_EnemiesShouldBeAlive() {
        //arrange
        AEnemy enemyTank = new Tank("tank1",1, dummyImage, dummyGameActivity);
        AEnemy enemyCar = new Car("car1", 1, dummyImage, dummyGameActivity);

        //act
        enemyTank.reduceHealthPoints(TANK_LEVEL_1_HEALTHPOINTS-1);
        enemyCar.reduceHealthPoints(CAR_LEVEL_1_HEALTHPOINTS-1);

        //assert
        assertTrue(enemyTank.isAlive());
        assertTrue(enemyCar.isAlive());
    }

    @Test
    public void ifDamageToEnemiesHigherThanHP_EnemiesShouldBeDead() {
        //arrange
        AEnemy enemyTank = new Tank("tank1", 1, dummyImage, dummyGameActivity);
        AEnemy enemyCar = new Car("car1", 1, dummyImage, dummyGameActivity);

        //act
        enemyTank.reduceHealthPoints(TANK_LEVEL_1_HEALTHPOINTS+1);
        enemyCar.reduceHealthPoints(CAR_LEVEL_1_HEALTHPOINTS+1);

        //assert
        assertFalse(enemyTank.isAlive());
        assertFalse(enemyCar.isAlive());
    }

    @Test
    public void whenEnemiesMove_ThePositionsShouldChange() {
        //arrange
        AEnemy enemyTank = new Tank("tank", 1, dummyImage, dummyGameActivity);
        AEnemy enemyCar = new Car("car1", 1, dummyImage, dummyGameActivity);

        MapStructure map = new MapStructure();

        //act
        enemyTank.move(map);
        Position oldPosTank = enemyTank.getPosition();
        enemyTank.move(map);

        enemyCar.move(map);
        Position oldPosCar = enemyCar.getPosition();
        enemyCar.move(map);

        //assert
        assertThat(oldPosTank, is(not(equalTo(enemyTank.getPosition()))));
        assertThat(oldPosCar, is(not(equalTo(enemyCar.getPosition()))));
    }
}
