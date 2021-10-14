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
    @GET("/api/dog/get")
    Call<List<Dog>> getDog();

    @DELETE("/api/dog/delete/{id}")
    Call<Dog> excluirDog(@Path("id") String id);

    @POST("/api/dog/post")
    Call<Dog> incluirDog(@Body Dog dog);

    @PUT("/api/dog/put/{id}")
    Call<Dog> alterarDog(@Path("id") String id, @Body Dog dog);
}
