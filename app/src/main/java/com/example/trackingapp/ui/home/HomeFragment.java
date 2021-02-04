package com.example.trackingapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.trackingapp.MainActivity;
import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;
import com.example.trackingapp.listmodel.DevicesListAdapter;
import com.example.trackingapp.ui.activities.show_device;


import java.io.Serializable;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView list;
    MyDatabaseManager mdm;
    private static HomeFragment instance;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        instance = this;
        list = root.findViewById(R.id.listUebersicht);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), show_device.class);
                intent.putExtra("object", (Serializable) list.getAdapter().getItem(position));
                startActivity(intent);
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        mdm = new MyDatabaseManager(getActivity());
        loadList();

        return root;
    }

    public void loadList() {
        ArrayList<Object> arr = new ArrayList();
        Cursor resultset = mdm.selectEverythingFromDevices();
        while (resultset.moveToNext()) {
            Object o = new Object();
            o.setId(resultset.getInt(0));
            o.setName(resultset.getString(1));
            o.setInventoryNumber(resultset.getString(2));
            o.setStatus((resultset.getString(3)));
            arr.add(o);
        }
        DevicesListAdapter myadapter = new DevicesListAdapter(this.getActivity(), R.layout.list_view_devices, arr);
        list.setAdapter(myadapter);
    }

    public static HomeFragment getInstance() {
        return instance;
    }
}