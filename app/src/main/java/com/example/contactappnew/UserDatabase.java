package com.example.contactappnew;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, ContactEntity.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract ContactDao getContactDao();
}
