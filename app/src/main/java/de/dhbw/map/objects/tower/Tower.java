package de.dhbw.map.objects.tower;

import java.util.UUID;

public abstract class Tower {
	private String label;
	private UUID id;
	private int costs;
	private int damage;
	private int range;
	private int fireRate;
	private int x;
	private int y;
	
	public Tower(String label, UUID id, int costs, int damage, int range, int fireRate, int x, int y) {
		this.label=label;
		this.id=id;
		this.costs=costs;
		this.damage=damage;
		this.range=range;
		this.fireRate=fireRate;
		this.x=x;
		this.y=y;
	}

	public String getLabel() {
		return label;
	}

	public UUID getId() {
		return id;
	}

	public int getCosts() {
		return costs;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public int getFireRate() {
		return fireRate;
	}
	
	public int getPositionX() {
		return x;
	}
	
	public int getPositionY() {
		return y;
	}	
}
