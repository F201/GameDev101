package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Fathurrohman on 2/9/2015.
 */
public class SaveGame {

    public static Preferences preferences;

    public static String name = "player1";

    public static String valScore = "scorePlayer1";
    public static String valScore2 = "scorePlayer2";

    private static Preferences getPreference() {
        if(preferences == null) {
            preferences = Gdx.app.getPreferences(name);
        }
        return preferences;
    }

    public static void setScorePlayer(String playerName,int scorePlayer) {
        getPreference().putInteger(playerName, scorePlayer);
        getPreference().flush();
    }

    public static int getScorePlayer() {
        return getPreference().getInteger(valScore, 0);
    }

    public static void clearData(){
        getPreference().clear();
        getPreference().flush();
    }
}