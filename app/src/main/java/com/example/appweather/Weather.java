package com.example.appweather;

public class Weather {

    String currentTime;
    String state;
    String urlIcon;
    String temp_max;
    String temp_min;

    public Weather(String currentTime, String state, String urlIcon, String temp_max, String temp_min) {
        this.currentTime = currentTime;
        this.state = state;
        this.urlIcon = urlIcon;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }
}
