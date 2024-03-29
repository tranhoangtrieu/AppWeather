package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appweather.Data.UserDatabase;
import com.example.appweather.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Register extends AppCompatActivity {

    EditText edit_username;
    EditText edit_password;
    EditText edit_name;
    Button btn_back;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addUser();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }
    private void initUI(){
        edit_username = findViewById(R.id.edit_usernameRegister);
        edit_password = findViewById(R.id.edit_passwordRegister);
        edit_name = findViewById(R.id.edit_nameRegister);
        btn_register = findViewById(R.id.btn_register);
        btn_back = findViewById(R.id.btn_back);
    }
    private void addUser() {
        String strusername = edit_username.getText().toString().trim();
        String strpassword = edit_password.getText().toString().trim();
        String strname =  edit_name.getText().toString().trim();

        if(TextUtils.isEmpty(strusername) ||TextUtils.isEmpty(strpassword) ){
            return;
        }
        User user = new User(strusername,strpassword,strname);
        if(isUserExist(user)){
            Toast.makeText(this,"User existed",Toast.LENGTH_SHORT).show();
            return;
        }
        UserDatabase.getInstance(this).userDAO().insertAll(user);

        Toast.makeText(this,"Add user successfully",Toast.LENGTH_SHORT).show();

        edit_username.setText("");
        edit_password.setText("");
        edit_name.setText("");
    }

    private boolean isUserExist(@NotNull User user){
        List<User> list = UserDatabase.getInstance(this).userDAO().loadAllByUserName(user.getUsername());
        return list !=null && !list.isEmpty();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}