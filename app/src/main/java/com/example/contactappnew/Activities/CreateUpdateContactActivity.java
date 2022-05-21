package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.Enums;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.GenderGenerator;
import com.example.contactappnew.Repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateUpdateContactActivity extends AppCompatActivity {

    public interface CallBack_CreateContact {
        void addContact();
    }

    private CallBack_CreateContact callBackCreateContact;
    private TextInputEditText contact_EDT_firstName
            ,contact_EDT_lastName
            ,contact_EDT_email
            ,contact_EDT_phoneNumber;
    private MaterialButton contact_BTN_finish;
    private Intent intent;
    private int userID, contactID;
    private ContactEntity contactEntity;
    private String contactFirstName,
                   contactLastName,
                   contactEmail,
                   contactPhoneNumber,
                   contactGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_update_contact);
        findViews();
        intent = getIntent();
        userID = intent.getIntExtra(Enums.USERID.toString(),0);
        if(intent.getExtras().containsKey(Enums.CONTACTID.toString()))//Checks if there is a field in the intent
            contactID = intent.getIntExtra(Enums.CONTACTID.toString(),0);
            //TODO: if contactID exist i need to fill all the text

        contact_BTN_finish.setOnClickListener(view -> {
            contactFirstName = contact_EDT_firstName.getText().toString();
            contactLastName = contact_EDT_lastName.getText().toString();
            contactEmail = contact_EDT_email.getText().toString();
            contactPhoneNumber = contact_EDT_phoneNumber.getText().toString();
            //contactGender = GenderGenerator.getMe().getGenderForName(contactFirstName);
            contactEntity = new ContactEntity(contactPhoneNumber, contactFirstName, contactLastName, "male", contactEmail, userID);
            Repository.getMe().getContactDao().insertContact(contactEntity);
            //callBackCreateContact.addContact();//
            intent = new Intent(CreateUpdateContactActivity.this, UserContactListActivity.class);
            intent.putExtra(Enums.USERID.toString(), userID);
            intent.putExtra(Enums.CONTACTID.toString(), contactEntity.getId());
            startActivity(intent);
        });

    }

    public void setCallBack_CreateContact(CallBack_CreateContact callBackCreateContact){
        this.callBackCreateContact = callBackCreateContact;
    }

    private void findViews() {
        contact_EDT_firstName= findViewById(R.id.contact_EDT_firstName);
        contact_EDT_lastName= findViewById(R.id.contact_EDT_lastName);
        contact_EDT_email= findViewById(R.id.contact_EDT_email);
        contact_EDT_phoneNumber= findViewById(R.id.contact_EDT_phoneNumber);
        contact_BTN_finish= findViewById(R.id.contact_BTN_finish);
    }
}
