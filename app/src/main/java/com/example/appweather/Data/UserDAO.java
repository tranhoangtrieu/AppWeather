package com.example.appweather.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appweather.User;


import java.util.List;
@Dao
public interface  UserDAO {
    @Query("Select * From user")
    List<User> getAll();

   @Query("SELECT * FROM user WHERE username = :username")
   List<User> loadAllByUserName(String username);
   @Query("SELECT * FROM user WHERE username = :username AND password = :password")
   List<User> loadAllByUserNamePassWord(String username,String password);
  @Insert
   void insertAll(User... users);
    @Query("SELECT Name FROM USER WHERE username = :username")
    String getNameOfUser(String username);

  @Delete
   void delete(User user);
}