package com.example.appweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
    String city;
    private static  String TITLE_PUSH_NOTIFICATION = "Thông báo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request permission and postpone setting up UI
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }
        setContentView(R.layout.activity_main);
        mapping();
        city = getIntent().getExtras().getString("city");
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
                            sendNotification();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with setting up UI
            recreate();
        }
    }
    private int getNotificationID() {
        return (int) new Date().getTime();
    }


    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunny);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Create an Intent for the activity you want to start.
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(getNotificationID(),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Intent intent = getIntent();
        String temp = intent.getExtras().getString("temp");
        String description = intent.getExtras().getString("description");

        NotificationCompat.Builder Builder = new NotificationCompat
                .Builder(MainActivity.this, createNotificationChannel.CHANNEL_ID)
                .setContentTitle(TITLE_PUSH_NOTIFICATION)
                .setContentText("Thời tiết hôm nay của thành phố "+ city +"\nNhiệt độ "+ Text_temperature.getText() +"\nTrạng thái thời tiết "+ txtTemperState.getText())
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Thời tiết hôm nay của thành phố "+ city +"\nNhiệt độ "+ Text_temperature.getText() +"\nTrạng thái thời tiết : "+ txtTemperState.getText()))
                .setSmallIcon(R.drawable.cloudy_sunny)
                .setLargeIcon(bitmap)
                .setSound(uri)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationID(), Builder.build());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}