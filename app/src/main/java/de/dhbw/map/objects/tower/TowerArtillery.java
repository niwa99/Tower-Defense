package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.Projectile;
import de.dhbw.map.objects.enemy.Enemy;

import static de.dhbw.util.Constants.*;
import de.dhbw.map.structure.Field;

public class TowerArtillery extends ATower {
	private ImageView artilleryTowerImage;

	public TowerArtillery(Field field, int level, GameActivity gameActivity) {
		super(UUID.randomUUID(), TowerType.ARTILLERY, level, getTowerArtilleryRangeByLevel(level), getTowerArtilleryFirerateByLevel(level), field, gameActivity, getTowerArtilleryCostsByLevel(level),
				getTowerArtilleryDamageByLevel(level));

		artilleryTowerImage = new ImageView(gameActivity);
		artilleryTowerImage.setLayoutParams(TOWER_ARTILLERY_LEVEL_1_TOWER_SIZE_PARAMS);
		artilleryTowerImage.setImageResource(DRAWABLE_TOWER_ARTILLERY);
		artilleryTowerImage.setX(getPositionX());
		artilleryTowerImage.setY(getPositionY());
		setBaseImage(artilleryTowerImage);
	}

	public TowerArtillery(Field field, int level, ImageView artilleryTowerImage, GameActivity gameActivity) {
		super(UUID.randomUUID(), TowerType.ARTILLERY, level, getTowerArtilleryRangeByLevel(level), getTowerArtilleryFirerateByLevel(level), field, gameActivity, getTowerArtilleryCostsByLevel(level),
				getTowerArtilleryDamageByLevel(level));
		this.artilleryTowerImage = artilleryTowerImage;
	}

	@Override
	public boolean fire(List<Enemy> enemies) {
		artilleryTowerImage.setRotation((float)rotateImage(enemies));
		if (super.fire(enemies)) {
			new Projectile(getPosition(), super.targetedEnemy, this.getDamage(), DRAWABLE_BULLET, gameActivity);
			return true;
		}
		return false;

	}

	public static int getTowerArtilleryCostsByLevel(int level) {
		switch (level) {
			case 2: return TOWER_ARTILLERY_LEVEL_2_COSTS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_COSTS;
			default: return TOWER_ARTILLERY_LEVEL_1_COSTS;
		}
	}

	private static int getTowerArtilleryDamageByLevel(int level) {
		switch (level) {
			case 2: return TOWER_ARTILLERY_LEVEL_2_DAMAGE;
			case 3: return TOWER_ARTILLERY_LEVEL_3_DAMAGE;
			default: return TOWER_ARTILLERY_LEVEL_1_DAMAGE;
		}
	}

	private static int getTowerArtilleryRangeByLevel(int level) {
		switch (level) {
			case 2: return TOWER_ARTILLERY_LEVEL_2_RANGE_IN_PIXELS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_RANGE_IN_PIXELS;
			default: return TOWER_ARTILLERY_LEVEL_1_RANGE_IN_PIXELS;
		}
	}

	private static int getTowerArtilleryFirerateByLevel(int level) {
		switch (level) {
			case 2: return TOWER_ARTILLERY_LEVEL_2_FIRERATE_IN_SECONDS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_FIRERATE_IN_SECONDS;
			default: return TOWER_ARTILLERY_LEVEL_1_FIRERATE_IN_SECONDS;
		}
	}
}
