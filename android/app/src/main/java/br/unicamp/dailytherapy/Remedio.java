package br.unicamp.dailytherapy;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Remedio implements Serializable
{
    @SerializedName("nomeRemedio")
    private String nomeRemedio;
    @SerializedName("frequencia")
    private char frequencia;
    @SerializedName("horario")
    private String horario;
    @SerializedName("inicio")
    private String inicio;
    @SerializedName("fim")
    private String fim;



    @SerializedName("nomeUsuario")
    private String nomeUsuario;

    public Remedio() { }

    public Remedio(Remedio modelo) throws Exception
    {
        this.nomeRemedio = modelo.nomeRemedio;
        this.frequencia = modelo.frequencia;
        this.horario = modelo.horario;
        this.inicio = modelo.inicio;
        this.fim = modelo.fim;
        this.nomeUsuario = modelo.nomeUsuario;
    }

    public Remedio(String nomeRemedio, String horario, char frequencia, String inicio, String fim, String nomeUsuario)
    {
        this.nomeRemedio = nomeRemedio;
        this.horario = horario;
        this.frequencia = frequencia;
        this.inicio = inicio;
        this.fim = fim;
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public char getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(char frequencia) {
        this.frequencia = frequencia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getNomeUsuario() { return nomeUsuario; }

    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }


}
