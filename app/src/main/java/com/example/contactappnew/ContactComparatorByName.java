package com.example.contactappnew;

import com.example.contactappnew.Entities.ContactEntity;

import java.util.Comparator;

public class ContactComparatorByName implements Comparator<ContactEntity> {
    @Override
    public int compare(ContactEntity contactEntity, ContactEntity t1) {
        return contactEntity.getFirstName().compareTo(t1.getFirstName());
    }
}
