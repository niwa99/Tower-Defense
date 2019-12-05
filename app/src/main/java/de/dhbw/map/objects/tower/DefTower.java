package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;
import static de.dhbw.util.ObjectStorage.*;

public class DefTower extends Tower {
	private ImageView defTowerImage;

	public DefTower(String label, Position position, int level) {
		super(label, UUID.randomUUID(), getDefTowerCostsByLevel(level), getDefTowerDamageByLevel(level),
				getDefTowerRangeByLevel(level), getDefTowerFirerateByLevel(level), position);

		defTowerImage = new ImageView(getContext());
		defTowerImage.setLayoutParams(DEF_TOWER_LEVEL_1_TOWER_SIZE_PARAMS);
		defTowerImage.setImageResource(DRAWABLE_TOWER);
		defTowerImage.setX(getPositionX());
		defTowerImage.setY(getPositionY());

		getMapLayout().addView(defTowerImage);
	}

	public DefTower(String label, Position position, int level, ImageView defTowerImage) {
		super(label, UUID.randomUUID(), getDefTowerCostsByLevel(level), getDefTowerDamageByLevel(level),
				getDefTowerRangeByLevel(level), getDefTowerFirerateByLevel(level), position);
		this.defTowerImage = defTowerImage;
	}

	@Override
	public boolean fire(List<Enemy> enemies) {
		defTowerImage.setRotation((float)rotateImage(enemies));
		if (super.fire(enemies)){
			new Bullet(getPosition(), super.targetedEnemy, DefTower.this);
			return true;
		}
		return false;

	}

	private static int getDefTowerCostsByLevel(int level) {
		switch (level) {
			case 1: return DEF_TOWER_LEVEL_1_COSTS;
			default: return DEF_TOWER_LEVEL_1_COSTS;
		}
	}

	private static int getDefTowerDamageByLevel(int level) {
		switch (level) {
			case 1: return DEF_TOWER_LEVEL_1_DAMAGE;
			default: return DEF_TOWER_LEVEL_1_DAMAGE;
		}
	}

	private static int getDefTowerRangeByLevel(int level) {
		switch (level) {
			case 1: return DEF_TOWER_LEVEL_1_RANGE_IN_PIXELS;
			default: return DEF_TOWER_LEVEL_1_RANGE_IN_PIXELS;
		}
	}

	private static int getDefTowerFirerateByLevel(int level) {
		switch (level) {
			case 1: return DEF_TOWER_LEVEL_1_FIRERATE_IN_SECONDS;
			default: return DEF_TOWER_LEVEL_1_FIRERATE_IN_SECONDS;
		}
	}
}
