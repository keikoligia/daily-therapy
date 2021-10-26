package br.unicamp.dailytherapy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface Service
{
    @GET("/usuarios")
    Call<List<Usuario>> getUsuario();

    @DELETE("/usuario/delete/{id}")
    Call<Usuario> excluirUsuario(@Path("id") String id);

    @POST("/usuario")
    Call<Usuario> getUsuarioNome(@Body Usuario usuario);

    @POST("/usuario")
    Call<Usuario> incluirUsuario(@Body Usuario usuario);
}
