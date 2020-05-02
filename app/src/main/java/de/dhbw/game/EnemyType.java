package de.dhbw.game;

public enum EnemyType {
    TANK("tank"),
    FREEZER("freezer");

    private final String label;

    EnemyType(String label){
        this.label=label;
    }

    public String getLabel(){
        return label;
    }
}
