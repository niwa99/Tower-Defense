package de.dhbw.util;

import android.content.Context;
import android.widget.FrameLayout;

import de.dhbw.activities.GameActivity;
import de.dhbw.game.Game;
import de.dhbw.map.matchfield.MatchField;
import de.dhbw.map.structure.MapStructure;

public class ObjectStorage {
    private static GameActivity gameActivity;
    private static Context context;
    private static Game game;
    private static FrameLayout mapLayout;
    private static MapStructure mapStructure;
    private static MatchField matchField;


    public static GameActivity getGameActivity() {
        return gameActivity;
    }
    public static void setGameActivity(GameActivity gameActivity) {
        ObjectStorage.gameActivity = gameActivity;
    }

    public static Context getContext() {
        return context;
    }
    public static void setContext(Context context) {
        ObjectStorage.context = context;
    }

    public static FrameLayout getMapLayout() {
        return mapLayout;
    }
    public static void setMapLayout(FrameLayout mapLayout) {
        ObjectStorage.mapLayout = mapLayout;
    }

    public static MapStructure getMapStructure() {
        return mapStructure;
    }
    public static void setMapStructure(MapStructure mapStructure) {
        ObjectStorage.mapStructure = mapStructure;
    }

    public static MatchField getMatchField() {
        return matchField;
    }
    public static void setMatchField(MatchField matchField) {
        ObjectStorage.matchField = matchField;
    }

    public static Game getGame(){
        return game;
    }
    public static void setGame(Game game){
        ObjectStorage.game=game;
    }

    public static void clear(){
        ObjectStorage.game=null;
        ObjectStorage.mapStructure=null;
        ObjectStorage.matchField=null;
    }
}
