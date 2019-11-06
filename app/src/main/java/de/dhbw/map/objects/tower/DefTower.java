package de.dhbw.map.objects.tower;

import android.widget.ImageView;

import java.util.UUID;

import de.dhbw.util.Position;

public class DefTower extends Tower{
	private static final int costs = 100;
	private static final int damage = 1;
	private static final int range = 100;
	private static final int fireRate = 5;
	ImageView towerImage;

	public DefTower(ImageView towerImage,String label, int damage, int range, int fireRate, Position pos) {
		super(label, UUID.randomUUID(), costs, damage, range, fireRate, pos);
		this.towerImage=towerImage;
		towerImage.setX(getPositionX());
		towerImage.setY(getPositionY());
		towerImage.bringToFront();
		
	}



}
