package com.example.appweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appweather.R;
import com.example.appweather.Weather;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Weather> list;

    public WeatherAdapter(Context context, int layout, List<Weather> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView Text_Curtime;
        TextView Text_State;
        ImageView imgIcon;
        TextView Text_tempMin;
        TextView Text_tempMax;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder.Text_Curtime = convertView.findViewById(R.id.Text_Curtime);
            viewHolder.Text_State = convertView.findViewById(R.id.Text_State);
            viewHolder.imgIcon = convertView.findViewById(R.id.imgIcon);
            viewHolder.Text_tempMin = convertView.findViewById(R.id.Text_tempMin);
            viewHolder.Text_tempMax = convertView.findViewById(R.id.Text_tempMax);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) convertView.getTag();
        }
        Weather weather = list.get(position);
        viewHolder.Text_Curtime.setText(weather.getCurrentTime());
        viewHolder.Text_State.setText(weather.getState());
        Picasso.get().load(weather.getUrlIcon()).into(viewHolder.imgIcon);
        viewHolder.Text_tempMin.setText(weather.getTemp_min()+"°C -");
        viewHolder.Text_tempMax.setText(" "+weather.getTemp_max()+"°C");
        return convertView;
    }
}
