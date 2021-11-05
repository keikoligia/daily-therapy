package br.unicamp.dailytherapy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface Service
{
    //usuario
    @GET("/usuario/login/{nome}")
    Call<Usuario> getUsuarioNome(@Path("nome") String nome);

    @POST("/usuario")
    Call<Usuario> incluirUsuario(@Body Usuario usuario);

    //medicamento
    @POST("/usuario/remedio")
    Call<Remedio> incluirMedicamento(@Body Remedio remedio);

    //resumo
    @POST("/usuario/remedio")
    Call<Remedio> gerarPdf(@Body Resumo resumo);

    @POST("/usuario/remedio/get")
    Call<Remedio> mostrarRemedio(@Body Remedio remedio);
}
