package de.dhbw.towerdefense;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.EnemyType;
import de.dhbw.map.objects.bullet.Bomb;
import de.dhbw.map.objects.bullet.Projectile;
import de.dhbw.map.objects.bullet.SnowFlake;
import de.dhbw.map.objects.enemy.AEnemy;
import de.dhbw.map.objects.enemy.EnemyView;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.ATower;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.map.objects.tower.TowerBoombastic;
import de.dhbw.map.objects.tower.TowerFreezer;
import de.dhbw.map.structure.Field;
import de.dhbw.map.structure.FieldDescription;

import static de.dhbw.util.Constants.DRAWABLE_TANK;
import static de.dhbw.util.Constants.DRAWABLE_TANK_HITTED;
import static de.dhbw.util.Constants.FIELD_SIZE;
import static de.dhbw.util.Constants.TANK_HEALTHPOINTS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BulletTest {

    @Mock
    private ImageView dummyImage = mock(ImageView.class);
    private GameActivity dummyGameActivity = mock(GameActivity.class);

    @Mock
    private EnemyView enemyView = mock(EnemyView.class);

    @Test
    public void shootProjectileFromSpawnPositionToEnemy_SHOULD_decreaseEnemyHealthpoints() throws InterruptedException {
        //arrange
        ATower tower = new TowerArtillery(new Field(FIELD_SIZE, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
        AEnemy enemy = new Tank("tank1",1, dummyGameActivity, enemyView);
        enemy.moveTo(FIELD_SIZE * 5, FIELD_SIZE * 1);
        enemy.setHealthpoints(1);

        //act
        new Projectile(tower.getPosition(), enemy, tower.getDamage(), dummyImage);

        Thread.sleep(2000);

        //assert
        assertNotEquals(enemy.getHealthPoints(), 1);
    }

    @Test
    public void shootBombFromSpawnPositionToEnemies_SHOULD_hitEnemiesInRange() throws InterruptedException {
        //arrange
        ATower tower = new TowerBoombastic(new Field(FIELD_SIZE, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
        AEnemy enemyTarget = new Tank("tank1", 1, dummyGameActivity, enemyView);
        AEnemy enemySideshot = new Tank("tank2", 1, dummyGameActivity, enemyView);
        AEnemy enemySideshot2 = new Tank("tank3", 1, dummyGameActivity, enemyView);
        AEnemy enemyNotInRange = new Tank("tank4", 1, dummyGameActivity, enemyView);

        enemyTarget.moveTo(375, 300);
        enemySideshot.moveTo(280, 300);
        enemySideshot2.moveTo(450, 300);
        enemyNotInRange.moveTo(500, 300);

        //act
        new Bomb(tower.getPosition(), enemyTarget, Arrays.asList(enemySideshot, enemySideshot2, enemyNotInRange), tower.getDamage(), dummyImage);

        Thread.sleep(2000);

        //assert
        assertNotEquals(enemyTarget.getHealthPoints(), TANK_HEALTHPOINTS);
        assertNotEquals(enemySideshot.getHealthPoints(), TANK_HEALTHPOINTS);
        assertNotEquals(enemySideshot2.getHealthPoints(), TANK_HEALTHPOINTS);
        assertEquals(enemyNotInRange.getHealthPoints(), TANK_HEALTHPOINTS);
    }

    @Test
    public void shootSnowFlakeFromSpawnPositionToEnemy_SHOULD_decreaseEnemySpeed() throws InterruptedException {
        //arrange
        TowerFreezer tower = new TowerFreezer(new Field(FIELD_SIZE, 2, 3, FieldDescription.FREE), 1, dummyImage, dummyGameActivity);
        AEnemy enemy = new Tank("tank1", 1, dummyGameActivity, enemyView);

        enemy.moveTo(375, 300);

        //act
        new SnowFlake(tower.getPosition(), enemy, tower.getDamage(), tower.getSlowness(), dummyImage);
        //NullPointerException will come up because of not intantiated game

        Thread.sleep(2000);

        //assert
        assertTrue(enemy.getSlowness() > 0);
    }

}
