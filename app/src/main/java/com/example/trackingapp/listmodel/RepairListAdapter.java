package com.example.trackingapp.listmodel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class RepairListAdapter extends BaseAdapter {
    private List<Object> objects = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;
    MyDatabaseManager mdm;

    public RepairListAdapter(Context ctx, int layoutId, List<Object> objects) {
        this.objects = objects;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mdm = new MyDatabaseManager((Activity)ctx);
    }

    @Override
    public int getCount() {
        return objects.size();
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
        final Object o = objects.get(position);
        View lstItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        ((TextView) lstItem.findViewById(R.id.text_view_repair_listView2)).setText(o.toString());
        ImageView deselect = (ImageView) lstItem.findViewById(R.id.image_view_deselect_listView2);

        deselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mdm.updateStatus(o.getId(), "'frei'", "Status");
            }
        });

        return lstItem;
    }
}
