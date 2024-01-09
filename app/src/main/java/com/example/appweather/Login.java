package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appweather.Data.UserDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Login extends AppCompatActivity {
    Button btn_login ;
    Button btn_register;

    EditText edit_username;
    EditText edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitUI();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);


            }
        });

    }


    private void InitUI(){
        edit_username = findViewById(R.id.edit_username);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        edit_password = findViewById(R.id.edit_Password);

    }


    private boolean isAccountExist(@NotNull User user){

        List<User> list = UserDatabase.getInstance(this).userDAO().loadAllByUserNamePassWord(user.getUsername(), user.getPassword());

        return list !=null && !list.isEmpty();
    }

    private void login() {
        String strusername = edit_username.getText().toString().trim();
        String strpassword = edit_password.getText().toString().trim();


        if(TextUtils.isEmpty(strusername) ||TextUtils.isEmpty(strpassword) ){
            return;
        }


        User user = new User(strusername,strpassword);
        if(isAccountExist(user)){
            Intent intent = new Intent(Login.this, InputActivity.class);
            intent.putExtra("strusername", strusername);
            startActivity(intent);
        }
    }

}