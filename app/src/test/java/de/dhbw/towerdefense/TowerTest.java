package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.EnemyView;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.structure.Field;
import de.dhbw.util.Position;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TowerTest {

    @Mock
    private ImageView imageDummy = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);
    private EnemyView enemyView = mock(EnemyView.class);

    @Test
    public void towerRecognizesEnemyIfItsInRange() {
        //arrange

        ATower tower = new TowerArtillery(new Field(), 1, imageDummy, dummyGameActivity);
        AEnemy enemy = new Tank("tank", 1, dummyGameActivity, enemyView);
        enemy.moveToPosition(new Position(50, 50));

        //act
        AEnemy recognizedEnemy = tower.getNearestEnemy(tower.getPosition(), Collections.singletonList(enemy));

        //assert
        assertEquals(enemy, recognizedEnemy);
    }

    @Test
    public void towerDontShootEnemyIfItsNotInRange() {
        //arrange
        ATower tower = new TowerArtillery(new Field(), 1, imageDummy, dummyGameActivity);
        AEnemy enemy = new Tank("tank", 1, dummyGameActivity, enemyView);
        enemy.moveToPosition(new Position(150, 150));

        //act
        AEnemy recognizedEnemy = tower.getNearestEnemy(tower.getPosition(), Collections.singletonList(enemy));

        //assert
        assertEquals(enemy, recognizedEnemy);
    }
}
