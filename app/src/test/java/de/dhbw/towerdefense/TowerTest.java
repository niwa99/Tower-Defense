package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.util.Position;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TowerTest {

    @Mock
    ImageView imageDummy = mock(ImageView.class);

    @Test
    public void towerKillsEnemyIfItsInRange() {
        //arrange
        Tower tower = new DefTower("t1", new Position(0, 0), 1);
        Enemy enemy = new Tank("tank", 1);
        enemy.moveToPosition(new Position(50, 50));

        //act
        tower.fire(Arrays.asList(enemy));

        //assert
        assertFalse(enemy.isAlive());
    }

    @Test
    public void towerDontShootEnemyIfItsNotInRange() {
        //arrange
        Tower tower = new DefTower("t1", new Position(0, 0), 1);
        Enemy enemy = new Tank("tank", 1);
        enemy.moveToPosition(new Position(150, 150));

        //act
        tower.fire(Arrays.asList(enemy));

        //assert
        assertTrue(enemy.isAlive());
    }
}
