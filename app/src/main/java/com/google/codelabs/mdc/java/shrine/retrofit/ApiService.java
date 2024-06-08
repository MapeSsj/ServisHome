package com.google.codelabs.mdc.java.shrine.retrofit;


import com.google.codelabs.mdc.java.shrine.response.GenericoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /*
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("clave") String clave);

    @GET("/ciudad/listar")
    Call<CiudadListadoResponse> listarCiudad();

    @FormUrlEncoded
    @POST("/cliente/insertar")
    Call<ClienteInsertarReponse> insertarCliente(@Field("nombre") String nombre, @Field("direccion") String direccion, @Field("email") String email, @Field("ciudad_id") String ciudadId);


    */

    @FormUrlEncoded
    @POST("/login")
    Call<GenericoResponse> login(@Field("usuario") String usuario, @Field("clave") String clave);


}
