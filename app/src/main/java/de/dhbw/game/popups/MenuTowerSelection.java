package de.dhbw.game.popups;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import de.dhbw.R;
import de.dhbw.map.objects.tower.TowerType;


public class MenuTowerSelection extends AMenu {
    private LinearLayout towerSelectionLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(TowerType type: TowerType.values()){
            TableLayout layout = (TableLayout) getLayoutInflater().inflate(R.layout.menu_choose_tower, null);
            TextView towerName = layout.findViewById(R.id.towerName);
            ImageView towerImage = layout.findViewById(R.id.towerImage);
            TextView towerPrice = layout.findViewById(R.id.towerPrice);
            towerName.setText(type.getType());
            towerImage.setImageDrawable(getDrawable(type.getDrawable()));
            towerPrice.setText(String.valueOf(type.getPrice()));
            addViewToPopUp(layout);
        }
    }
}
