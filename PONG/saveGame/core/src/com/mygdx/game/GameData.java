package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Fathurrohman on 2/9/2015.
 */
public class GameData {

    private static String PREF_NAME = "myApp";
    public static String PREF_SCORE_1 = "scoreData1";
    public static String PREF_SCORE_2 = "scoreData2";

    private static Preferences preferences;

    private static Preferences getPreferences() {
        if(preferences == null) {
            preferences = Gdx.app.getPreferences(PREF_NAME);
        }
        return preferences;
    }

    public static void setPrefScore(String player,int score) {
        getPreferences().putInteger(player, score);
        getPreferences().flush();
    }

    public static int getPrefScore(String player) {
        return getPreferences().getInteger(player, 0);
    }

    public static void clearData() {
        getPreferences().clear();
        getPreferences().flush();
    }

}
