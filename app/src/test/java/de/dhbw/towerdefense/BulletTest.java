package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.Projectile;
import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BulletTest {

    @Mock
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Test
    public void shootBulletFromSpawnPositionToTargetPosition() throws InterruptedException {
        //arrange
        final int field_size = 150;
        ATower tower = new TowerArtillery(new Field(field_size, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
        Enemy enemy = new Tank("tank1",1, dummyImage, dummyGameActivity);
        enemy.moveTo(field_size * 5, field_size * 1);
        enemy.setHealthpoints(1);

        //act
        new Projectile(tower.getPosition(), enemy, tower.getDamage(), dummyImage, dummyGameActivity);

        Thread.sleep(2000);

        //assert
        assertNotEquals(enemy.getHealthPoints(), 1);
    }

}
