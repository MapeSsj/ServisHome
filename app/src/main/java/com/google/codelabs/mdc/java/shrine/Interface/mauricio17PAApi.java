package com.google.codelabs.mdc.java.shrine.Interface;

import com.google.codelabs.mdc.java.shrine.Model.ReqAuth;
import com.google.codelabs.mdc.java.shrine.Model.ReqGuardarUsuario;
import com.google.codelabs.mdc.java.shrine.Model.RptaAuth;
import com.google.codelabs.mdc.java.shrine.Model.RptaGeneral;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface mauricio17PAApi {
    @GET("api/usuarios/obtener")
    Call<List<List<Object>>> obtenerUsuarios();

    @POST("api/usuarios/login")
    Call<RptaAuth> getJWT(@Body ReqAuth reqAuth);

    //api_guardarUsuario
    @POST("api/usuarios/registrar")
    Call<RptaGeneral> saveUser(@Body ReqGuardarUsuario reqGuardarUsuario);
}
