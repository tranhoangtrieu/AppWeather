package com.example.appweather;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appweather.User;
import java.util.List;

@Dao
public interface DAO {
    @Query("Select * From user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username = :username")
    List<User> loadAllByUserName(String username);
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    List<User> loadAllByUserNamePassWord(String username,String password);
    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
