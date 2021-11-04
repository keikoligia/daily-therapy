package br.unicamp.dailytherapy;

import com.google.gson.annotations.SerializedName;

public class Resumo
{
    @SerializedName("conteudoPdf")
    private String conteduoPdf;
    @SerializedName("idUsuario")
    private String idUsuario;
    @SerializedName("idRemedio")
    private String idRemedio;

    public String getConteduoPdf() {
        return conteduoPdf;
    }

    public void setConteduoPdf(String conteduoPdf) {
        this.conteduoPdf = conteduoPdf;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdRemedio() {
        return idRemedio;
    }

    public void setIdRemedio(String idRemedio) {
        this.idRemedio = idRemedio;
    }



}
