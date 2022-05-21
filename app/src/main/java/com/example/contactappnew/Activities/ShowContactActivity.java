package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.Enums;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ShowContactActivity extends AppCompatActivity {
    private MaterialTextView showContact_LBL_firstName,
                             showContact_LBL_lastName,
                             showContact_LBL_email,
                             showContact_LBL_phoneNumber;
    private MaterialButton showContact_BTN_finish;
    private Intent intent;
    private Bundle bundle;
    private int contactID;
    private ContactEntity contactEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_contact);
        findViews();
        intent = getIntent();
        bundle = intent.getBundleExtra(Enums.BUNDLE.toString());
        contactID = bundle.getInt(Enums.CONTACTID.toString());
        contactEntity = Repository.getMe().getContactDao().getContactByID(contactID);
        showContact_LBL_firstName.setText(contactEntity.getFirstName());
        showContact_LBL_lastName.setText(contactEntity.getLastName());
        showContact_LBL_email.setText(contactEntity.getEmail());
        showContact_LBL_phoneNumber.setText(contactEntity.getPhoneNumber());

        showContact_BTN_finish.setOnClickListener(view -> {
            intent = new Intent(ShowContactActivity.this, UserContactListActivity.class);
            intent.putExtra(Enums.BUNDLE.toString(), bundle);
            startActivity(intent);
            finish();
        });

    }

    private void findViews() {
        showContact_LBL_firstName = findViewById(R.id.showContact_LBL_firstName);
        showContact_LBL_lastName = findViewById(R.id.showContact_LBL_lastName);
        showContact_LBL_email = findViewById(R.id.showContact_LBL_email);
        showContact_LBL_phoneNumber = findViewById(R.id.showContact_LBL_phoneNumber);
        showContact_BTN_finish = findViewById(R.id.showContact_BTN_finish);
    }
}
