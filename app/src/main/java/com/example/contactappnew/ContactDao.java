package com.example.contactappnew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(ContactEntity contact);

    @Query("SELECT DISTINCT * FROM contact")
    List<ContactEntity> getContacts();

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("DELETE FROM contact WHERE id LIKE :id")
    void deleteContact(int id);

}
