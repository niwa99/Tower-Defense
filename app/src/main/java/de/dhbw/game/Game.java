package de.dhbw.game;

import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.util.Position;
import de.dhbw.map.structure.MapStructur;

public class Game {
	public Game() {
		
	}

	/**
	 * this method starts the game
	 *
	 */
	public static void main(String[] args) {
		MapStructur map = new MapStructur();
		Tank tank1 = new Tank("Tank1",5, 950);
		Tank tank2 = new Tank("Tank2",9, 950);
		DefTower tower = new DefTower("tower1",1, map.getSizeField()*2, 2, map.getField(new Position(2, 3)).getSpawnPoint());
		DefTower tower2 = new DefTower("tower2",2, map.getSizeField()*3, 5, map.getField(new Position(6, 1)).getSpawnPoint());
		
		
		MatchField matchField = new MatchField(map);
		matchField.addEnemy(tank1);
		matchField.addEnemy(tank2);
		matchField.addTower(tower);
		matchField.addTower(tower2);
		
		matchField.moveEnemies();
		matchField.fireTowers();
	}
}
