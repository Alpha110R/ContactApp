package com.example.contactappnew.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.contactappnew.Utils.MySignal;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Enums;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText signIn_EDT_email, signIn_EDT_password;
    private MaterialButton signIn_BTN_signIn, signIn_BTN_register;
    private String userMail, userPass;
    private UserEntity userEntity;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_in);
        findViews();
        initializeIntentBundleAndUser();

        signIn_BTN_signIn.setOnClickListener(view -> {
            userMail = signIn_EDT_email.getText().toString();
            userPass = signIn_EDT_password.getText().toString();
            userEntity = Repository.getMe().getUserDao().getUser(userMail, userPass);
            if(userEntity != null)//Checks if user exist in the DB
                moveToUserContactListActivity();
            else//User doesn't exist in the DB
                toastMessagesAccordingError();
        });

        signIn_BTN_register.setOnClickListener(view -> {
            intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        signIn_EDT_email = findViewById(R.id.signIn_EDT_email);
        signIn_EDT_password = findViewById(R.id.signIn_EDT_password);
        signIn_BTN_signIn = findViewById(R.id.signIn_BTN_signIn);
        signIn_BTN_register = findViewById(R.id.signIn_BTN_register);
    }
    public void initializeIntentBundleAndUser(){
        intent = getIntent();
        if(intent.getBundleExtra(Enums.BUNDLE.toString()) != null) {
            bundle = intent.getBundleExtra(Enums.BUNDLE.toString());
            userEntity = Repository.getMe().getUserDao().getUserByID(bundle.getInt(Enums.USERID.toString()));
            signIn_EDT_email.setText(userEntity.getEmail());
            signIn_EDT_password.setText(userEntity.getPassword());
        }
        else
            bundle = new Bundle();
    }

    public void moveToUserContactListActivity(){
        intent = new Intent(SignInActivity.this, UserContactListActivity.class);
        bundle.putInt(Enums.USERID.toString(), userEntity.getId());
        intent.putExtra(Enums.BUNDLE.toString(), bundle);
        startActivity(intent);
        finish();
    }

    public void toastMessagesAccordingError(){
        if(Repository.getMe().getUserDao().getUserByEmail(userMail) != null){
            signIn_EDT_password.setBackgroundColor(Color.parseColor("#4DFF6666"));
            signIn_EDT_email.setBackgroundColor(Color.TRANSPARENT);
            MySignal.getMe().makeToastMessage("Password incorrect");
        }
        else {
            signIn_EDT_email.setBackgroundColor(Color.parseColor("#4DFF6666"));
            signIn_EDT_password.setBackgroundColor(Color.parseColor("#4DFF6666"));
            MySignal.getMe().makeToastMessage("User doesn't exist. Go to Registration");
        }
        MySignal.getMe().vibrate();
    }
}