package de.dhbw.map.objects.enemy;

import java.util.UUID;

public abstract class Enemy{
	private String label;
	private UUID id ;
	private int hp;
	private int progress;
	private int x;
	private int y;
	
	public Enemy(String label, UUID id, int hp, int x, int y) {
		this.label=label;
		this.id=id;
		this.hp=hp;
		this.progress=0;
		this.x=x;
		this.y=y;
	}
	
	public int getHealthPoints() {
		return hp;
	}
	
	public String getLabel() {
		return label;
	}
	
	public UUID getId() {
		return id;
	}
	
	public int getPositionX() {
		return x;
	}
	
	public int getPositionY() {
		return y;
	}
}
