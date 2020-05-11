package de.dhbw.towerdefense;


import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
<<<<<<< HEAD
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.util.Position;
=======
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;
>>>>>>> master

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TowerRotationTest {

    @Mock
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void checkRotationIsOnCorrectAngle() {
        //arrange
        List<Enemy> enemy = new ArrayList<>();
<<<<<<< HEAD
        enemy.add(new Tank("tank1",1, dummyImage));
        ATower tower = new TowerArtillery("t1", new Position(2, 3), 1, dummyImage);
=======
        enemy.add(new Tank("tank1",1, dummyImage, dummyGameActivity));
        Tower tower = new DefTower("t1", new Field(1, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
>>>>>>> master

        //act
        enemy.get(0).moveTo(3,3);
        double rotation = tower.rotateImage(enemy);
        enemy.get(0).moveTo(1,3);
        double rotation2 = tower.rotateImage(enemy);
        enemy.get(0).moveTo(2,4);
        double rotation3 = tower.rotateImage(enemy);

        //assert
        assertEquals(180.0, rotation, 0.0);
        assertEquals(0.0, rotation2, 0.0);
        assertEquals(rotation3, -90.0, 0.0);
    }
}
