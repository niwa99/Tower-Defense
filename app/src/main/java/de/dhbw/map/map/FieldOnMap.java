package de.dhbw.map.map;

public class FieldOnMap {

    // x and y Coordinates
    private int xCoord;
    private int yCoord;

    // Enum for Field type
    private FieldDescription fieldDescription;

    //Constructor
    public FieldOnMap(int xCoord, int yCoord, FieldDescription fieldDescription) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.fieldDescription = fieldDescription;
    }

    // Getters and Setters
    public int getyCoord() {

        return yCoord;
    }

    public int getxCoord() {

        return xCoord;
    }

    public FieldDescription getFieldDescription() {

        return fieldDescription;
    }

    public void setFieldDescription(FieldDescription fieldDescription){

        this.fieldDescription = fieldDescription;
    }
}
