package com.example.contactappnew.Repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactappnew.Entities.ContactEntity;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(ContactEntity contact);

    @Query("SELECT DISTINCT * FROM contact WHERE `User ID` LIKE:userID")
    List<ContactEntity> getContactsByUserID(int userID);

    @Query("SELECT * FROM contact WHERE `id` LIKE :contactID AND Email LIKE:mail")
    ContactEntity getContactByContactIDEmail(int contactID, String mail);

    @Query("SELECT * FROM contact WHERE id LIKE :id")
    ContactEntity getContactByID(int id);

    @Query("SELECT * FROM contact WHERE `First Name` LIKE :firstName AND `Last Name` LIKE:lastName")
    ContactEntity getContactByFirstAndLastName(String firstName, String lastName);

    @Query("SELECT * FROM contact WHERE Email LIKE :email")
    ContactEntity getContactByEmail(String email);

    @Query("SELECT * FROM contact WHERE `Phone Number` LIKE :phoneNumber")
    ContactEntity getContactByPhoneNumber(String phoneNumber);

    @Update
    void updateContactEntity(ContactEntity contactEntity);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("DELETE FROM contact WHERE id LIKE:id")
    void deleteContactByID(int id);

    @Query("DELETE FROM contact WHERE id LIKE :id")
    void deleteContact(int id);

}
