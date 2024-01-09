package com.example.appweather;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
    public User(){};

    public User(String username, String password,String Name){

        this.username = username;
        this.password = password;
        this.Name =Name;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private String Name;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
