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
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.util.Position;

import static de.dhbw.util.ObjectStorage.getMapStructure;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TowerRotationTest {

    @Mock
    ImageView imageDummy = mock(ImageView.class);

    @Test
    public void checkRotationIsOnCorrectAngle() {
        //arrange
        List<Enemy> enemy = new ArrayList<>();
        enemy.add(new Tank("tank1",1));
        DefTower tower = new DefTower("tower1", getMapStructure().getField(new Position(2, 3)).getSpawnPoint(), 1);

        //act
        enemy.get(0).moveTo(3,3);
        double rotation = tower.rotateImage(enemy);
        enemy.get(0).moveTo(1,3);
        double rotation2 = tower.rotateImage(enemy);
        enemy.get(1).moveTo(2,4);
        double rotation3 = tower.rotateImage(enemy);

        //assert
        assertTrue(rotation == 270 && rotation2 == 90 && rotation3 == 180);
    }
}
