package de.dhbw.game;

public enum EnemyType {
    TANK("tank"),
    CAR("car"),
    PLANE("plane");

    private final String label;

    EnemyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
