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
    public void detectIfEnemyIsHitWhenEnemyMovesFromLeftToRight_SHOULD_BE_FalseWhenEnemyXGreater226() throws InterruptedException {
        //arrange
        final int field_size = 150;
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(field_size,field_size);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionX(); i <226 ; i++) {
            enemy.moveTo(i,field_size);
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(field_size, 500), new Position(field_size, field_size), enemy));
        }

        enemy.moveTo(226, field_size);
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(field_size, 500), new Position(field_size, field_size), enemy));
    }

    @Test
    public void detectIfEnemyIsHitWhenEnemyMovesThroughLaserStartPosition_SHOULD_AlwaysBeTrue() throws InterruptedException {
        //arrange
        final int field_size = 150;
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(field_size,field_size);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i <500 ; i++) {
            enemy.moveTo(field_size, i );
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(field_size, 500), new Position(field_size, field_size), enemy));
        }
    }

    @Test
    public void detectIfEnemyIsHitWhenEnemyMovesOppositeDirectionOfLaserStartPosition_SHOULD_BE_TrueUntilOutOfRange() throws InterruptedException {
        //arrange
        final int field_size = 150;
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(field_size,200);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i <250 ; i++) {
            enemy.moveTo(field_size, i );
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(field_size, 0), new Position(field_size, 250), enemy));
        }
        enemy.moveTo(field_size, 500);
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(field_size, 0), new Position(field_size, 250), enemy));
    }

    @Test
    public void test2() throws InterruptedException {
        //arrange
        final int field_size = 150;
        AEnemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(300,field_size);
        enemy.setHealthpoints(10000);

        //act
        for (int i = enemy.getPositionY(); i <225 ; i++) {
            enemy.moveTo(enemy.getPositionX(), i );
            assertTrue(LaserRay.isEnemyHitOnPosition(new Position(0, field_size*2), new Position(350, field_size), enemy));
        }
        enemy.moveTo(field_size, 300);
        assertFalse(LaserRay.isEnemyHitOnPosition(new Position(0,field_size*2), new Position(350, field_size), enemy));
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
