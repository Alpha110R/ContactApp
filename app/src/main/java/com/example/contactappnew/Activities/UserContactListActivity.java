package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactappnew.ContactAdapterToListView;
import com.example.contactappnew.ContactComparatorByName;
import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.R;
import com.example.contactappnew.Repository.Repository;
import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Enums;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserContactListActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;
    private int userID;
    private RecyclerView contactList_LST_contacts;
    private List<ContactEntity> contacts;
    private ContactAdapterToListView contactAdapterToListView;
    private FloatingActionButton fab_logOutToSignIn, fab_addContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_list);
        findViews();
        initializeIntentBundleAndUserID();
        contactAdapterToListView = new ContactAdapterToListView(this);
        restartContactAdapterToListView();

        contactAdapterToListView.setCallBack_ContactCard(new ContactAdapterToListView.CallBack_ContactCard(){
            @Override
            public void remove(ContactEntity contactEntity, int position){
                Repository.getMe().getContactDao().deleteContact(contactEntity.getId());
            }

            @Override
            public void settings(ContactEntity contactEntity, int position){
                bundle.putInt(Enums.CONTACTID.toString(), contactEntity.getId());
                moveToPageWithBundle(CreateUpdateContactActivity.class);
            }

            @Override
            public void clicked(ContactEntity contactEntity, int position) {
                bundle.putInt(Enums.CONTACTID.toString(), contactEntity.getId());
                moveToPageWithBundle(ShowContactActivity.class);
            }
        });

        fab_addContact.setOnClickListener(view -> {
            bundle.remove(Enums.CONTACTID.toString());
            moveToPageWithBundle(CreateUpdateContactActivity.class);
        });
        fab_logOutToSignIn.setOnClickListener(view -> {
            moveToPageWithBundle(SignInActivity.class);
        });
    }

    private void findViews() {
        fab_logOutToSignIn = findViewById(R.id.fab_logOutToSignIn);
        fab_addContact = findViewById(R.id.fab_addContact);
        contactList_LST_contacts = findViewById(R.id.contactList_LST_contacts);
    }

    public void restartContactAdapterToListView(){
        contacts = new ArrayList<>(Repository.getMe().getContactDao().getContactsByUserID(userID));
        Collections.sort(contacts, new ContactComparatorByName());
        contactAdapterToListView.setContacts(contacts);
        contactList_LST_contacts.setLayoutManager(new LinearLayoutManager(UserContactListActivity.this));
        contactList_LST_contacts.setHasFixedSize(true);
        contactList_LST_contacts.setAdapter(contactAdapterToListView);
    }

    public void initializeIntentBundleAndUserID(){
        intent = getIntent();
        bundle = intent.getBundleExtra(Enums.BUNDLE.toString());
        userID = bundle.getInt(Enums.USERID.toString());
    }

    public void moveToPageWithBundle(Class activity){
        intent = new Intent(UserContactListActivity.this, activity);
        intent.putExtra(Enums.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }

}
