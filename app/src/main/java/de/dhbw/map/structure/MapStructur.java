package de.dhbw.map.structure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.dhbw.game.util.Position;

public class MapStructur {
	private Map<String, Field> fields = new HashMap<String, Field>();
	private static final int sizeField = 80;

    // Constructor
    public MapStructur(){
        FieldDescription[][] fieldDescription = new FieldDescription[11][6];
        fieldDescription = createPath();
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 6; y++) {
            	Position pos = new Position(x,y);
                fields.put(pos.toString(), new Field(sizeField, x, y, fieldDescription[x][y]));
            }
        }
    }

    // sets the FieldDescription for the Map (says which Field belongs to the Path and which not)
    private FieldDescription[][] createPath(){

        // There is only one fix Path with the following Coordinates
        FieldDescription[][] fieldDescription = new FieldDescription[11][6];
        String[] path = new String[]{"0,2", "1,2", "1,1", "1,0", "2,0", "3,0", "4,0", "4,1", "4,2",
                "3,2", "3,3", "3,4", "4,4", "5,4", "6,4", "6,3", "6,2", "7,2", "8,2", "9,2", "10,2"};
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                for (String field : path){
                    if (field.equals(("" + i + "," + j))) {
                        fieldDescription[i][j] = FieldDescription.PATH;
                        break;
                    } else {
                        fieldDescription[i][j] = FieldDescription.FREE;
                    }
                }
            }
        }
        return fieldDescription;
    }
    
    public List<Field> getFields(){
    	return new ArrayList<Field>(fields.values());
    }

    /**
     * returns the first Field, where enemies should spawn
     *
     */
    public Field getFirstFieldForEnemy() {
    	return fields.values().stream().filter(f -> isFirstField(f)).findFirst().get();
    }

    /**
     * returns the next Field for an enemy to move
     * @param lastField
     * @param actualField
     * @return
     */
    public Field getNextFieldForEnemy(Field lastField, Field actualField) {
    	List<Field> possibleFields = new ArrayList<Field>();
    	possibleFields.add(fields.getOrDefault((new Position(actualField.getFieldPositionX()-1, actualField.getFieldPositionY()).toString()), new Field()));
    	possibleFields.add(fields.getOrDefault((new Position(actualField.getFieldPositionX(), actualField.getFieldPositionY()-1).toString()), new Field()));
    	possibleFields.add(fields.getOrDefault((new Position(actualField.getFieldPositionX()+1, actualField.getFieldPositionY()).toString()), new Field()));
    	possibleFields.add(fields.getOrDefault((new Position(actualField.getFieldPositionX(), actualField.getFieldPositionY()+1).toString()), new Field()));
    	Optional<Field> nextField = possibleFields.stream().filter(f -> f.getFieldDescription()==FieldDescription.PATH && !f.equals(lastField)).findAny();
    	
    	return nextField.isPresent()? nextField.get() : null;
    }
    
    public Field getField(Position pos) {
    	return fields.get(pos.toString());
    }

    //First Field has to be on the left side X=0
    public boolean isFirstField(Field field) {
    	return field.getFieldPositionX()==0 && field.getFieldDescription()==FieldDescription.PATH;
    }
    
    public int getSizeField() {
    	return sizeField;
    }
}
