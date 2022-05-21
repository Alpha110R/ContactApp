package com.example.contactappnew;

import android.app.Application;

import com.example.contactappnew.Repository.GenderGenerator;
import com.example.contactappnew.Repository.Repository;
import com.example.contactappnew.Utils.FieldValidation;
import com.example.contactappnew.Utils.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.initHelper(this);
        GenderGenerator.initHelper();
        MySignal.initHelper(this);
        FieldValidation.initHelper();
    }
}
