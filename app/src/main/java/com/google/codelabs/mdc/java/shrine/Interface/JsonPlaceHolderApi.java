package com.google.codelabs.mdc.java.shrine.Interface;

import com.google.codelabs.mdc.java.shrine.Model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Posts>> getPosts();

}
