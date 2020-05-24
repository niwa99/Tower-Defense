package de.dhbw.util;

import android.widget.LinearLayout;

import de.dhbw.R;

public class Constants {
    //Settings
    public static final String STATUS_ON = "on";
    public static final String STATUS_OFF = "off";

    //Drawables
    public static final int DRAWABLE_BULLET = R.drawable.bullet;
    public static final int DRAWABLE_BULLET_BOOMBASTIC = R.drawable.tower_boombastic_bullet;
    public static final int DRAWABLE_BULLET_FREEZER = R.drawable.tower_freezer_bullet;
    public static final int DRAWABLE_TANK = R.drawable.tank;
    public static final int DRAWABLE_TANK_HITTED = R.drawable.tank_hitted;
    public static final int DRAWABLE_TOWER_ARTILLERY = R.drawable.tower_artillery;
    public static final int DRAWABLE_TOWER_BOOMBASTIC = R.drawable.tower_boombastic_complete;
    public static final int DRAWABLE_TOWER_BOOMBASTIC_BASE = R.drawable.tower_boombastic_base;
    public static final int DRAWABLE_TOWER_BOOMBASTIC_HEAD = R.drawable.tower_boombastic_head;
    public static final int DRAWABLE_TOWER_FREEZER_BASE = R.drawable.tower_freezer_idle;
    public static final int DRAWABLE_TOWER_FREEZER_HEAD = R.drawable.tower_freezer_shoot;
    public static final int DRAWABLE_FIELD_TRANSPARENT = R.drawable.transparent_background;
    public static final int DRAWABLE_FIELD_ON_CLICK_PLUS = R.drawable.button_plus_background;
    public static final int DRAWABLE_PATH_HORIZONTAL = R.drawable.path_horizontal;
    public static final int DRAWABLE_PATH_VERTICAL = R.drawable.path_vertical;
    public static final int DRAWABLE_PATH_LEFT_UP= R.drawable.path_left_up;
    public static final int DRAWABLE_PATH_LEFT_DOWN = R.drawable.path_left_down;
    public static final int DRAWABLE_PATH_RIGHT_DOWN = R.drawable.path_right_down;
    public static final int DRAWABLE_PATH_RIGHT_UP = R.drawable.path_right_up;

    //Icons
    public static final int ICON_SOUND_ON = R.drawable.icon_sound_on;
    public static final int ICON_SOUND_OFF = R.drawable.icon_sound_off;
    public static final int ICON_MUSIC_ON = R.drawable.icon_music_on;
    public static final int ICON_MUSIC_OFF = R.drawable.icon_music_off;
    public static final int ICON_ANIMATION_ON = R.drawable.icon_animations_on;
    public static final int ICON_ANIMATION_OFF = R.drawable.icon_animations_off;


    //Map Parameters
    public static final int FIELD_SIZE = 150;
    public static final int AMOUNT_ROWS = 6;
    public static final int AMOUNT_COLUMNS = 11;


    //TowerArtillery Parameters
    public static final int TOWER_ARTILLERY_LEVEL_1_COSTS = 10;
    public static final int TOWER_ARTILLERY_LEVEL_1_DAMAGE = 1;
    public static final int TOWER_ARTILLERY_LEVEL_1_RANGE_IN_PIXELS = 450;
    public static final int TOWER_ARTILLERY_LEVEL_1_FIRERATE_IN_SECONDS = 3;

    public static final int TOWER_ARTILLERY_LEVEL_2_COSTS = 25;
    public static final int TOWER_ARTILLERY_LEVEL_2_DAMAGE = 2;
    public static final int TOWER_ARTILLERY_LEVEL_2_RANGE_IN_PIXELS = 460;
    public static final int TOWER_ARTILLERY_LEVEL_2_FIRERATE_IN_SECONDS = 2;

    public static final int TOWER_ARTILLERY_LEVEL_3_COSTS = 50;
    public static final int TOWER_ARTILLERY_LEVEL_3_DAMAGE = 3;
    public static final int TOWER_ARTILLERY_LEVEL_3_RANGE_IN_PIXELS = 500;
    public static final int TOWER_ARTILLERY_LEVEL_3_FIRERATE_IN_SECONDS = 1;
    public static final LinearLayout.LayoutParams TOWER_ARTILLERY_LEVEL_1_TOWER_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);

    //TowerBoombastic Parameters
    public static final int TOWER_BOOMBASTIC_LEVEL_1_COSTS = 50;
    public static final int TOWER_BOOMBASTIC_LEVEL_1_DAMAGE = 5;
    public static final int TOWER_BOOMBASTIC_LEVEL_1_RANGE_IN_PIXELS = 600;
    public static final int TOWER_BOOMBASTIC_LEVEL_1_FIRERATE_IN_SECONDS = 5;
    public static final LinearLayout.LayoutParams TOWER_BOOMBASTIC_LEVEL_1_TOWER_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);

    //TowerFreezer Parameters
    public static final int TOWER_FREEZER_LEVEL_1_COSTS = 50;
    public static final int TOWER_FREEZER_LEVEL_1_DAMAGE = 1;
    public static final int TOWER_FREEZER_LEVEL_1_RANGE_IN_PIXELS = 600;
    public static final int TOWER_FREEZER_LEVEL_1_FIRERATE_IN_SECONDS = 5;
    public static final int TOWER_FREEZER_LEVEL_1_SLOWNESS = 75;
    public static final LinearLayout.LayoutParams TOWER_FREEZER_LEVEL_1_TOWER_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE, FIELD_SIZE);

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
    public static final int BULLET_SPEED = 2; //if the number is low, the bullet will be quick
    public static final int BOMB_RANGE = 100;
    public static final LinearLayout.LayoutParams BULLET_SIZE_PARAMS = new LinearLayout.LayoutParams(FIELD_SIZE/4, FIELD_SIZE/4);
}
