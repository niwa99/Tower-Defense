package de.dhbw.map.structure;

import de.dhbw.util.Position;

public class Field {

    private int xCoord;
    private int yCoord;
    private int sizeInPx;

    private FieldDescription fieldDescription;

    /**
     * Constructor
     * @param sizeInPx
     * @param xCoord
     * @param yCoord
     * @param fieldDescription
     */
    public Field(int sizeInPx, int xCoord, int yCoord, FieldDescription fieldDescription) {
    	this.sizeInPx = sizeInPx;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.fieldDescription = fieldDescription;
    }

    /**
     * Constructor (TEST PURPOSE ONLY!)
     */
    public Field() {
    	this.sizeInPx = 0;
        this.xCoord = 0;
        this.yCoord = 0;
        this.fieldDescription = FieldDescription.FREE;
    }

    /**
     *
     * @return unique id of the field object
     */
    public String getId() {
        return String.valueOf(getFieldPositionX())+"01230"+String.valueOf(getFieldPositionY());
    }

    /**
     * Get a position object of the current field. Not in px!
     * @return position
     */
    public Position getFieldPosition() {
        return new Position(xCoord, yCoord);
    }

    /**
     * @return y-Position of the Field in the map
     */
    public int getFieldPositionY() {
        return yCoord;
    }

    /**
     *
     * @return x-Positon of the Field in the map
     */
    public int getFieldPositionX() {
        return xCoord;
    }

    /**
     * @return real x-Coordinate in px
     *
     */
    public int getPixelCoordX() {
    	return xCoord*sizeInPx;
    }

    /**
     * @return real y-Coordinate in px
     *
     */
    public int getPixelCoordY() {
    	return yCoord*sizeInPx;
    }

    /**
     *
     * @return field description of this field.
     */
    public FieldDescription getFieldDescription() {
        return fieldDescription;
    }

    /**
     * Set the field description of this field.
     * @param fieldDescription
     */
    public void setFieldDescription(FieldDescription fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * Get the midpoint of this field in px as spawnpoint.
     * @return position of mid-coordinates in px
     */
    public Position getSpawnPoint() {
    	return new Position(getPixelCoordX()+(sizeInPx/2), getPixelCoordY()+(sizeInPx/2));
    }

    /**
     *
     * @return size of this field in px
     */
    public int getSizeInPx() {
        return sizeInPx;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldDescription == null) ? 0 : fieldDescription.hashCode());
		result = prime * result + sizeInPx;
		result = prime * result + xCoord;
		result = prime * result + yCoord;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (fieldDescription != other.fieldDescription)
			return false;
		if (sizeInPx != other.sizeInPx)
			return false;
		if (xCoord != other.xCoord)
			return false;
		if (yCoord != other.yCoord)
			return false;
		return true;
	}
}