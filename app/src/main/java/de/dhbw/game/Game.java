package de.dhbw.game;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.ObjectStorage.*;

public class Game {
    private LinearLayout.LayoutParams buttonSizeParams;
    private List<Button> mapButtons;
    private Optional<Button> clickedButton = Optional.ofNullable(null);

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

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Optional<Button> button = mapButtons.stream().filter(e -> v.getId()==e.getId()).findFirst();
                if(button.isPresent()){
                    if(clickedButton.isPresent()){
                        if(clickedButton.get().getId()==button.get().getId()){
                            clickedButton = Optional.ofNullable(null);
                            button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            createNewTowerOnField();
                            //TODO
                        }else{
                            clickedButton.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                            button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                            clickedButton = button;
                        }
                    }else{
                        clickedButton = button;
                        button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));

                    }
                }
            }
        };

        int count = 0;
        getMapStructure().getFields().stream().forEach(f -> {
            Button fieldButton = new Button(getContext());
            fieldButton.setX(f.getSpawnPoint().getX());
            fieldButton.setY(f.getSpawnPoint().getY());
            fieldButton.setLayoutParams(buttonSizeParams);
            fieldButton.setId(f.getId());
            if (f.getFieldDescription().equals(FieldDescription.PATH)) {
                fieldButton.setEnabled(false);
                fieldButton.setBackgroundColor(Color.GREEN);
                getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if (f.getFieldDescription().equals(FieldDescription.FREE)) {
                fieldButton.setOnClickListener(listener);
                fieldButton.setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            }
        });
    }

    public void createNewTowerOnField(){
	    //TODO
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