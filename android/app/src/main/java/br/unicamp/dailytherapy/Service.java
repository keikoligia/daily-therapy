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

    @DELETE("/usuario/delete/:id")
    Call<Usuario> excluirUsuario(@Path("id") String id);

    @POST("/api/dog/post")
    Call<Usuario> incluirUsuario(@Body Usuario usuario);

    @PUT("/usuario/:id")
    Call<Usuario> alterarUsuario(@Path("id") String id, @Body Usuario usuario);
}
