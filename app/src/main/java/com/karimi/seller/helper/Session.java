package com.karimi.seller.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.karimi.seller.model.Business;

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


    public void setBranch(int branch) {
        extraEditor.putInt(Config.BUSINESS_ID, branch);
        extraEditor.commit();
    }
    public int getBranch() {
        return ExtrasPref.getInt(Config.BUSINESS_ID, -1);
    }


    public void setBusiness(Business business) {
        extraEditor.putString(Config.BUSINESS_OWNER_NAME, business.getOwner_name());
        extraEditor.putString(Config.BUSINESS_NAME, business.getBusiness_name());
        extraEditor.putInt(Config.BUSINESS_ID, business.getId());
        extraEditor.commit();
    }

    public void setBusiness(String name, String businessName, int businessID) {
        extraEditor.putString(Config.BUSINESS_OWNER_NAME, name);
        extraEditor.putString(Config.BUSINESS_NAME, businessName);
        extraEditor.putInt(Config.BUSINESS_ID, businessID);
        extraEditor.commit();
    }

}
