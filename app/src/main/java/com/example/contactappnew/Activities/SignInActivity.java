package com.example.contactappnew.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.example.contactappnew.Repository.UserDao;
import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Enums;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText signIn_EDT_email, signIn_EDT_password;
    private MaterialButton signIn_BTN_signIn, signIn_BTN_register;
    private String userMail, userPass;
    private UserDao userDao;
    private UserEntity userEntity;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_in);
        findViews();
        userDao = Repository.getMe().getUserDao();
        UserEntity user = new UserEntity("alon","ronder","alon@gmail","111");
        userDao.insertUser(user);
        //userDao.insertUser(new UserEntity("TIMOR", "LAST", "asd@", "password"));
        List<UserEntity> userTemp;
        userTemp = userDao.getUsers();



        signIn_BTN_signIn.setOnClickListener(view -> {
            userMail = signIn_EDT_email.getText().toString();
            userPass = signIn_EDT_password.getText().toString();
            userEntity = userDao.getUser(userMail, userPass);
            if(userEntity != null){//Checks if user exist in the DB
                intent = new Intent(SignInActivity.this, UserContactListActivity.class);
                intent.putExtra(Enums.USERID.toString(), userEntity.getId());//Sends the ID of the user, his primary key
                Log.d("tagg","SIGNIN user iD: " + userEntity.getId() + " user mail: " + userEntity.getEmail());
                startActivity(intent);
            }
            else{//User doesn't exist in the DB
                if(userDao.getUserByEmail(userMail) != null){
                    Log.d("tagg", "PASS not good");
                    //TODO: pop toast message that the password doesn't match
                }
                else {
                    Log.d("tagg", "User doesn't exist in the db");
                    //TODO: needs to pop toast to go to registration
                }
            }
        });
        signIn_BTN_register.setOnClickListener(view -> {
            intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void findViews() {
        signIn_EDT_email = findViewById(R.id.signIn_EDT_email);
        signIn_EDT_password = findViewById(R.id.signIn_EDT_password);
        signIn_BTN_signIn = findViewById(R.id.signIn_BTN_signIn);
        signIn_BTN_register = findViewById(R.id.signIn_BTN_register);
    }


}