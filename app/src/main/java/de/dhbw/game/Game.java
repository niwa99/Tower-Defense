package de.dhbw.game;

import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Position;

import static de.dhbw.util.ObjectStorage.*;

public class Game {
    private LinearLayout.LayoutParams buttonSizeParams;
    private List<Button> mapButtons;

	public Game() {
		setMapStructure(new MapStructure());
		buttonSizeParams = new LinearLayout.LayoutParams(getMapStructure().getSizeField(), getMapStructure().getSizeField());
		mapButtons = new ArrayList<>();
		setMatchField(new MatchField());
	}

	public void runGame() {
	    generateButtonsOnMap();

        addEnemiesToMatchField();
        addTowersToMatchField();

        getMatchField().startEnemyMovement();
        getMatchField().startTowerFire();
    }

    private void generateButtonsOnMap() {
        getMapStructure().getFields().stream().forEach(f -> {
            Button fieldButton = new Button(getContext());
            fieldButton.setX(f.getSpawnPoint().getX());
            fieldButton.setY(f.getSpawnPoint().getY());
            fieldButton.setLayoutParams(buttonSizeParams);
            if (f.getFieldDescription().equals(FieldDescription.PATH)) {
                fieldButton.setEnabled(false);
                fieldButton.setBackgroundColor(Color.GREEN);
                getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if (f.getFieldDescription().equals(FieldDescription.FREE)) {
                //fieldButton.setBackgroundColor(Color.GRAY);
                //layout.addView(fieldButton);
                //btns.add(fieldButton);
            }
        });
    }

    private void addEnemiesToMatchField() {
        Tank tank1 = new Tank("Tank1", 1);
        Tank tank2 = new Tank("Tank2", 2);

        getMatchField().addEnemy(tank1);
        getMatchField().addEnemy(tank2);
    }

    private void addTowersToMatchField() {
        DefTower tower = new DefTower("tower1", getMapStructure().getField(new Position(2, 3)).getSpawnPoint(), 1);
        DefTower tower2 = new DefTower("tower2", getMapStructure().getField(new Position(6, 1)).getSpawnPoint(), 1);

        getMatchField().addTower(tower);
        getMatchField().addTower(tower2);
	}
}