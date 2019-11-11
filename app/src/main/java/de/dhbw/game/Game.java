package de.dhbw.game;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.objects.enemy.Tank;
import de.dhbw.map.objects.tower.DefTower;
import de.dhbw.map.structure.FieldDescription;
import de.dhbw.map.structure.MapStructure;
import de.dhbw.util.Position;

public class Game {
	public Game() {
		
	}

	/**
	 * this method starts the game
	 *
	 */
	public static void main(String[] args) {
	/*
		MapStructure map = new MapStructure();
		Tank tank1 = new Tank(null,"Tank1",5, 950);
		Tank tank2 = new Tank(null,"Tank2",9, 950);
		DefTower tower = new DefTower("tower1",1, map.getSizeField()*2, 2, map.getField(new Position(2, 3)).getSpawnPoint());
		DefTower tower2 = new DefTower("tower2",2, map.getSizeField()*3, 5, map.getField(new Position(6, 1)).getSpawnPoint());
		
		
		MatchField matchField = new MatchField(map);
		matchField.addEnemy(tank1);
		matchField.addEnemy(tank2);
		matchField.addTower(tower);
		matchField.addTower(tower2);
		
		matchField.moveEnemies();
		matchField.fireTowers();
		*/
	}

	public static void runGame(Context context, int tankImage, int towerImage, int bulletImage, FrameLayout layout) {
        MapStructure map = new MapStructure();
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(map.getSizeField(), map.getSizeField());
        List<Button> btns = new ArrayList<Button>();
        map.getFields().stream().forEach(f -> {
            Button btn = new Button(context);
            btn.setX(f.getSpawnPoint().getX());
            btn.setY(f.getSpawnPoint().getY());
            btn.setLayoutParams(btnParams);
            if (f.getFieldDescription().equals(FieldDescription.PATH)) {
                btn.setEnabled(false);
                btn.setBackgroundColor(Color.GREEN);
                layout.addView(btn);
                btns.add(btn);
            } else if (f.getFieldDescription().equals(FieldDescription.FREE)) {
                //btn.setBackgroundColor(Color.GRAY);
                //layout.addView(btn);
                //btns.add(btn);
            }
        });


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        ImageView imageTank1 = new ImageView(context);
        ImageView imageTank2 = new ImageView(context);
        imageTank1.setLayoutParams(params);
        imageTank2.setLayoutParams(params);
        imageTank1.setImageResource(tankImage);
        imageTank2.setImageResource(tankImage);

        ImageView imageTower1 = new ImageView(context);
        ImageView imageTower2 = new ImageView(context);
        imageTower1.setLayoutParams(params);
        imageTower2.setLayoutParams(params);
        imageTower1.setImageResource(towerImage);
        imageTower2.setImageResource(towerImage);

        layout.addView(imageTank1);
        layout.addView(imageTank2);
        layout.addView(imageTower1);
        layout.addView(imageTower2);

        Tank tank1 = new Tank(imageTank1, "Tank1", 5, 950);
        Tank tank2 = new Tank(imageTank2, "Tank2", 9, 975);

        LinearLayout.LayoutParams paramsBullet = new LinearLayout.LayoutParams(50, 50);
        ImageView bulletImage1 = new ImageView(context);
        bulletImage1.setLayoutParams(paramsBullet);
        bulletImage1.setImageResource(bulletImage);
        layout.addView(bulletImage1);

        ImageView bulletImage2 = new ImageView(context);
        bulletImage2.setLayoutParams(paramsBullet);
        bulletImage2.setImageResource(bulletImage);
        layout.addView(bulletImage2);

        DefTower tower = new DefTower(imageTower1, bulletImage1, "tower1", 1, map.getSizeField() * 2, 2, map.getField(new Position(2, 3)).getSpawnPoint());
        DefTower tower2 = new DefTower(imageTower2, bulletImage2, "tower2", 1, map.getSizeField() * 3, 5, map.getField(new Position(6, 1)).getSpawnPoint());


        MatchField matchField = new MatchField(map);
        matchField.addEnemy(tank1);
        matchField.addEnemy(tank2);
        matchField.addTower(tower);
        matchField.addTower(tower2);

        matchField.moveEnemies();
        matchField.fireTowers();
    }



}
