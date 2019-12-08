package de.dhbw.map.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dhbw.util.Constants;
import de.dhbw.util.Position;

import static de.dhbw.util.Constants.*;

public class MapStructure {

	private Map<String, Field> fields = new HashMap<String, Field>();
    private static final Position[] path = new Position[] {
            new Position(0,2), new Position(1,2), new Position(1,1),
            new Position(1,0), new Position(2,0), new Position(3,0),
            new Position(4,0), new Position(4,1), new Position(4,2),
            new Position(3,2), new Position(3,3), new Position(3,4),
            new Position(4,4), new Position(5,4), new Position(6,4),
            new Position(6,3), new Position(6,2), new Position(7,2),
            new Position(8,2), new Position(9,2), new Position(10,2)};

    public MapStructure() {
        FieldDescription[][] fieldDescription = createPath();
        for (int x = 0; x < AMOUNT_COLUMNS; x++) {
            for (int y = 0; y < AMOUNT_ROWS; y++) {
            	Position pos = new Position(x,y);
                fields.put(pos.toString(), new Field(FIELD_SIZE, x, y, fieldDescription[x][y]));
            }
        }
    }

    /**
     * Sets the FieldDescriptions for the Map (says which Field belongs to the Path and which not)
     * @return 2D-FieldDescription-Array
     */
    private FieldDescription[][] createPath() {
        // There is only one fixed Path with the following Coordinates
        FieldDescription[][] fieldDescription = new FieldDescription[AMOUNT_COLUMNS][AMOUNT_ROWS];
        boolean spawn = true;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                for (Position pos : path) {
                    if (pos.equals(new Position(i, j))) {
                        if(spawn){
                            fieldDescription[i][j] = FieldDescription.Spawn;
                            spawn=false;
                        }else{
                            fieldDescription[i][j] = FieldDescription.PATH;
                        }
                        break;
                    } else {
                        fieldDescription[i][j] = FieldDescription.FREE;
                    }
                }
            }
        }
        return fieldDescription;
    }

    /**
     * This Method calculates the needed path picture
     * @param xCoord
     * @param yCoord
     * @return pathImage
     */
    public static int calculatePath(float xCoord, float yCoord){

        int pathImage = DRAWABLE_PATH_HORIZONTAL;
        int pathNumber = getPathNumber(xCoord, yCoord);
        if (pathNumber != 0 && pathNumber != 20) {
            if (path[pathNumber].getX() == path[pathNumber - 1].getX() && path[pathNumber].getX() == path[pathNumber + 1].getX()) {
                pathImage = Constants.DRAWABLE_PATH_VERTICAL;
            } else if (path[pathNumber].getY() == path[pathNumber - 1].getY() && path[pathNumber].getY() == path[pathNumber + 1].getY()) {
                pathImage = Constants.DRAWABLE_PATH_HORIZONTAL;
            } else if (path[pathNumber].getY() == path[pathNumber - 1].getY()) {
                if (path[pathNumber].getY() < path[pathNumber + 1].getY()) {
                    if(path[pathNumber].getX() < path[pathNumber - 1].getX()){
                        pathImage = Constants.DRAWABLE_PATH_RIGHT_DOWN;
                    } else {
                        pathImage = Constants.DRAWABLE_PATH_LEFT_DOWN;
                    }
                } else {
                    if(path[pathNumber].getX() < path[pathNumber - 1].getX()){
                        pathImage = Constants.DRAWABLE_PATH_RIGHT_UP;
                    } else {
                        pathImage = Constants.DRAWABLE_PATH_LEFT_UP;
                    }
                }
            } else {
                if (path[pathNumber].getX() < path[pathNumber + 1].getX()) {
                    if(path[pathNumber].getY() < path[pathNumber - 1].getY()){
                        pathImage = Constants.DRAWABLE_PATH_RIGHT_DOWN;
                    } else {
                        pathImage = Constants.DRAWABLE_PATH_RIGHT_UP;
                    }
                } else {
                    if (path[pathNumber].getX() < path[pathNumber - 1].getX()) {
                        pathImage = Constants.DRAWABLE_PATH_LEFT_DOWN;
                    } else {
                        pathImage = Constants.DRAWABLE_PATH_LEFT_UP;
                    }
                }
            }
        }
        return pathImage;
    }


    /**
     * This Method return the index of the path with the Coordinates from the parameters
     * @param xCoord
     * @param yCoord
     * @return path index
     */
    public static int getPathNumber(float xCoord, float yCoord){

        int i = 0;
        int x = (int) ((xCoord - 75) / FIELD_SIZE);
        int y = (int) ((yCoord - 75) / FIELD_SIZE);
        for (; i < 20; i++){
            if (path[i].getX() == x && path[i].getY() == y){
                return i;
            }
        }
        return i;
    }


    public List<Field> getFields(){
    	return new ArrayList<Field>(fields.values());
    }

    /**
     *
     * @param progress
     * @return Field where the enemy should move to
     */
    public Field getFieldForEnemy(int progress) {
        return progress<path.length ? fields.get(path[progress].toString()) : null;
    }
    
    public Field getField(Position pos) {
    	return fields.get(pos.toString());
    }
    
    public static int getSizeField() {
    	return FIELD_SIZE;
    }
}
