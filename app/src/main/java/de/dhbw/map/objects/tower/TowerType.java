package de.dhbw.map.objects.tower;

import static de.dhbw.util.Constants.DRAWABLE_TOWER;
import static de.dhbw.util.Constants.DEF_TOWER_LEVEL_1_COSTS;

public enum TowerType {
    ARTILLERY("Artillery", DRAWABLE_TOWER, DEF_TOWER_LEVEL_1_COSTS),
    FREEZER("Freezer", DRAWABLE_TOWER, DEF_TOWER_LEVEL_1_COSTS),
    GUN("Gun", DRAWABLE_TOWER, DEF_TOWER_LEVEL_1_COSTS);

    private String type;
    private int drawable;
    private int price;

    TowerType(String type, int drawable, int price) {
        this.type = type;
        this.drawable = drawable;
        this.price=price;
    }

    public String getType() {
        return this.type;
    }
    public int getDrawable() { return this.drawable;}
    public int getPrice(){
        return this.price;
    }
}
