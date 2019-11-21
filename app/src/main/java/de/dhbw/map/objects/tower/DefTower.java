package de.dhbw.map.objects.tower;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.UUID;

import de.dhbw.map.objects.enemy.Enemy;
import de.dhbw.util.Position;

public class DefTower extends Tower{
	private static final int costs = 100;
	private static final int damage = 1;
	private static final int range = 100;
	private static final int fireRate = 5;
	private ImageView towerImage;
	private ImageView bulletImage;
	private static final LinearLayout.LayoutParams paramsBullet = new LinearLayout.LayoutParams(50,50);


	public DefTower(ImageView towerImage, ImageView bulletImage, String label, int damage, int range, int fireRate, Position pos) {
		super(label, UUID.randomUUID(), costs, damage, range, fireRate, pos);

		this.towerImage=towerImage;
		this.bulletImage=bulletImage;

		towerImage.setX(getPositionX());
		towerImage.setY(getPositionY());
		towerImage.bringToFront();
	}

	@Override
	public boolean fire(List<Enemy> enemies) {
		if(super.fire(enemies)){
			towerImage.setRotation(rotateImage(enemies));
			//bulletImage.setVisibility(View.VISIBLE);
			//Bullet bullet = new Bullet(bulletImage, getPosition(), new Position(0, 0), 950);
			//bullet.visualizeShot();
			return true;

		}
		return false;
	}
}
