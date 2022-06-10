package com.example.contactappnew.Repository;

import com.example.contactappnew.CallBack_CreateGenderFotContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenderGenerator{

    private Call<UserGender> call;
    private GenderApi api;
    public static GenderGenerator me;
    private String gender;
    private CallBack_CreateGenderFotContact callBack_createGenderFotContact = null;

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

    public void setCallBack_CreateGenderFotContact(CallBack_CreateGenderFotContact callBack_createGenderFotContact){
        this.callBack_createGenderFotContact = callBack_createGenderFotContact;
    }

    public void getGenderForName(String name) {
        call = api.getGender(name);
        call.enqueue(new Callback<UserGender>() {
            @Override
            public void onResponse(Call<UserGender> call, Response<UserGender> response) {
                if (response.isSuccessful()) {
                    gender = response.body().getGender();
                    callBack_createGenderFotContact.genderGeneratedForContact(gender);
                }
            }

            @Override
            public void onFailure(Call<UserGender> call, Throwable t) {
            }
        });
    }
}
