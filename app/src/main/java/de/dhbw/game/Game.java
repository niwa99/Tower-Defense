package de.dhbw.game;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.objects.tower.Tower;
import de.dhbw.map.structure.Field;
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
		buttonSizeParams = new LinearLayout.LayoutParams(MapStructure.getSizeField(), MapStructure.getSizeField());
		mapButtons = new ArrayList<>();
		setMatchField(new MatchField());
	}

	public void runGame() {
	    generateButtonsOnMap();

        addEnemiesToMatchField();
        addTowersToMatchField();

    }

    private void generateButtonsOnMap() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Optional<Button> button = mapButtons.stream().filter(e -> v.getTransitionName()==e.getTransitionName()).findFirst();
                if(button.isPresent()){
                    Position pos = getPositionFromButtonId(button.get().getTransitionName());
                    Field field = getMapStructure().getField(pos);

                    if(field.getFieldDescription()==FieldDescription.FREE){
                        if(clickedButton.isPresent()){
                            if(clickedButton.get().getTransitionName()==button.get().getTransitionName()){
                                clickedButton = Optional.ofNullable(null);
                                button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                                createNewTowerOnField(getPositionFromButtonId(button.get().getTransitionName()));
                            }else{
                                clickedButton.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_TRANSPARENT, null));
                                button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));
                                clickedButton = button;
                            }
                        }else{
                            clickedButton = button;
                            button.get().setBackground(getContext().getResources().getDrawable(Constants.DRAWABLE_FIELD_ON_CLICK_PLUS, null));

                        }
                    }else if(field.getFieldDescription()==FieldDescription.TOWER){
                        Optional<Tower> tower = getMatchField().getTower(field);
                        if(tower.isPresent()){
                            getMatchField().removeTower(tower.get());
                            getMapLayout().removeView(((DefTower)tower.get()).getDefTowerImage());
                            field.setFieldDescription(FieldDescription.FREE);
                        }
                    }
                }
            }
        };

        View.OnClickListener spwanFieldListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEnemiesToMatchField();
            }
        };

        int count = 0;
        getMapStructure().getFields().stream().forEach(f -> {
            Button fieldButton = new Button(getContext());
            fieldButton.setX(f.getSpawnPoint().getX());
            fieldButton.setY(f.getSpawnPoint().getY());
            fieldButton.setLayoutParams(buttonSizeParams);
            fieldButton.setTransitionName(f.getId());
            if (f.getFieldDescription().equals(FieldDescription.PATH)) {
                fieldButton.setEnabled(false);
                fieldButton.setBackground(getContext().getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
                getMapLayout().addView(fieldButton);
                mapButtons.add(fieldButton);
            } else if(f.getFieldDescription().equals(FieldDescription.Spawn)){
                fieldButton.setOnClickListener(spwanFieldListener);
                fieldButton.setBackground(getContext().getResources().getDrawable(MapStructure.calculatePath(fieldButton.getX(), fieldButton.getY()), null));
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

    public Position getPositionFromButtonId(String id){
        String[] result = id.split("01230");
	    return new Position(Integer.valueOf(result[0]), Integer.valueOf(result[1]));
    }

    public void createNewTowerOnField(Position pos){
	    if(getMapStructure().getField(pos).getFieldDescription()==FieldDescription.FREE) {
            DefTower newTower = new DefTower("tower1", getMapStructure().getField(pos), 1);
            getMatchField().addTower(newTower);
            getMapStructure().getField(pos).setFieldDescription(FieldDescription.TOWER);
        }
    }

    private void addEnemiesToMatchField() {
        Tank tank1 = new Tank("Tank1", 1);
        Tank tank2 = new Tank("Tank2", 2);
        Tank tank3 = new Tank("Tank2", 3);

        getMatchField().addEnemy(tank1);
        getMatchField().addEnemy(tank2);
        getMatchField().addEnemy(tank3);
    }

    private void addTowersToMatchField() {
        //DefTower tower = new DefTower("tower1", getMapStructure().getField(new Position(2, 3)).getSpawnPoint(), 1);
        //DefTower tower2 = new DefTower("tower2", getMapStructure().getField(new Position(6, 1)).getSpawnPoint(), 1);
        createNewTowerOnField(new Position(2,3));
        createNewTowerOnField(new Position(6,1));
        //getMatchField().addTower(tower);
        //getMatchField().addTower(tower2);
	}
}