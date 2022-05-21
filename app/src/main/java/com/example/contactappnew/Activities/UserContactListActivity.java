package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactappnew.ContactAdapterToListView;
import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Enums;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserContactListActivity extends AppCompatActivity {
    private Intent intent;
    private int userID;
    private UserEntity userEntity;
    private RecyclerView contactList_LST_contacts;
    private ArrayList <ContactEntity> contacts;
    private ContactAdapterToListView contactAdapterToListView;
    private CreateUpdateContactActivity createUpdateContactActivity;
    private FloatingActionButton fab_logOutToSignIn, fab_addContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_list);
        findViews();

        intent = getIntent();
        userID = intent.getIntExtra(Enums.USERID.toString(),0);
        Log.d("tagg","USERCONTACTLIST user iD: " + userID);

        contactAdapterToListView = new ContactAdapterToListView(this);
        restartContactAdapterToListView();


        contactAdapterToListView.setCallBack_ContactCard(new ContactAdapterToListView.CallBack_ContactCard(){
            @Override
            public void remove(ContactEntity contactEntity, int position){
                Repository.getMe().getContactDao().deleteContact(contactEntity.getId());
                restartContactAdapterToListView();
            }

            @Override
            public void settings(ContactEntity contactEntity, int position){
                intent = new Intent(UserContactListActivity.this, CreateUpdateContactActivity.class);
                intent.putExtra(Enums.USERID.toString(), userID);
                intent.putExtra(Enums.CONTACTID.toString(), contactEntity.getId());
                startActivity(intent);
            }
        });


        fab_addContact.setOnClickListener(view -> {
            intent = new Intent(UserContactListActivity.this, CreateUpdateContactActivity.class);
            intent.putExtra(Enums.USERID.toString(), userID);
            startActivity(intent);
        });
    }

    private void findViews() {
        fab_logOutToSignIn = findViewById(R.id.fab_logOutToSignIn);
        fab_addContact = findViewById(R.id.fab_addContact);
        contactList_LST_contacts = findViewById(R.id.contactList_LST_contacts);
    }

    public void restartContactAdapterToListView(){
        contacts = new ArrayList<ContactEntity>(Repository.getMe().getContactDao().getContactsByUserID(userID));
        Log.d("tagg", "contacts lisr: " + contacts.get(0).getFirstName());

        contactAdapterToListView.setContacts(contacts);
        contactList_LST_contacts.setLayoutManager(new LinearLayoutManager(UserContactListActivity.this));
        contactList_LST_contacts.setHasFixedSize(true);
        contactList_LST_contacts.setAdapter(contactAdapterToListView);
    }

}
