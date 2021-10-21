package br.unicamp.dailytherapy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable
{
    //@SerializedName("id")
    //private String id;
    @SerializedName("nome")
    private String nome;
    @SerializedName("contato")
    private String contato;
    @SerializedName("nomeCtt")
    private String nomeCtt;
    @SerializedName("email")
    private String email;
    @SerializedName("senha")
    private String senha;

    public Usuario() { }

    public Usuario(Usuario modelo) throws Exception{
        this.nome = modelo.nome;
        this.contato = modelo.contato;
        this.nomeCtt = modelo.nomeCtt;
        this.email = modelo.email;
        this.senha = modelo.senha;
    }

    public Usuario(String nome, String contato, String nomeCtt, String email, String senha)
    {
        this.nome = nome;
        this.contato = contato;
        this.nomeCtt = nomeCtt;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getContato() { return contato; }

    public void setContato(String contato) { this.contato = contato; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }
}
