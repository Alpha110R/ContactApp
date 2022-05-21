package com.example.contactappnew.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Enums;
import com.example.contactappnew.Utils.FieldValidation;
import com.example.contactappnew.Utils.MySignal;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    private Bundle bundle;
    private String errorMessage = "";
    private boolean validateFlag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_account);
        findViews();
        bundle = new Bundle();
        register_BTN_register.setOnClickListener(view -> {
            getTextFieldsFromUser();
            if(checksFields()) {
                CreateUserAndInsertToDB();
                MySignal.getMe().makeToastMessage("Register Completed");
                intent = new Intent(RegisterActivity.this, UserContactListActivity.class);
                intent.putExtra(Enums.BUNDLE.toString(), bundle);
                startActivity(intent);
                finish();
            }
            else {
                setPopUpValidation().show();
                MySignal.getMe().vibrate();
            }
            validateFlag=true;
        });
        register_BTN_signIn.setOnClickListener(view -> {
            intent = new Intent(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
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

    private void getTextFieldsFromUser(){
        userFirstName = register_EDT_firstName.getText().toString();
        userLastName = register_EDT_lastName.getText().toString();
        userEmail = register_EDT_email.getText().toString();
        userPassword = register_EDT_password.getText().toString();
    }

    private void CreateUserAndInsertToDB(){
        userEntity = new UserEntity(userFirstName, userLastName, userEmail, userPassword);
        Repository.getMe().getUserDao().insertUser(userEntity);
        userEntity = Repository.getMe().getUserDao().getUserByEmail(userEmail);
        bundle.putInt(Enums.USERID.toString(), userEntity.getId());
    }

    public boolean checksFields(){
        if(!FieldValidation.getMe().isValidName(userFirstName)) {
            errorMessage += "\nFirst Name not valid";
            register_EDT_firstName.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else
            register_EDT_firstName.setBackgroundColor(Color.TRANSPARENT);
        if(!FieldValidation.getMe().isValidName(userLastName)) {
            errorMessage += "\nLast Name not valid";
            register_EDT_lastName.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else
            register_EDT_lastName.setBackgroundColor(Color.TRANSPARENT);

        if(!FieldValidation.getMe().isValidEmailAddress(userEmail)){
            errorMessage += "\nEmail not valid";
            register_EDT_email.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else {
            if (Repository.getMe().getUserDao().getUserByEmail(userEmail) != null) {
                errorMessage += "\nThis Email exists";
                register_EDT_email.setBackgroundColor(Color.parseColor("#4DFF6666"));
                validateFlag = false;
            } else
                register_EDT_email.setBackgroundColor(Color.TRANSPARENT);
        }
        if(!FieldValidation.getMe().isValidPassword(userPassword)) {
            errorMessage += "\nPassword not valid, needs 5 to 18 chars";
            register_EDT_password.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else
            register_EDT_password.setBackgroundColor(Color.TRANSPARENT);
        return validateFlag;
    }
    public MaterialAlertDialogBuilder setPopUpValidation(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_caution)
                .setTitle("ERROR")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        errorMessage="";
        return selectGameScreen;
    }

}
