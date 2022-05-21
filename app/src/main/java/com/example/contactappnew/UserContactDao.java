package com.example.contactappnew;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserContactDao {
    @Query("SELECT user.`First Name` AS userName, contact.`First Name` AS contactFirstName FROM user, contact WHERE user.id = contact.`User ID`")
    public List<UserContact> getAllUsersContacts();

}
class UserContact {
    public String userName;
    public String contactFirstName;
}