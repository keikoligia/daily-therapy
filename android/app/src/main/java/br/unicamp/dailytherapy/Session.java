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
        String usename = prefs.getString("nome","");
        return usename;
    }

    public void setCttName(String nomeCtt) {
        prefs.edit().putString("nomeCtt", nomeCtt).commit();
    }

    public String getCttNome()
    {
        String nomeCtt = prefs.getString("nomeCtt","");
        return nomeCtt;
    }

    public void setSenha(String nomeCtt) {
        prefs.edit().putString("senha", nomeCtt).commit();
    }

    public String getSenha()
    {
        String senha = prefs.getString("senha","");
        return senha;
    }

    public void setEmailCtt(String emailCtt) {prefs.edit().putString("emailCtt", emailCtt).commit(); }

    public String getEmailCtt()
    {
        String emailCtt = prefs.getString("emailCtt", "");
        return emailCtt;
    }

    public void setEmail(String email) {prefs.edit().putString("email", email).commit(); }

    public String getEmail()
    {
        String email = prefs.getString("email", "");
        return email;
    }

}