package com.example.appweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appweather.Adapter.LocationAutoSuggest;
import com.example.appweather.Data.UserDatabase;
//import com.google.android.gms.maps.model.LatLng;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {
    Button btn_Search;
    String city;
    TextView Text_name;
    AutoCompleteTextView Text_input;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mapping();
        Intent intent = getIntent();
        String strusername = intent.getExtras().getString("strusername");
        Text_name.setText("Xin chào "+ UserDatabase.getInstance(this).userDAO().getNameOfUser(strusername));
        Text_input.setAdapter(new LocationAutoSuggest(InputActivity.this,android.R.layout.simple_list_item_1));
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
            }
        });
    }

    private void mapping(){
        btn_Search = findViewById(R.id.btn_Search);
        Text_name = findViewById(R.id.Text_name);
        Text_input = findViewById(R.id.Text_input);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InputActivity.this, Login.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
