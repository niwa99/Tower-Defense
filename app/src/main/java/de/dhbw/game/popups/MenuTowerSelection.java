package de.dhbw.game.popups;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import de.dhbw.R;
import de.dhbw.game.Game;
import de.dhbw.game.IMoneyListener;
import de.dhbw.map.objects.tower.TowerType;
import de.dhbw.util.Position;


public class MenuTowerSelection extends AMenu implements IMoneyListener {

    private Position position;
    public static Game game;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader("Build Tower");
        position = (Position) getIntent().getSerializableExtra(getString(R.string.position));
        for(TowerType type: TowerType.values()) {
            TableLayout layout = (TableLayout) getLayoutInflater().inflate(R.layout.menu_tower_selection_element, null);
            TextView towerName = layout.findViewById(R.id.towerName);
            ImageView towerImage = layout.findViewById(R.id.towerImage);
            TextView towerPrice = layout.findViewById(R.id.towerPrice);
            layout.findViewById(R.id.linearLayoutTower).setTag(type);
            towerName.setText(type.getType());
            towerImage.setImageDrawable(getDrawable(type.getDrawable()));
            towerPrice.setText(String.valueOf(type.getPrice()));
            towerPrice.setId(type.getPrice());
            addViewToPopUp(layout);
        }

        game.setMenu(this);
        performMoneyUpdate(game.getMoney());
      }


    public void buildTower(View view) {
        if (((TowerType) view.getTag()).getPrice() <= game.getMoney()) {
            game.buildTower((TowerType) view.getTag(), position);
            finish();
        }
    }

    @Override
    public void performMoneyUpdate(int money) {
        for(int i = 0; i < getMenuLayout().getChildCount(); i++){
            LinearLayout linearLayout = getMenuLayout().getChildAt(i).findViewById(R.id.linearLayoutTower);
            int price = Integer.parseInt(((TextView)linearLayout.getChildAt(0)).getText().toString());
            if (price > money) {
                runOnUiThread(() -> linearLayout.setBackgroundColor(getColor(R.color.red)));
            } else {
                runOnUiThread(() -> linearLayout.setBackgroundColor(getColor(R.color.green)));
            }
        }
    }

    @Override
    public void closeWindow(View view) {
        game.setMenu(null);
        super.closeWindow(view);
    }
}