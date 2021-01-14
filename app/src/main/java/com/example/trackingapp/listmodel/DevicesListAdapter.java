package com.example.trackingapp.listmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trackingapp.Object;
import com.example.trackingapp.R;

import java.util.ArrayList;
import java.util.List;

public class DevicesListAdapter extends BaseAdapter {
    private List<Object> objects = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    public DevicesListAdapter(Context ctx, int layoutId, List<Object> objects) {
        this.objects = objects;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object o = objects.get(position);
        View lstItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        ((TextView) lstItem.findViewById(R.id.text_view_device_listView)).setText(o.toString());
        ImageView status= (ImageView) lstItem.findViewById(R.id.image_view_status_listView);
        status.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        ImageView fixing = (ImageView) lstItem.findViewById(R.id.image_view_fix_listView);
        fixing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });


        return null;
    }
}
