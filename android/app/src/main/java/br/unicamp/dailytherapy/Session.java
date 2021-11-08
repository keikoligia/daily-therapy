package br.unicamp.dailytherapy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUseName(String nome) {
        prefs.edit().putString("nome", nome).commit();
    }

    public String getusename()
    {
        String usename = prefs.getString("usename","");
        return usename;
    }

    public void setKey(String key) {prefs.edit().putString("key", key).commit(); }

    public String getKey()
    {
        String key = prefs.getString("key", "");
        return key;
    }

}