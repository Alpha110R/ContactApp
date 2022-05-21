package com.example.contactappnew.Repository;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactappnew.Entities.ContactEntity;
import com.example.contactappnew.Entities.UserEntity;
import com.example.contactappnew.Repository.ContactDao;
import com.example.contactappnew.Repository.UserDao;

@Database(entities = {UserEntity.class, ContactEntity.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract ContactDao getContactDao();
}
