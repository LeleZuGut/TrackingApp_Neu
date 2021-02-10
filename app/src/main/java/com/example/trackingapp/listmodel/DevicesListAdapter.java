package com.example.trackingapp.listmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.Locale;

public class DevicesListAdapter extends BaseAdapter implements Filterable {
    private List<Object> objects = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;
    Context ctx;
    SQLiteDatabase db;
    private List<Object> mOriginalValues;

    public DevicesListAdapter(Context ctx, int layoutId, List<Object> objects) {
        this.objects = objects;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ctx = ctx;
        MyDatabaseManager mdm = new MyDatabaseManager(ctx);
        db = mdm.getReadableDatabase();
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        final Object o = objects.get(position);
        View lstItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        ((TextView) lstItem.findViewById(R.id.text_view_device_listView2)).setText(o.toString());
        final ImageView status = (ImageView) lstItem.findViewById(R.id.image_view_status_listView2);
        ImageView fixing = (ImageView) lstItem.findViewById(R.id.image_view_fix_listView2);
        ImageView borrow = (ImageView) lstItem.findViewById(R.id.image_view_borrow_listView2);

        switch (o.getStatus()) {
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
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Status des Gerätes: " + status.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        fixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText repairmessage = new EditText(ctx);
                repairmessage.setHint("Was ist kaputt?");
                new AlertDialog.Builder(ctx)
                        .setMessage("Gerät " + o.getName() + " auf Reparatur setzen?")
                        .setPositiveButton("JA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.execSQL("Update Devices set Status = 'reparatur' where ID = " + o.getId());
                                HomeFragment.getInstance().loadList();
                                Toast.makeText(ctx, "Geräte wurde auf Status Reparatur gesetzt.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setView(repairmessage).show();

            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Gerät " + o.getName() + " wirklich buchen?")
                        .setPositiveButton("BUCHEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.execSQL("Update Devices set Status = 'besetzt' where ID = " + o.getId());
                                HomeFragment.getInstance().loadList();
                                Toast.makeText(ctx, "Geräte wurde auf Status Ausgliehen gesetzt.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        return lstItem;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                objects = (ArrayList<Object>) results.values; // has

                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                List<Object> FilteredArrList = new ArrayList<Object>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Object>(objects);

                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    Locale locale = Locale.getDefault();
                    constraint = constraint.toString().toLowerCase(locale);
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        Object model = mOriginalValues.get(i);

                        String data = model.getName();
                        if (data.toLowerCase(locale).contains(constraint.toString())) {

                            FilteredArrList.add(model);
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;
            }
        };
        return filter;
    }
}

