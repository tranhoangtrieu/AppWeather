package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.ArchTaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appweather.Data.UserDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Login extends AppCompatActivity {
    Button btn_dangnhap ;
    Button btn_dangky;

    EditText edit_username;
    EditText edit_password;
    TextView text_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitUI();

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);


            }
        });

    }


    private void InitUI(){
        edit_username = findViewById(R.id.edit_username);

        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        btn_dangky = findViewById(R.id.btn_dangky);

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
            setContentView(R.layout.login);
            text_name = findViewById(R.id.tetx_name);


        }
    }

}