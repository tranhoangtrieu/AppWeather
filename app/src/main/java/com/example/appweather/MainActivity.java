package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    TextView Text_cloud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getJsonWeather("hanoi");
    }

    public void getJsonWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
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
                            String temp = response.getString("temp");
                            Text_temperature.setText(temp+"°");
                            String humidity = main.getString("humidity");
                            Text_Humidity.setText(humidity+"%");
                            JSONObject wind = response.getJSONObject("wind");
                            String speed = wind.getString("wind");
                            Text_Wind_Speed.setText(speed+"m/s");
                            JSONObject clouds = response.getJSONObject("clouds");
                            String all = clouds.getString("all");
                            Text_cloud.setText(all + "%");
                            String date = response.getString("dt");
                            long lDate = Long.parseLong(date);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm:ss");
                            Date dates = new Date(lDate);
                            String currenttime = dateFormat.format(dates);
                            Text_datetime.setText(currenttime);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(MainActivity.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}