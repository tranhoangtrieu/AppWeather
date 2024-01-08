package com.example.appweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appweather.Adapter.LocationAutoSuggest;
//import com.google.android.gms.maps.model.LatLng;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.appweather.Adapter.LocationAutoSuggest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputActivity extends AppCompatActivity {
    Button btn_Search;
    String city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mapping();
        AutoCompleteTextView Text_input = findViewById(R.id.Text_input);
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
                finish();
            }
        });
    }

    private void mapping(){
        btn_Search = findViewById(R.id.btn_Search);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InputActivity.this, Login.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
