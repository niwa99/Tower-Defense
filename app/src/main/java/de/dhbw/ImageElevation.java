package de.dhbw;

public enum ImageElevation {
    ENEMIES(50f),
    LIFE_BAR(51f),
    BULLET(60f),
    ANIMATION(70f),
    RANGE_INDICATOR(100f);

    public float elevation;

    ImageElevation(float elevation) {
        this.elevation = elevation;
    }
}
