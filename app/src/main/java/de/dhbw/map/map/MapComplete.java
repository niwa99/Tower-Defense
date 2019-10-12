package de.dhbw.map.map;

import java.util.ArrayList;
import java.util.List;

public class MapComplete {

    // Constructor
    public MapComplete(){

        List<FieldOnMap> fieldList = new ArrayList<>();
        FieldDescription[][] fieldDescription = new FieldDescription[11][6];
        fieldDescription = createPath();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                fieldList.add(new FieldOnMap(i, j, fieldDescription[i][j]));
            }
        }
    }

    // sets the FieldDescription for the Map (says which Field belongs to the Path and which not)
    private FieldDescription[][] createPath(){

        // There is only one fixed Path with the following Coordinates
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
}
