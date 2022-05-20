package com.example.contactappnew;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    //for single userEntity insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity userEntity);

    //checking user exist or not in our db
    @Query("SELECT * FROM user WHERE email LIKE :mail AND password LIKE :pass")
    UserEntity getUser(String mail, String pass);

    @Query("SELECT DISTINCT * FROM user")
    List<UserEntity> getUsers();

    @Query("SELECT DISTINCT * FROM user WHERE id LIKE :id")
    UserEntity getUserByID(int id);

    //getting user data details
    @Query("select * from user where id Like :id")
    UserEntity getUserDataDetails(int id);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

}
