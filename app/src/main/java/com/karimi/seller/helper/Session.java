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


    public int getBusinessID() {
        return ExtrasPref.getInt(Config.BUSINESS_ID, -1);
    }


    public String getBusinessOwnerName() {
        return ExtrasPref.getString(Config.BUSINESS_OWNER_NAME, null);
    }
    public String getBusinessName() {
        return ExtrasPref.getString(Config.BUSINESS_NAME, null);
    }



    public void setMoneyType(String moneyType) {
        extraEditor.putString(Config.MONEY_TYPE, moneyType);
        extraEditor.commit();
    }
    public String getMoneyType() {
        return ExtrasPref.getString(Config.MONEY_TYPE, Config.MONEY_TYPE_DEFAULT);
    }



    public void setMinOrder(Double price) {
        extraEditor.putString(Config.MIN_ORDER, String.valueOf(price));
        extraEditor.commit();
    }
    public Double getMinOrder() {
        return Double.parseDouble(ExtrasPref.getString(Config.MIN_ORDER, "0.0"));
    }

    public void setTaxPercent(int taxPercent) {
        extraEditor.putInt(Config.TAX_PERCENT, taxPercent);
        extraEditor.commit();
    }
    public int getTaxPercent() {
        return ExtrasPref.getInt(Config.TAX_PERCENT, 0);
    }



    public void setShippingPrice(Double shippingPrice) {
        extraEditor.putString(Config.SHIPPING_PRICE, String.valueOf(shippingPrice));
        extraEditor.commit();
    }
    public Double getShippingPrice() {
        return Double.parseDouble(ExtrasPref.getString(Config.SHIPPING_PRICE, "0.0"));
    }

    public void setFreeShippingPrice(Double price) {
        extraEditor.putString(Config.SHIPPING_FREE_PRICE, String.valueOf(price));
        extraEditor.commit();
    }
    public Double getFreeShippingPrice() {
        return Double.parseDouble(ExtrasPref.getString(Config.SHIPPING_FREE_PRICE, "0.0"));
    }



    public void setCheckBox(boolean sound_scanner, boolean money, boolean card, boolean debit, boolean discount){
        extraEditor.putBoolean(Config.CHECK_BOX_SOUND_SCANNER,sound_scanner);
        extraEditor.putBoolean(Config.CHECK_BOX_MONEY,money);
        extraEditor.putBoolean(Config.CHECK_BOX_CARD,card);
        extraEditor.putBoolean(Config.CHECK_BOX_DEBIT,debit);
        extraEditor.putBoolean(Config.CHECK_BOX_DISCOUNT,discount);
        extraEditor.commit();
    }



    public void setCheckBoxSoundScanner(boolean checkBox) {
        extraEditor.putBoolean(Config.CHECK_BOX_SOUND_SCANNER,checkBox);
        extraEditor.commit();
    }
    public boolean getCheckBoxSoundScanner() {
        return ExtrasPref.getBoolean(Config.CHECK_BOX_SOUND_SCANNER, true);
    }

}
