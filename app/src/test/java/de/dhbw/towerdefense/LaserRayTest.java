package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.mockito.Mock;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.LaserRay;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.FIELD_SIZE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class LaserRayTest {
    @Mock
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void detectIfEnemyIsHitWhenEnemyMovesFromLeftToRight_SHOULD_BE_FalseWhenEnemyXGreater151() throws InterruptedException {
        //arrange
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(FIELD_SIZE/2, FIELD_SIZE/2);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionX(); i <151 ; i++) {
            enemy.moveTo(i, FIELD_SIZE);
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(FIELD_SIZE, 500), new Position(FIELD_SIZE, FIELD_SIZE), enemy));
        }

        enemy.moveTo(226, FIELD_SIZE);

        //assert
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(FIELD_SIZE, 500), new Position(FIELD_SIZE, FIELD_SIZE), enemy));
    }

    @Test
    public void detectIfEnemyIsHitWhenEnemyMovesThroughLaserStartPosition_SHOULD_AlwaysBeTrue() throws InterruptedException {
        //arrange
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(FIELD_SIZE, FIELD_SIZE);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i <500 ; i++) {
            enemy.moveTo(FIELD_SIZE, i);
            //assert
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(FIELD_SIZE, 500), new Position(FIELD_SIZE, FIELD_SIZE), enemy));
        }
    }

    @Test
    public void detectIfEnemyIsHitWhenEnemyMovesOppositeDirectionOfLaserStartPosition_SHOULD_BE_TrueUntilOutOfRange() throws InterruptedException {
        //arrange
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(FIELD_SIZE,200);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i <250 ; i++) {
            enemy.moveTo(FIELD_SIZE, i);
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(FIELD_SIZE, 0), new Position(FIELD_SIZE, 250), enemy));
        }
        enemy.moveTo(FIELD_SIZE, 500);

        //assert
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(FIELD_SIZE, 0), new Position(FIELD_SIZE, 250), enemy));
    }

    @Test
    public void test2() throws InterruptedException {
        //arrange
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(FIELD_SIZE*2, FIELD_SIZE);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i < 172 ; i++) {
            enemy.moveTo(enemy.getPositionX(), i);
            System.out.println(i + " " + LaserRay.isEnemyHitOnPosition(new Position(0, FIELD_SIZE*2), new Position(350, FIELD_SIZE), enemy));
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(0, FIELD_SIZE*2), new Position(350, FIELD_SIZE), enemy));
        }
        enemy.moveTo(FIELD_SIZE, 300);

        //assert
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(0,FIELD_SIZE*2), new Position(350, FIELD_SIZE), enemy));
    }

    @Test
    public void test(){
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(626,229);
        enemy.setHealthpoints(10000);
        System.out.println(LaserRay.isEnemyHitOnPosition(new Position(657,150),new Position(626,229),enemy));
        enemy.moveTo(600,77);
        System.out.println(LaserRay.isEnemyHitOnPosition(new Position(657,150),new Position(626,229),enemy));
    }
}
