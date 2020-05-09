package de.dhbw.towerdefense;


import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.util.Position;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TowerRotationTest {

    @Mock
    ImageView dummyImage = mock(ImageView.class);

    @Test
    public void checkRotationIsOnCorrectAngle() {
        //arrange
        List<Enemy> enemy = new ArrayList<>();
        enemy.add(new Tank("tank1",1, dummyImage));
        ATower tower = new TowerArtillery("t1", new Position(2, 3), 1, dummyImage);

        //act
        enemy.get(0).moveTo(3,3);
        double rotation = tower.rotateImage(enemy);
        enemy.get(0).moveTo(1,3);
        double rotation2 = tower.rotateImage(enemy);
        enemy.get(0).moveTo(2,4);
        double rotation3 = tower.rotateImage(enemy);

        //assert
        assertTrue(rotation == 180.0);
        assertTrue(rotation2 == 0.0);
        assertTrue(rotation3 == -90.0);
    }
}
