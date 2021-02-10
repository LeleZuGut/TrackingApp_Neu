package com.example.trackingapp.ui.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;
import com.example.trackingapp.listmodel.DevicesListAdapter;
import com.example.trackingapp.listmodel.RepairListAdapter;
import com.example.trackingapp.ui.activities.show_device;
import com.example.trackingapp.ui.home.HomeFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    ListView list;
    SearchView search;
    SQLiteDatabase db;
    private static NotificationsFragment instance;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        list = root.findViewById(R.id.listReparatur);
        search = root.findViewById(R.id.search_view_repair);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RepairListAdapter tmpadapter = (RepairListAdapter) list.getAdapter();
                tmpadapter.getFilter().filter(newText);
                list.setAdapter(tmpadapter);
                return true;
            }
        });
        instance = this;
        MyDatabaseManager mdm = new MyDatabaseManager(getActivity());
        db = mdm.getReadableDatabase();
        loadList();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), show_device.class);
                intent.putExtra("object", (Serializable) list.getAdapter().getItem(position));
                startActivity(intent);
            }
        });
        return root;
    }

    public void loadList() {
        ArrayList<Object> arr = new ArrayList();
        Cursor resultset = db.rawQuery("Select * from Devices where status = 'reparatur' order by name", null);
        while (resultset.moveToNext()) {
            Object o = new Object();
            o.setId(resultset.getInt(0));
            o.setName(resultset.getString(1));
            o.setInventoryNumber(resultset.getString(2));
            o.setStatus((resultset.getString(3)));
            o.setRepairmessage(resultset.getString(4));
            arr.add(o);
        }
        RepairListAdapter myadapter = new RepairListAdapter(this.getActivity(), R.layout.list_view_repair_2, arr);
        list.setAdapter(myadapter);
    }

    public static NotificationsFragment getInstance() {
        return instance;
    }
}