package com.example.contactappnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView txt;
    private MaterialButton button;
    private UserDatabase database;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mail1 = "alonr@gmail.com";
        String firstName1 = "alonn";
        String lastName1 = "Ronder";
        String mail2 = "aa@gmail.com";
        String pass1 = "124578";
        String pass2 = "alonpass";



        userDao = Room.databaseBuilder(this, UserDatabase.class, "werDB.db")
                .allowMainThreadQueries()
                .build().getUserDao();
        UserEntity user = new UserEntity(firstName1, lastName1, mail2, pass2);
        //userDao.insertUser(user);
        //userDao.insertUser(new UserEntity("TIMOR", "LAST", "asd@", "password"));
        List<UserEntity> userTemp;
        userTemp = userDao.getUsers();





        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.genderize.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GenderApi api=retrofit.create(GenderApi.class);
        Call<UserGender> call=api.getGender("alon");
        call.enqueue(new Callback<UserGender>() {
            @Override
            public void onResponse(Call<UserGender> call, Response<UserGender> response) {
                if(response.isSuccessful()) {
                    //txt.setText(response.body().getGender());
                }
            }

            @Override
            public void onFailure(Call<UserGender> call, Throwable t) {
            }
        });



    }


}