package com.example.trackingapp.listmodel;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import com.example.trackingapp.MainActivity;
import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;
import com.example.trackingapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class DevicesListAdapter extends BaseAdapter {
    private List<Object> objects = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;
    Context ctx;
    MyDatabaseManager mdm;

    public DevicesListAdapter(Context ctx, int layoutId, List<Object> objects) {
        this.objects = objects;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ctx = ctx;
        mdm = new MyDatabaseManager((Activity)ctx);
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        final Object o = objects.get(position);
        View lstItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        ((TextView) lstItem.findViewById(R.id.text_view_device_listView2)).setText(o.toString());
        final ImageView status= (ImageView) lstItem.findViewById(R.id.image_view_status_listView2);
        ImageView fixing = (ImageView) lstItem.findViewById(R.id.image_view_fix_listView2);
        ImageView borrow = (ImageView) lstItem.findViewById(R.id.image_view_borrow_listView2);

        switch (o.getStatus()){
            case "frei":
                status.setImageResource(R.drawable.circle_green);
                status.setContentDescription("Verfügbar");
                borrow.setVisibility(borrow.VISIBLE);
                fixing.setVisibility(borrow.VISIBLE);
                break;
            case "besetzt":
                status.setImageResource(R.drawable.circle_red);
                status.setContentDescription("Besetzt");
                borrow.setVisibility(borrow.INVISIBLE);
                fixing.setVisibility(borrow.INVISIBLE);

                break;
            case "reparatur":
                status.setImageResource(R.drawable.circle_orange);
                status.setContentDescription("Defekt");
                borrow.setVisibility(borrow.INVISIBLE);
                fixing.setVisibility(borrow.INVISIBLE);
                break;
        }
        status.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Status des Gerätes: "+status.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        fixing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mdm.updateStatus(o.getId(), "'reparatur'");
                Toast.makeText(ctx, "Geräte wurde auf Status Reparatur gesetzt.", Toast.LENGTH_SHORT).show();

            }
        });


        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdm.updateStatus(o.getId(), "'besetzt'");
                Toast.makeText(ctx, "Geräte wurde auf Status Ausgliehen gesetzt.", Toast.LENGTH_SHORT).show();
            }
        });

        return lstItem;
    }
}
