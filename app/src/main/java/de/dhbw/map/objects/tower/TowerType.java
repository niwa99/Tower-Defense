package de.dhbw.map.objects.tower;

import static de.dhbw.util.Constants.DRAWABLE_TOWER;
import static de.dhbw.util.Constants.DEF_TOWER_LEVEL_1_COSTS;

public enum TowerType {
    ARTILLERY("Artillery", DRAWABLE_TOWER),
    FREEZER("Freezer", DRAWABLE_TOWER),
    GUN("Gun", DRAWABLE_TOWER);

    private String type;
    private int drawable;
    private int cost;

    TowerType(String type, int drawable) {

        this.type = type;
        this.drawable = drawable;
    }

    public String getType() {
        return this.type;
    }
    public int getDrawable() { return this.drawable;}
    public int getCosts(String type) {
        if (type == "Artillery") {
            return DEF_TOWER_LEVEL_1_COSTS;
        }  else if (type == "Freezer") {
            return DEF_TOWER_LEVEL_1_COSTS;
        } else {
            return DEF_TOWER_LEVEL_1_COSTS;
        }
    }
}
