package com.example.contactappnew.Repository;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenderGenerator{
    public static Call<UserGender> call;
    public static GenderApi api;
    public static GenderGenerator me;
    public static UserGender userGender;

    public GenderGenerator(){
         api=new Retrofit.Builder()
                .baseUrl("https://api.genderize.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GenderApi.class);
    }
    public static void initHelper() {
        if (me == null) {
            me = new GenderGenerator();
        }
    }
    public static GenderGenerator getMe() { return me; }

    public String getGenderForName(String name) {
        call = api.getGender(name);
        call.enqueue(new Callback<UserGender>() {
            @Override
            public void onResponse(Call<UserGender> call, Response<UserGender> response) {
                if (response.isSuccessful()) {
                    userGender = response.body();
                    Log.d("tagg", "response: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UserGender> call, Throwable t) {
            }
        });
        return userGender.getGender();
    }
}


/*

Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.genderize.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GenderApi.class);
   GenderApi api = retrofit.create(GenderApi.class);
       Call<UserGender> call = api.getGender(name);
        call.enqueue(new Callback<UserGender>() {
            @Override
            public void onResponse(Call<UserGender> call, Response<UserGender> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<UserGender> call, Throwable t) {
            }
        });
 */