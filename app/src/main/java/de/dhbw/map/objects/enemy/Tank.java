package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.UUID;

import de.dhbw.R;
import de.dhbw.activities.GameActivity;
import de.dhbw.map.structure.MapStructure;
import android.content.Context;

public class Tank extends Enemy{
	private final static int hp = 7;
	private final static int speed = 999;
	ImageView tankImage;

	public Tank(ImageView tankImage ,String label, int hp, int speed) {
		super(label, UUID.randomUUID(), hp, speed);
		this.tankImage = tankImage;
	}

	@Override
	public boolean move(MapStructure map) {
		if(super.move(map)){
			tankImage.setX(getPositionX());
			tankImage.setY(getPositionY());
		}
		return false;
	}
}
