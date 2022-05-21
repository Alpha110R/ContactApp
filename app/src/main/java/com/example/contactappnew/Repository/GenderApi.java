package com.example.contactappnew.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenderApi {
    @GET(".")
    Call<UserGender> getGender(@Query("name") String name);
}
