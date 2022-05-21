package com.example.contactappnew.Repository;

import android.app.Application;

import androidx.room.Room;

public class Repository {
    private static Repository me;
    private UserDao userDao;
    private ContactDao contactDao;
    private Application application;

    public static void initHelper(Application application) {
        if (me == null) {
            me = new Repository(application);
        }
    }
    public static Repository getMe() { return me; }

    private Repository (Application application){
        this.application = application;
        userDao = Room.databaseBuilder(application, UserDatabase.class, "testDB.db")
                .allowMainThreadQueries()
                .build().getUserDao();
        contactDao = Room.databaseBuilder(application, UserDatabase.class, "testDB.db")
                .allowMainThreadQueries()
                .build().getContactDao();

    }

    public UserDao getUserDao(){
        return userDao;
    }
    public ContactDao getContactDao(){
        return contactDao;
    }


}
