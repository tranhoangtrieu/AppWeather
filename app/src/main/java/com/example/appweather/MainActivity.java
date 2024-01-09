package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final String API_KEY = "d15dcebccc91478483885fba781fc2a6" ;
    TextView txtTemperState;
    ImageView imgWeatherIcon;
    TextView Text_datetime;
    TextView Text_temperature;
    TextView Text_Humidity;
    TextView Text_Wind_Speed;
    TextView Text_cloudy;
    TextView Text_NameCity;
    TextView Text_NameNational;

    Button btn_Next_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        String city = getIntent().getExtras().getString("city");
        if(city.equals("")){
            getJsonWeather("Ho Chi Minh");
        }else getJsonWeather(city);
        btn_Next_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FutureActivity.class);
                intent.putExtra("city", city);
                startActivity(intent);
            }
        });

    }

    private void mapping(){
        txtTemperState = findViewById(R.id.txtTemperState);
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);
        Text_datetime = findViewById(R.id.Text_datetime);
        Text_temperature = findViewById(R.id.Text_temperature);
        Text_Humidity = findViewById(R.id.Text_Humidity);
        Text_Wind_Speed = findViewById(R.id.Text_Wind_Speed);
        Text_cloudy = findViewById(R.id.Text_cloudy);
        Text_NameCity = findViewById(R.id.Text_NameCity);
        Text_NameNational = findViewById(R.id.Text_NameNational);
        btn_Next_day = findViewById(R.id.btn_Next_day);
    }

    public void getJsonWeather(final String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" +city+ "&appid=" + API_KEY + "&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);
                            String icon = weatherObj.getString("icon");
                            String urlIcon = "https://openweathermap.org/img/wn/" + icon + ".png";
                            Picasso.get().load(urlIcon).into(imgWeatherIcon);
                            String temperState = weatherObj.getString("description");
                            txtTemperState.setText(temperState);
                            JSONObject main = response.getJSONObject("main");
                            String temp = main.getString("temp");
                            Text_temperature.setText(temp+"°C");
                            String humidity = main.getString("humidity");
                            Text_Humidity.setText(humidity+"%");
                            JSONObject wind = response.getJSONObject("wind");
                            String speed = wind.getString("speed");
                            Text_Wind_Speed.setText(speed+"m/s");
                            JSONObject clouds = response.getJSONObject("clouds");
                            String all = clouds.getString("all");
                            Text_cloudy.setText(all + "%");
                            String date = response.getString("dt");
                            long lDate = Long.parseLong(date);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm:ss");
                            Date dates = new Date(lDate * 1000);
                            String currenttime = dateFormat.format(dates);
                            Text_datetime.setText(currenttime);
                            String nameCity = response.getString("name");
                            Text_NameCity.setText("Thành phố : "+nameCity);
                            JSONObject sys = response.getJSONObject("sys");
                            String national = sys.getString("country");
                            Text_NameNational.setText("Quốc gia : "+national);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(MainActivity.this, "Dữ liệu thời tiết của thành phố : " + city, Toast.LENGTH_SHORT).show();
                        Log.d("Error",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Không có dữ liệu của thành phố : " + city, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


}