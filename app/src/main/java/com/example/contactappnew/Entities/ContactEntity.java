package com.example.contactappnew.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contact")
public class ContactEntity {

    public ContactEntity(){}

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    public int getId() { return id; }
    public void setId(@NonNull int id) { this.id = id; }

    @ColumnInfo(name = "Phone Number")
    private String phoneNumber;//TODO: can be multiple numbers
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @ColumnInfo(name = "First Name")
    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ColumnInfo(name = "Last Name")
    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    @ColumnInfo(name = "Gender")
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @ColumnInfo(name = "Email")
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @NonNull
    @ColumnInfo(name = "User ID")
    private int userID;
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID;}
}
