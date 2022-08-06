package com.karimi.seller.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences ExtrasPref;
    private SharedPreferences.Editor extraEditor;

    private static final String PREF_EXTRAS = "SSP";

    Session() {
        ExtrasPref = App.context.getSharedPreferences(PREF_EXTRAS, Context.MODE_PRIVATE);
        extraEditor = ExtrasPref.edit();
    }

    public static Session getInstance() {
        return new Session();
    }

    public void setSessionKey(String key) {
        extraEditor.putString(Config.SESSION_KEY, key);
        extraEditor.commit();
    }

    public String getSessionKey() {
        return ExtrasPref.getString(Config.SESSION_KEY, null);
    }

}
