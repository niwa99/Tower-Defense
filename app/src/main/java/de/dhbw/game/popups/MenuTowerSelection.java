package de.dhbw.game.popups;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import de.dhbw.R;

public class MenuTowerSelection extends AMenu {
    private LinearLayout towerSelectionLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addViewToPopUp((LinearLayout) getLayoutInflater().inflate(R.layout.menu_choose_tower, null));
        addViewToPopUp((LinearLayout) getLayoutInflater().inflate(R.layout.menu_choose_tower, null));
        addViewToPopUp((LinearLayout) getLayoutInflater().inflate(R.layout.menu_choose_tower, null));
    }
}
