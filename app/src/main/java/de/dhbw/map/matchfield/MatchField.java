package de.dhbw.map.matchfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import tower.defense.enemy.Enemy;
import tower.defense.tower.Tower;

public class MatchField {
	private List<Enemy> enemies;
	private List<Tower> towers;
	
	public MatchField() {
		enemies = new ArrayList<>();
		towers = new ArrayList<>();
	}
	
	public void addTower(Tower tower) {
		towers.add(tower);
	}
	
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public boolean removeTower(UUID id) {
		Optional<Tower> tower = getTower(id);
		if(tower.isPresent()) {
			towers.remove(tower.get());
			return true;
		}
		return false;
	}
	
	public boolean removeEnemy(UUID id) {
		Optional<Enemy> enemy = getEnemy(id);
		if(enemy.isPresent()) {
			enemies.remove(enemy.get());
			return true;
		}
		return false;
	}

	public Optional<Tower> getTower(UUID id) {
		return towers.stream().filter(e -> e.getId()==id).findAny();
	}
	
	public List<UUID> getTowers(){
		return towers.stream().map(Tower::getId).collect(Collectors.toList());
	}
	
	public Optional<Enemy> getEnemy(UUID id) {
		return enemies.stream().filter(e -> e.getId()==id).findAny();
	}
	
	public List<UUID> getEnemies(){
		return enemies.stream().map(Enemy::getId).collect(Collectors.toList());
	}
}
