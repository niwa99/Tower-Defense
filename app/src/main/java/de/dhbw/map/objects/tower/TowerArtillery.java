package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.ABullet;
import de.dhbw.map.objects.bullet.Projectile;
import de.dhbw.map.objects.enemy.Enemy;

import static de.dhbw.util.Constants.*;
import de.dhbw.map.structure.Field;

public class TowerArtillery extends ATower {
	private ImageView defTowerImage;

	public TowerArtillery(String label, Field field, int level, GameActivity gameActivity) {
		super(label, UUID.randomUUID(), getDefTowerCostsByLevel(level), getDefTowerDamageByLevel(level),
				getDefTowerRangeByLevel(level), getDefTowerFirerateByLevel(level), field, gameActivity);

		defTowerImage = new ImageView(gameActivity);
		defTowerImage.setLayoutParams(TOWER_ARTILLERY_LEVEL_1_TOWER_SIZE_PARAMS);
		defTowerImage.setImageResource(DRAWABLE_TOWER_ARTILLERY);
		defTowerImage.setX(getPositionX());
		defTowerImage.setY(getPositionY());
		setBaseImage(defTowerImage);
	}

	public TowerArtillery(String label, Field field, int level, ImageView defTowerImage, GameActivity gameActivity) {
		super(label, UUID.randomUUID(), getDefTowerCostsByLevel(level), getDefTowerDamageByLevel(level),
				getDefTowerRangeByLevel(level), getDefTowerFirerateByLevel(level), field, gameActivity);
		this.defTowerImage = defTowerImage;
	}

	@Override
	public boolean fire(List<Enemy> enemies) {
		defTowerImage.setRotation((float)rotateImage(enemies));
		if (super.fire(enemies)) {
			new Projectile(getPosition(), super.targetedEnemy, this.getDamage(), DRAWABLE_BULLET, gameActivity, FIELD_SIZE/2);
			return true;
		}
		return false;
	}

	public static int getDefTowerCostsByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_COSTS;
			default: return TOWER_ARTILLERY_LEVEL_1_COSTS;
		}
	}

	private static int getDefTowerDamageByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_DAMAGE;
			default: return TOWER_ARTILLERY_LEVEL_1_DAMAGE;
		}
	}

	private static int getDefTowerRangeByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_RANGE_IN_PIXELS;
			default: return TOWER_ARTILLERY_LEVEL_1_RANGE_IN_PIXELS;
		}
	}

	private static int getDefTowerFirerateByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_FIRERATE_IN_SECONDS;
			default: return TOWER_ARTILLERY_LEVEL_1_FIRERATE_IN_SECONDS;
		}
	}
}
