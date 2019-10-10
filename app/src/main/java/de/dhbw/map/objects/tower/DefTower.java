package de.dhbw.map.objects.tower;

import java.util.UUID;

public class DefTower extends Tower{

	public DefTower(String label, UUID id, int costs, int damage, int range, int fireRate, int x, int y) {
		super(label, UUID.randomUUID(), costs, damage, range, fireRate, x, y);
		
	}

}
