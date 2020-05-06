package de.dhbw.game.popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.dhbw.R;

public abstract class AMenu extends Activity {
    private LinearLayout mainLayout;
    private LinearLayout menuLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.amenu_layout, null);
        menuLayout = mainLayout.findViewById(R.id.menu_layout);

        setContentView(mainLayout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(1250, 550);
    }

    public void addViewToPopUp(View child) {
        menuLayout.addView(child);
    }

    public void closeWindow(View view) {
        finish();
    }

    public void setHeader(String header) {
        ((TextView) mainLayout.findViewById(R.id.header)).setText(header);
    }

    protected LinearLayout getMenuLayout() {
        return menuLayout;
    }
}