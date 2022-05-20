package com.example.contactappnew;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contact")
public class ContactEntity {

    public ContactEntity(){}
    ContactEntity(String phoneNumber, String firstName, String lastName, String gender, String email){
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;//TODO: can be multiple numbers
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ColumnInfo(name = "first_name")
    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ColumnInfo(name = "last_Name")
    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ColumnInfo(name = "gender")
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
}
