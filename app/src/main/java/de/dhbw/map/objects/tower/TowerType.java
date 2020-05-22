package de.dhbw.map.objects.tower;

import static de.dhbw.util.Constants.DRAWABLE_TOWER_ARTILLERY;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_BOOMBASTIC;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_FREEZER_BASE;
import static de.dhbw.util.Constants.DRAWABLE_TOWER_PLASMARIZER_BASE;
import static de.dhbw.util.Constants.TOWER_ARTILLERY_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_BOOMBASTIC_LEVEL_1_COSTS;
import static de.dhbw.util.Constants.TOWER_FREEZER_LEVEL_1_COSTS;

public enum TowerType {
    ARTILLERY("Artillery", DRAWABLE_TOWER_ARTILLERY, TOWER_ARTILLERY_LEVEL_1_COSTS),
    FREEZER("Freezer", DRAWABLE_TOWER_FREEZER_BASE, TOWER_FREEZER_LEVEL_1_COSTS),
    BOOMBASTIC("Boombastic", DRAWABLE_TOWER_BOOMBASTIC, TOWER_BOOMBASTIC_LEVEL_1_COSTS),
    PLASMARIZER("Plasmarizer", DRAWABLE_TOWER_PLASMARIZER_BASE, TOWER_ARTILLERY_LEVEL_1_COSTS),
    ASSAULTLASER("Assaultlaser", DRAWABLE_TOWER_ARTILLERY, TOWER_ARTILLERY_LEVEL_1_COSTS);

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
    public int getDrawable() {
        return this.drawable;
    }

    public int getPrice() {
        return this.price;
    }
}
