package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appweather.Adapter.WeatherAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FutureActivity extends AppCompatActivity {
    static final String API_KEY = "d15dcebccc91478483885fba781fc2a6" ;
    List<Weather> weatherList;
    WeatherAdapter weatherAdapter;
    ListView List_Nextday;
    ImageView img_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);
        Intent intent = getIntent();
        String city = intent.getExtras().getString("city");
        List_Nextday = findViewById(R.id.List_Nextday);
        weatherList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(FutureActivity.this, R.layout.row_weather, weatherList);
        List_Nextday.setAdapter(weatherAdapter);
        getJsonNextDay(city);
        img_Back = findViewById(R.id.img_Back);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutureActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getJsonNextDay(String city){
        final String url = "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+API_KEY+"&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for(int i = 0; i < list.length(); i++){
                                JSONObject item = list.getJSONObject(i);
                                String sDate = item.getString("dt");

                                long lDate = Long.parseLong(sDate);

                                SimpleDateFormat dateFormat = new SimpleDateFormat ("EEEE, dd-MM-yyyy HH:mm:ss");

                                Date date = new Date(lDate*1000);
                                String currentTime = dateFormat.format(date);//ngày giờ hiện tại

                                JSONObject main = item.getJSONObject("main");
                                String temp_min = main.getString("temp_min");//nhiệt độ nhỏ nhất

                                String temp_max = main.getString("temp_max");//nhiệt độ lớn nhất

                                JSONArray weather = item.getJSONArray("weather");

                                JSONObject weatherItem = weather.getJSONObject(0);
                                String description = weatherItem.getString("description");

                                String icon = weatherItem.getString("icon");
                                String urlIcon = "http://openweathermap.org/img/wn/"+icon+".png";//icon

                                weatherList.add(new Weather(currentTime, description, urlIcon, temp_max, temp_min));
                            }
                            weatherAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(FutureActivity.this, "Dữ liệu thời tiết của thành phố : " + city, Toast.LENGTH_SHORT).show();
                        Log.d("Error",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FutureActivity.this, "Không có dữ liệu của thành phố : " + city, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FutureActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}