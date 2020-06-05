package de.dhbw;

public enum ImageElevation {
    ENEMIES(50f),
    BULLET(60f),
    RANGE_INDICATOR(100f);

    public float elevation;

    ImageElevation(float elevation) {
        this.elevation = elevation;
    }
}
