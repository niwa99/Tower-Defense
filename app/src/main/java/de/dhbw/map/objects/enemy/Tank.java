package de.dhbw.map.objects.enemy;

import android.widget.ImageView;

import java.util.UUID;

import de.dhbw.map.structure.MapStructure;

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
			switch (getDirection()) {
				case "top": tankImage.setRotation(0); break;
				case "right": tankImage.setRotation(90); break;
				case "bottom": tankImage.setRotation(180); break;
				case "left": tankImage.setRotation(270); break;
			}
		}
		return false;
	}
}
