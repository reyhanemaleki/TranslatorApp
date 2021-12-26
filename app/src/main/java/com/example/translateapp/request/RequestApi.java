package com.example.translateapp.request;

import com.example.translateapp.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestApi {
    @GET("v3/search")
    Call<Model> getTranslation(@Query("token") String token , @Query("q")  String q, @Query("type") String type , @Query("filter") String filter);
}
