package com.example.trackingapp.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.trackingapp.listmodel.DevicesListAdapter;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView list;
    SQLiteDatabase mydatabase;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list = root.findViewById(R.id.listUebersicht);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        mydatabase = getActivity().openOrCreateDatabase("TrackingDatabase", android.content.Context.MODE_PRIVATE, null);
       loadList(list);

        return root;


    }

    public void loadList(ListView lv) {
        ArrayList<Object> arr = new ArrayList();
        Cursor resultset = mydatabase.rawQuery("Select * from Devices", null);
        while (resultset.moveToNext()) {
            Object o = new Object();
            o.setId(resultset.getInt(0));
            o.setName(resultset.getString(1));
            o.setInventoryNumber(resultset.getString(2));
            o.setStatus((resultset.getString(3)));
            arr.add(o);
        }
        DevicesListAdapter myadapter = new DevicesListAdapter(this.getActivity(), R.layout.list_view_devices, arr);
        lv.setAdapter(myadapter);
    }

}