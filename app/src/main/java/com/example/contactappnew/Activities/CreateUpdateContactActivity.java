package com.example.contactappnew.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappnew.CallBack_CreateGenderFotContact;
import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.Enums;
import com.example.contactappnew.FieldValidation;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.GenderGenerator;
import com.example.contactappnew.Repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class CreateUpdateContactActivity extends AppCompatActivity {
    private TextInputEditText contact_EDT_firstName
            ,contact_EDT_lastName
            ,contact_EDT_email
            ,contact_EDT_phoneNumber;
    private MaterialButton contact_BTN_finish, contact_BTN_exit;
    private Intent intent;
    private Bundle bundle;
    private int userID, contactID;
    private ContactEntity contactEntity;
    private String contactFirstName,
                   contactLastName,
                   contactEmail,
                   contactPhoneNumber,
                   errorMessage="";
    private boolean validateFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_update_contact);
        findViews();
        initializeIntentBundleAndUserID();
        GenderGenerator.getMe().setCallBack_CreateGenderFotContact(callBack_createGenderFotContact);
        if(bundle.containsKey(Enums.CONTACTID.toString())){//Checks if sent here contactID to edit
            getContact();
            fillAllTextFieldsByContact();
        }
        contact_BTN_finish.setOnClickListener(view -> {
            getAllTextFieldsFromContact();
            if(checksFields()) {
                if (bundle.containsKey(Enums.CONTACTID.toString())) {
                    updateUserAndInsertToDB();
                    moveToPageWithBundle(UserContactListActivity.class);
                } else
                    CreateUserAndInsertToDB();
            }else
                setPopUpValidation().show();
            validateFlag = true;
        });
        contact_BTN_exit.setOnClickListener(view -> {
            setPopUp().show();
        });
    }

    private void findViews() {
        contact_EDT_firstName = findViewById(R.id.contact_EDT_firstName);
        contact_EDT_lastName = findViewById(R.id.contact_EDT_lastName);
        contact_EDT_email = findViewById(R.id.contact_EDT_email);
        contact_EDT_phoneNumber = findViewById(R.id.contact_EDT_phoneNumber);
        contact_BTN_finish = findViewById(R.id.contact_BTN_finish);
        contact_BTN_exit = findViewById(R.id.contact_BTN_exit);
    }

    private void initializeIntentBundleAndUserID(){
        intent = getIntent();
        bundle = intent.getBundleExtra(Enums.BUNDLE.toString());
        userID = bundle.getInt(Enums.USERID.toString());
    }

    private void getContact(){
        contactID = bundle.getInt(Enums.CONTACTID.toString());
        contactEntity = Repository.getMe().getContactDao().getContactByID(contactID);
    }

    /**
     * When I want to edit existing contact I need to see his details
     */
    private void fillAllTextFieldsByContact(){
        contact_EDT_firstName.setText(contactEntity.getFirstName());
        contact_EDT_lastName.setText(contactEntity.getLastName());
        contact_EDT_email.setText(contactEntity.getEmail());
        contact_EDT_phoneNumber.setText(contactEntity.getPhoneNumber());
    }

    private void getAllTextFieldsFromContact(){
        contactFirstName = contact_EDT_firstName.getText().toString();
        contactLastName = contact_EDT_lastName.getText().toString();
        contactEmail = contact_EDT_email.getText().toString();
        contactPhoneNumber = contact_EDT_phoneNumber.getText().toString();
    }

    /**
     * Update existing contact, doesn't need to set gender and userID
     */
    private void updateUserAndInsertToDB(){
        setContactEntity();
        Repository.getMe().getContactDao().updateContactEntity(contactEntity);
    }

    /**
     * Create new contact for the user's contact list
     */
    private void CreateUserAndInsertToDB(){
        contactEntity = new ContactEntity();
        setContactEntity();
        contactEntity.setUserID(userID);
        GenderGenerator.getMe().getGenderForName(contactFirstName);
    }

    private void setContactEntity(){
        contactEntity.setPhoneNumber(contactPhoneNumber);
        contactEntity.setFirstName(contactFirstName);
        contactEntity.setLastName(contactLastName);
        contactEntity.setEmail(contactEmail);
    }

    /**
     * CallBack -> when the Gender generator has done, I can update the DB and exit the page
     */
    private CallBack_CreateGenderFotContact callBack_createGenderFotContact = new CallBack_CreateGenderFotContact() {
        @Override
        public void genderGenerated(String gender) {
            contactEntity.setGender(gender);
            Repository.getMe().getContactDao().insertContact(contactEntity);
            moveToPageWithBundle(UserContactListActivity.class);
        }
    };

    private void moveToPageWithBundle(Class activity){
        intent = new Intent(CreateUpdateContactActivity.this, activity);
        bundle.remove(Enums.CONTACTID.toString());
        intent.putExtra(Enums.BUNDLE.toString(), bundle);
        startActivity(intent);
    }

    /**
     * PopUp when the user wants to exit the add/edit contact
     * @return
     */
    public MaterialAlertDialogBuilder setPopUp(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_caution)
                .setTitle("The changes you made won't save")
                .setMessage("Are you sure you want to leave?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveToPageWithBundle(UserContactListActivity.class);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return selectGameScreen;
    }

    public boolean checksFields(){
        if(!FieldValidation.getMe().isValidName(contactFirstName)) {
            errorMessage += "\nFirst Name not valid";
            contact_EDT_firstName.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else
            contact_EDT_firstName.setBackgroundColor(Color.TRANSPARENT);
        if(!FieldValidation.getMe().isValidName(contactLastName)) {
            errorMessage += "\nLast Name not valid";
            contact_EDT_lastName.setBackgroundColor(Color.parseColor("#4DFF6666"));
            validateFlag=false;
        }else
            contact_EDT_lastName.setBackgroundColor(Color.TRANSPARENT);
        if(!contactEmail.equals("")) {
            if (!FieldValidation.getMe().isValidEmailAddress(contactEmail)) {
                errorMessage += "\nEmail not valid";
                contact_EDT_email.setBackgroundColor(Color.parseColor("#4DFF6666"));
                validateFlag = false;
            } else {
                if (Repository.getMe().getContactDao().getContactByEmail(contactEmail) != null) {//Make sure this mail isn't ours
                    if(Repository.getMe().getContactDao().getContactByEmail(contactEmail).getId() != contactID){
                        errorMessage += "\nThis Email exists";
                        contact_EDT_email.setBackgroundColor(Color.parseColor("#4DFF6666"));
                        validateFlag = false;
                    }
                } else
                    contact_EDT_email.setBackgroundColor(Color.TRANSPARENT);
            }
        }else contact_EDT_email.setBackgroundColor(Color.TRANSPARENT);
        if(!contactPhoneNumber.equals("")) {
            if (!FieldValidation.getMe().isValidPhoneNumber(contactPhoneNumber)) {
                errorMessage += "\nPhone Number not valid. Needs to be 10 digits";
                contact_EDT_phoneNumber.setBackgroundColor(Color.parseColor("#4DFF6666"));
                validateFlag = false;
            } else
                contact_EDT_phoneNumber.setBackgroundColor(Color.TRANSPARENT);
        }else contact_EDT_phoneNumber.setBackgroundColor(Color.TRANSPARENT);
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
