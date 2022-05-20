package com.example.contactappnew;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GenderApi {
    @GET(".")
    Call<UserGender> getGender(@Query("name") String name);
}
