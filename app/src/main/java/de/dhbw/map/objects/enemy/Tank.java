package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.dhbw.map.structure.MapStructure;

import static de.dhbw.util.Constants.*;
import static de.dhbw.util.ObjectStorage.*;

public class Tank extends Enemy {
	private ImageView tankImage;
	private Timer timer;

	public Tank(String label, int level) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankLifePointsCostsByLevel(level));

		timer = new Timer();
		tankImage = new ImageView(getContext());
		tankImage.setLayoutParams(TANK_ENEMY_SIZE_PARAMS);
		tankImage.setImageResource(DRAWABLE_TANK);

		getMapLayout().addView(tankImage);
	}

	public Tank(String label, int level, ImageView tankImage) {
		super(label, UUID.randomUUID(), getTankHealthpointsByLevel(level), getTankSpeedByLevel(level), getTankValueByLevel(level), getTankValueByLevel(level));
		this.tankImage = tankImage;
	}

	@Override
	public boolean move(MapStructure map) {
		if (super.move(map)){
			getGameActivity().runOnUiThread(() -> {
				tankImage.setX(getPositionX());
				tankImage.setY(getPositionY());
				switch (getDirection()) {
					case UP:
						tankImage.setRotation(0);
						break;
					case RIGHT:
						tankImage.setRotation(90);
						break;
					case DOWN:
						tankImage.setRotation(180);
						break;
					case LEFT:
						tankImage.setRotation(270);
						break;
				}
			});
		}
		return false;
	}

	public void hit(int damage) {
		super.reduceHealthPoints(damage);
		getGameActivity().runOnUiThread(() -> tankImage.setImageResource(DRAWABLE_TANK_HITTED));
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getGameActivity().runOnUiThread(() -> tankImage.setImageResource(DRAWABLE_TANK));
			}
		}, 100);
	}

	public ImageView getTankImage() {
		return tankImage;
	}

	private static int getTankHealthpointsByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_HEALTHPOINTS;
			case 2: return TANK_LEVEL_2_HEALTHPOINTS;
			case 3: return TANK_LEVEL_3_HEALTHPOINTS;
			default: return TANK_LEVEL_1_HEALTHPOINTS;
		}
	}

	private static int getTankSpeedByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_SPEED;
			case 2: return TANK_LEVEL_2_SPEED;
			case 3: return TANK_LEVEL_3_SPEED;
			default: return TANK_LEVEL_1_SPEED;
		}
	}

	private static int getTankValueByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_VALUE;
			case 2: return TANK_LEVEL_2_VALUE;
			case 3: return TANK_LEVEL_3_VALUE;
			default: return TANK_LEVEL_1_VALUE;
		}
	}

	private static int getTankLifePointsCostsByLevel(int level) {
		switch (level) {
			case 1: return TANK_LEVEL_1_LIFE_POINT_COSTS;
			case 2: return TANK_LEVEL_2_LIFE_POINT_COSTS;
			case 3: return TANK_LEVEL_3_LIFE_POINT_COSTS;
			default: return TANK_LEVEL_1_LIFE_POINT_COSTS;
		}
	}
}
