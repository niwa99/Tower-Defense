package de.dhbw.util;

import android.widget.LinearLayout;

import de.dhbw.R;

public class Constants {
    //Settings
    public static final String STATUS_ON = "on";
    public static final String STATUS_OFF = "off";

    //Drawables
    public static final int DRAWABLE_BULLET = R.drawable.bullet;
    public static final int DRAWABLE_TANK = R.drawable.tank;
    public static final int DRAWABLE_TANK_HITTED = R.drawable.tank_hitted;
    public static final int DRAWABLE_TOWER = R.drawable.tower;
    public static final int DRAWABLE_FIELD_TRANSPARENT = R.drawable.transparent_background;
    public static final int DRAWABLE_FIELD_ON_CLICK_PLUS = R.drawable.button_plus_background;
    public static final int DRAWABLE_PATH_HORIZONTAL = R.drawable.path_horizontal;
    public static final int DRAWABLE_PATH_VERTICAL = R.drawable.path_vertical;
    public static final int DRAWABLE_PATH_LEFT_UP= R.drawable.path_left_up;
    public static final int DRAWABLE_PATH_LEFT_DOWN = R.drawable.path_left_down;
    public static final int DRAWABLE_PATH_RIGHT_DOWN = R.drawable.path_right_down;
    public static final int DRAWABLE_PATH_RIGHT_UP = R.drawable.path_right_up;

    //Map Parameters
    public static final int FIELD_SIZE = 150;
    public static final int AMOUNT_ROWS = 6;
    public static final int AMOUNT_COLUMNS = 11;


    //Def-Tower Parameters
    public static final int DEF_TOWER_LEVEL_1_COSTS = 10;
    public static final int DEF_TOWER_LEVEL_1_DAMAGE = 1;
    public static final int DEF_TOWER_LEVEL_1_RANGE_IN_PIXELS = 450;
    public static final int DEF_TOWER_LEVEL_1_FIRERATE_IN_SECONDS = 3;

    public static final LinearLayout.LayoutParams DEF_TOWER_LEVEL_1_TOWER_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);


    //Tank Parameters
    public static final int TANK_LEVEL_1_HEALTHPOINTS = 3;
    public static final int TANK_LEVEL_1_SPEED = 975; //From 0 to 1000
    public static final int TANK_LEVEL_1_VALUE = 5;
    public static final int TANK_LEVEL_1_LIFE_POINT_COSTS = 5;


    public static final int TANK_LEVEL_2_HEALTHPOINTS = 7;
    public static final int TANK_LEVEL_2_SPEED = 980;
    public static final int TANK_LEVEL_2_VALUE = 10;
    public static final int TANK_LEVEL_2_LIFE_POINT_COSTS = 5;

    public static final int TANK_LEVEL_3_HEALTHPOINTS = 25;
    public static final int TANK_LEVEL_3_SPEED = 985;
    public static final int TANK_LEVEL_3_VALUE = 25;
    public static final int TANK_LEVEL_3_LIFE_POINT_COSTS = 5;

    public static final LinearLayout.LayoutParams TANK_ENEMY_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);


    //Bullet Parameters
    public static final int BULLET_SPEED = 995; //From 0 to 1000

    public static final LinearLayout.LayoutParams BULLET_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE/4, FIELD_SIZE/4);
}
