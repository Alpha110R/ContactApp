package com.example.contactappnew;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactappnew.Entities.ContactEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapterToListView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface CallBack_ContactCard {
        void remove(ContactEntity contactEntity, int position);
        void settings(ContactEntity contactEntity, int position);
        void clicked(ContactEntity contactEntity, int position);
    }

    private List<ContactEntity> contacts;
    private CallBack_ContactCard callBackContactCard;
    private Activity activity;

    public ContactAdapterToListView (Activity activity){
        this.activity = activity;
    }
    public void setCallBack_ContactCard(CallBack_ContactCard callBackContactCard) {
        this.callBackContactCard = callBackContactCard;
    }

    public ContactAdapterToListView setContacts(List<ContactEntity> contacts) {
        this.contacts = contacts;

        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        ContactHolder contactHolder = new ContactHolder(view);
        return contactHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ContactHolder holder = (ContactHolder) viewHolder;
        ContactEntity contactEntity = getContact(position);
/**
 * Connect to view
 */
        holder.contactCard_LBL_contactName.setText(contactEntity.getFirstName() + " " + contactEntity.getLastName());
        holder.contactCard_LBL_contactGender.setText(contactEntity.getGender());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public ContactEntity getContact(int position){
        return contacts.get(position);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private MaterialTextView contactCard_LBL_contactName, contactCard_LBL_contactGender;
        private MaterialButton contactCard_BTN_remove, contactCard_BTN_settings;

        public ContactHolder(View itemView) {
            super(itemView);
            contactCard_LBL_contactName = itemView.findViewById(R.id.contactCard_LBL_contactName);
            contactCard_LBL_contactGender = itemView.findViewById(R.id.contactCard_LBL_contactGender);
            contactCard_BTN_remove = itemView.findViewById(R.id.contactCard_BTN_remove);
            contactCard_BTN_settings = itemView.findViewById(R.id.contactCard_BTN_settings);

            contactCard_BTN_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBackContactCard != null) {
                        callBackContactCard.remove(getContact(getAdapterPosition()), getAdapterPosition());
                        contacts.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });

            contactCard_BTN_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBackContactCard != null) {
                        callBackContactCard.settings(getContact(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBackContactCard != null) {
                        callBackContactCard.clicked(getContact(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }
    }
}
