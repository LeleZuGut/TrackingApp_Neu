package com.example.trackingapp.spinnermodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trackingapp.R;

public class StatusSpinnerAdapter extends BaseAdapter {

    Context context;
    String[] status;
    LayoutInflater inflter;

    public StatusSpinnerAdapter(Context applicationContext, String[] status) {
        this.context = applicationContext;
        this.status = status;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_status, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.spinner_imageView);
        TextView names = (TextView) convertView.findViewById(R.id.spinner_text);
        names.setText(status[position]);
        switch (status[position]){
            case "Verfügbar":
                icon.setImageResource(R.drawable.circle_green);
                break;
            case "Besetzt":
                icon.setImageResource(R.drawable.circle_red);
                break;
            case "Defekt":
                icon.setImageResource(R.drawable.circle_orange);
                break;
        }
        return convertView;
    }
}
