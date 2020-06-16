package de.dhbw.activities;

public enum UIActions {
    moveImage(1),
    rotateImage(2),
    setImageResource(3),
    addView(4),
    removeView(5),
    setForeGound(6),
    startAnimator(7),
    setText(8);

    int id;

    private UIActions(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
