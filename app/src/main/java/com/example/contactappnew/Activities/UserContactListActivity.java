package com.example.contactappnew.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

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
    private TextInputEditText contactList_EDT_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_list);
        findViews();
        initializeIntentBundleAndUserID();
        contactAdapterToListView = new ContactAdapterToListView(this);
        restartContactAdapterToListView();

        contactList_EDT_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.d("tagg", "afterTextChanged");
                if(s.length() == 0){
                    contacts = new ArrayList<>(Repository.getMe().getContactDao().getContactsByUserID(userID));
                    Collections.sort(contacts, new ContactComparatorByName());
                    contactAdapterToListView.setContacts(contacts);
                    contactAdapterToListView.notifyDataSetChanged();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("tagg", "beforeTextChanged");

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("tagg", "onTextChanged");
                contactAdapterToListView.setContacts(getListContactBySearch(s.toString()));
                contactAdapterToListView.notifyDataSetChanged();
            }
        });

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
        contactList_EDT_search = findViewById(R.id.contactList_EDT_search);
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

    public List<ContactEntity> getListContactBySearch(String search){
        List<ContactEntity> newContactList = new ArrayList<>();
         for(int i=0 ; i< contacts.size(); i++){
             if(contacts.get(i).getFirstName().startsWith(search))
                 newContactList.add(contacts.get(i));
         }
        return newContactList;
    }

}
        /**.stream()
        .filter(contact -> contact.getFirstName().startsWith())
        .collect(Collectors.toList());*/