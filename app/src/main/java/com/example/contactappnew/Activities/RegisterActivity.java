package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText register_EDT_firstName
                             ,register_EDT_lastName
                             ,register_EDT_email
                             ,register_EDT_password;
    private MaterialButton register_BTN_register, register_BTN_signIn;
    private UserEntity userEntity;
    private String userFirstName, userLastName, userEmail, userPassword;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_account);
        findViews();
        register_BTN_register.setOnClickListener(view -> {
            userFirstName = register_EDT_firstName.getText().toString();
            userLastName = register_EDT_lastName.getText().toString();
            userEmail = register_EDT_email.getText().toString();
            userPassword = register_EDT_password.getText().toString();
            userEntity = new UserEntity(userFirstName, userLastName, userEmail, userPassword);
            Repository.getMe().getUserDao().insertUser(userEntity);
        });
        register_BTN_signIn.setOnClickListener(view -> {
            intent = new Intent(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    private void findViews() {
        register_EDT_firstName = findViewById(R.id.register_EDT_firstName);
        register_EDT_lastName = findViewById(R.id.register_EDT_lastName);
        register_EDT_email = findViewById(R.id.register_EDT_email);
        register_EDT_password = findViewById(R.id.register_EDT_password);
        register_BTN_register = findViewById(R.id.register_BTN_register);
        register_BTN_signIn = findViewById(R.id.register_BTN_signIn);
    }
}
/*
UserEntity userId = Repository.getMe().getUserDao().getUser(userEmail, userPassword);
            Repository.getMe().getContactDao().insertContact(new ContactEntity("0548192255", "alonContact", "alonLAST", "male", "contact@gmail", userId.getId()));
            ContactEntity contact = Repository.getMe().getContactDao().getContactByEmail("contact@gmail");
            Log.d("tagg", "User name: " + userId.getFirstName() + " ID: " + userId.getId());
            Log.d("tagg", "contact name: " + contact.getFirstName() + " ID: " + contact.getId() + " userContact ID: " + contact.getUserID());
 */