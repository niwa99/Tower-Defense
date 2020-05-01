package de.dhbw.game.wave;

public class WaveComposition {
    private EnemyType type;
    private int level;
    private int amount;

    public WaveComposition(EnemyType type, int level, int amount){
        this.type=type;
        this.level=level;
        this.amount=amount;
    }

    public EnemyType getEnemyType(){
        return type;
    }

    public int getLevel(){
        return level;
    }

    public int getAmount(){
        return amount;
    }
}
