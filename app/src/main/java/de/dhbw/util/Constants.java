package de.dhbw.util;

import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import de.dhbw.R;

public class Constants {
    //Drawables
    public static final int DRAWABLE_BULLET = R.drawable.bullet;
    public static final int DRAWABLE_TANK = R.drawable.tank;
    public static final int DRAWABLE_TANK_HITTED = R.drawable.tank_hitted;
    public static final int DRAWABLE_TOWER = R.drawable.tower;
    public static final int DRAWABLE_FIELD_TRANSPARENT = R.drawable.transparent_background;
    public static final int DRAWABLE_FIELD_ON_CLICK_PLUS = R.drawable.button_plus_background;


    //Map Parameters
    public static final int FIELD_SIZE = 150;
    public static final int AMOUNT_ROWS = 6;
    public static final int AMOUNT_COLUMNS = 11;


    //Def-Tower Parameters
    public static final int DEF_TOWER_LEVEL_1_COSTS = 100;
    public static final int DEF_TOWER_LEVEL_1_DAMAGE = 1;
    public static final int DEF_TOWER_LEVEL_1_RANGE_IN_PIXELS = 300;
    public static final int DEF_TOWER_LEVEL_1_FIRERATE_IN_SECONDS = 3;

    public static final LinearLayout.LayoutParams DEF_TOWER_LEVEL_1_TOWER_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);


    //Tank Parameters
    public static final int TANK_LEVEL_1_HEALTHPOINTS = 7;
    public static final int TANK_LEVEL_1_SPEED = 975; //From 0 to 1000

    public static final int TANK_LEVEL_2_HEALTHPOINTS = 7;
    public static final int TANK_LEVEL_2_SPEED = 980;

    public static final LinearLayout.LayoutParams TANK_ENEMY_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);


    //Bullet Parameters
    public static final int BULLET_SPEED = 995; //From 0 to 1000

    public static final LinearLayout.LayoutParams BULLET_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE/4, FIELD_SIZE/4);
}
