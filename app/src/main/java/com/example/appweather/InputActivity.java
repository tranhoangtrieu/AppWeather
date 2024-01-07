package com.example.appweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class InputActivity extends AppCompatActivity {
    TextView Text_input;
    Button btn_Search;
    String city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mapping();
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = Text_input.getText().toString().trim();
                if(city.equals("")){
                    city = "Ho Chi Minh";
                }
                Intent intent = new Intent(InputActivity.this, MainActivity.class);
                intent.putExtra("city", city);
                startActivity(intent);
                finish();
            }
        });
    }

    private void mapping(){
        Text_input = findViewById(R.id.Text_input);
        btn_Search = findViewById(R.id.btn_Search);
    }
}
