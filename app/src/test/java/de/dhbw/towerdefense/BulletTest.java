package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.Bullet;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BulletTest {


    @Mock
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void shootBulletFromSpawnPositionToTargetPosition() {
        //arrange
        final int field_size = 150;
        DefTower tower = new DefTower("t1", new Field(field_size, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
        Enemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(field_size * 5, field_size * 1);

        //act
        Bullet bullet = new Bullet(tower.getPosition(), enemy, tower, dummyImage, dummyGameActivity);

        boolean targetReached = false;
        int nMoves = 0;
        while (nMoves < 1000) {
            targetReached = !bullet.move();
            if (targetReached) {
                break;
            }
            nMoves++;
        }

        //assert
        assertTrue(targetReached);
    }

}
