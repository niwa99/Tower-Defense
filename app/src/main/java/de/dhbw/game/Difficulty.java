package de.dhbw.game;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static Difficulty asDifficulty(int number) {
        switch (number) {
            case 0: return EASY;
            case 1: return MEDIUM;
            case 2: return HARD;
            default: return null;
        }
    }

    public static int asNumber(Difficulty difficulty) {
        switch (difficulty) {
            case EASY: return 0;
            case MEDIUM: return 1;
            case HARD: return 2;
            default: return -1;
        }
    }
}
