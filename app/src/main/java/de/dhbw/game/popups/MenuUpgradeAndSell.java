package de.dhbw.game.popups;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import de.dhbw.R;
import de.dhbw.game.Game;
import de.dhbw.game.IMoneyListener;
import de.dhbw.map.objects.tower.TowerArtillery;
import de.dhbw.util.Position;


public class MenuUpgradeAndSell extends AMenu implements IMoneyListener {

    private Position pos;
    private String towerType;
    private int level;
    private int towerDamage;
    private int towerRange;
    private int towerFireRate;
    private int towerCost;
    private int upgradeCost;
    private int upgradeDamage;
    private int upgradeRange;
    private int upgradeFireRate;
    private int towerDrawable;
    private int maxLevel = 4;
    public static Game game;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = (Position) getIntent().getSerializableExtra(getString(R.string.position));
        towerDamage =  (int) getIntent().getSerializableExtra(getString(R.string.towerDamage));
        towerRange =  (int) getIntent().getSerializableExtra(getString(R.string.towerRange));
        towerFireRate =  (int) getIntent().getSerializableExtra(getString(R.string.towerFireRate));
        towerCost =  (int) getIntent().getSerializableExtra(getString(R.string.towerCost));
        towerDrawable =  (int) getIntent().getSerializableExtra(getString(R.string.towerDrawable));
        level =  (int) getIntent().getSerializableExtra(getString(R.string.towerLevel));
        towerType = (String) getIntent().getSerializableExtra(getString(R.string.towerType));
        upgradeCost =  (int) getIntent().getSerializableExtra(getString(R.string.towerUpgrCost));
        upgradeDamage =  (int) getIntent().getSerializableExtra(getString(R.string.towerUpgrDamage));
        upgradeRange =  (int) getIntent().getSerializableExtra(getString(R.string.towerUpgrRange));
        upgradeFireRate =  (int) getIntent().getSerializableExtra(getString(R.string.towerUpgrFireRate));
        addViewToPopUp(initializeView());
        performMoneyUpdate(game.getMoney());
        game.setMenu(this);

    }

    @Override
    public void performMoneyUpdate(int money) {
        Button linearLayout = findViewById(R.id.upgrade);
        if (upgradeCost <= money && level < maxLevel) {
            runOnUiThread(() -> linearLayout.setBackgroundColor(getColor(R.color.green)));
        } else {
            runOnUiThread(() -> linearLayout.setBackgroundColor(getColor(R.color.red)));
        }
    }

    public void sellTower(View view) {
        game.sellTower(pos);
        game.setMenu(null);
        super.closeWindow(view);
    }

    public void upgradeTower(View view) {
        if (level < maxLevel && upgradeCost <= game.getMoney()) {
            level += 1;
            game.setMoney(game.getMoney()-upgradeCost);
            towerDamage = upgradeDamage;
            towerRange = upgradeRange;
            towerFireRate = upgradeFireRate;
            towerCost = upgradeCost;
            int[] data = game.upgradeTower(pos, level);
            upgradeDamage = data[0];
            upgradeRange = data[1];
            upgradeFireRate = data[2];
            upgradeCost = data[3];
            removeViewFromPopUp(findViewById(R.id.upgradeAndSell));
            addViewToPopUp(initializeView());
            performMoneyUpdate(game.getMoney());
            game.setMenu(this);
        }
    }

    public LinearLayout initializeView() {

        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.menu_upgrade_and_sell, null);

        setHeader(towerType + " lvl " + level);
        TextView towerDamageLabel = layout.findViewById(R.id.damageNow);
        TextView towerRangeLabel = layout.findViewById(R.id.rangeNow);
        TextView towerFireRateLabel = layout.findViewById(R.id.fireRateNow);
        TextView refund = layout.findViewById(R.id.refundAmount);

        towerDamageLabel.setText(String.valueOf(towerDamage));
        towerRangeLabel.setText(String.valueOf(towerRange));
        towerFireRateLabel.setText(String.valueOf(towerFireRate));
        refund.setText(String.valueOf(towerCost/2));

        ImageView towerImage = layout.findViewById(R.id.towerImageNow);
        towerImage.setImageResource(towerDrawable);

        TextView towerUpgrDamage = layout.findViewById(R.id.damageAfterUpgr);
        TextView towerUpgrRange = layout.findViewById(R.id.rangeAfterUpgr);
        TextView towerUpgrFireRate = layout.findViewById(R.id.fireRateAfterUpgr);
        TextView upgrade = layout.findViewById(R.id.upgradeAmount);

        towerUpgrDamage.setText(String.valueOf(upgradeDamage));
        towerUpgrRange.setText(String.valueOf(upgradeRange));
        towerUpgrFireRate.setText(String.valueOf(upgradeFireRate));
        upgrade.setText(String.valueOf(upgradeCost));

        if (level == maxLevel) {
            Button upgradeButton = layout.findViewById(R.id.upgrade);
            upgradeButton.setText("Max Reached");
            runOnUiThread(() -> upgradeButton.setBackgroundColor(getColor(R.color.red)));
        }
        return layout;
    }
}
