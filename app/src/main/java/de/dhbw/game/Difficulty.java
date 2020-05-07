package de.dhbw.game;

public enum Difficulty {
    EASY(0),
    MEDIUM(1),
    HARD(2);

    private final int difficultyAsNumber;
    
    Difficulty(int difficultyAsNumber){
        this.difficultyAsNumber = difficultyAsNumber;
    }
    public static Difficulty asDifficulty(int number) {
        for (Difficulty d : Difficulty.values()) {
            if(d.difficultyAsNumber==number){
                return d;
            }
        }
        return null;
    }

    public int asNumber() {
        return difficultyAsNumber;
    }
}
