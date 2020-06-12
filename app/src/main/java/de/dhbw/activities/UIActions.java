package de.dhbw.activities;

public enum UIActions {
    moveImage(1),
    rotateImage(2),
    setImageResource(3),
    addView(4),
    removeView(5);

    int id;

    private UIActions(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
