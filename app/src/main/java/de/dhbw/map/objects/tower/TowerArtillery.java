package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.activities.GameActivity;
import de.dhbw.map.objects.bullet.Projectile;
import de.dhbw.map.objects.enemy.AEnemy;

import static de.dhbw.util.Constants.*;
import de.dhbw.map.structure.Field;

public class TowerArtillery extends ATower {

	private ImageView artilleryTowerImage;

	/**
	 * Constructor
	 * @param field
	 * @param level
	 * @param gameActivity
	 */
	public TowerArtillery(Field field, int level, GameActivity gameActivity) {
		super(UUID.randomUUID(), TowerType.ARTILLERY, level, getTowerArtilleryCostsByLevel(level),
				getTowerArtilleryDamageByLevel(level), getTowerArtilleryRangeByLevel(level), getTowerArtilleryFirerateByLevel(level), field, gameActivity);

		artilleryTowerImage = new ImageView(gameActivity);
		artilleryTowerImage.setLayoutParams(TOWER_ARTILLERY_LEVEL_1_TOWER_SIZE_PARAMS);
		artilleryTowerImage.setImageResource(DRAWABLE_TOWER_ARTILLERY);
		artilleryTowerImage.setX(getPositionX());
		artilleryTowerImage.setY(getPositionY());
		setBaseImage(artilleryTowerImage);
	}

	/**
	 * Constructor (TEST PURPOSE ONLY!)
	 * @param field
	 * @param level
	 * @param artilleryTowerImage
	 * @param gameActivity
	 */
	public TowerArtillery(Field field, int level, ImageView artilleryTowerImage, GameActivity gameActivity) {
		super(UUID.randomUUID(), TowerType.ARTILLERY, level, getTowerArtilleryCostsByLevel(level),
				getTowerArtilleryDamageByLevel(level), getTowerArtilleryRangeByLevel(level), getTowerArtilleryFirerateByLevel(level), field, gameActivity);
		this.artilleryTowerImage = artilleryTowerImage;
	}

	@Override
	public boolean fire(List<AEnemy> enemies) {
		artilleryTowerImage.setRotation((float)rotateImage(enemies));
		if (super.fire(enemies)) {
			new Projectile(getPosition(), super.targetedEnemy, this.getDamage(), DRAWABLE_BULLET, gameActivity, FIELD_SIZE/2).start();
			return true;
		}
		return false;
	}

	@Override
	public int getCosts(int level) {
		return getTowerArtilleryCostsByLevel(level);
	}

	@Override
	public int getDamage(int level) {
		return getTowerArtilleryDamageByLevel(level);
	}

	@Override
	public int getRange(int level) {
		return getTowerArtilleryRangeByLevel(level);
	}

	@Override
	public int getFireRate(int level) {
		return getTowerArtilleryFirerateByLevel(level);
	}

	/**
	 * Get the costs of this tower by level.
	 * @param level
	 * @return costs
	 */
	public static int getTowerArtilleryCostsByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_COSTS;
			case 2: return TOWER_ARTILLERY_LEVEL_2_COSTS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_COSTS;
			case 4: return TOWER_ARTILLERY_LEVEL_4_COSTS;
			default: return TOWER_ARTILLERY_LEVEL_4_COSTS;
		}
	}

	/**
	 * Get the damage of this tower by level.
	 * @param level
	 * @return damage
	 */
	private static int getTowerArtilleryDamageByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_DAMAGE;
			case 2: return TOWER_ARTILLERY_LEVEL_2_DAMAGE;
			case 3: return TOWER_ARTILLERY_LEVEL_3_DAMAGE;
			case 4: return TOWER_ARTILLERY_LEVEL_4_DAMAGE;
			default: return TOWER_ARTILLERY_LEVEL_4_DAMAGE;
		}
	}

	/**
	 * Get the range in px of this tower by level.
	 * @param level
	 * @return range
	 */
	private static int getTowerArtilleryRangeByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_RANGE_IN_PIXELS;
			case 2: return TOWER_ARTILLERY_LEVEL_2_RANGE_IN_PIXELS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_RANGE_IN_PIXELS;
			case 4: return TOWER_ARTILLERY_LEVEL_4_RANGE_IN_PIXELS;
			default: return TOWER_ARTILLERY_LEVEL_4_RANGE_IN_PIXELS;
		}
	}

	/**
	 * Get the firerate of this tower by level.
	 * @param level
	 * @return firerate
	 */
	private static int getTowerArtilleryFirerateByLevel(int level) {
		switch (level) {
			case 1: return TOWER_ARTILLERY_LEVEL_1_FIRERATE_IN_SECONDS;
			case 2: return TOWER_ARTILLERY_LEVEL_2_FIRERATE_IN_SECONDS;
			case 3: return TOWER_ARTILLERY_LEVEL_3_FIRERATE_IN_SECONDS;
			case 4: return TOWER_ARTILLERY_LEVEL_4_FIRERATE_IN_SECONDS;
			default: return TOWER_ARTILLERY_LEVEL_4_FIRERATE_IN_SECONDS;
		}
	}
}
