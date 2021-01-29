package com.example.trackingapp.ui.notifications;

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

import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.listmodel.DevicesListAdapter;
import com.example.trackingapp.listmodel.RepairListAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    SQLiteDatabase mydatabase;

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
        ListView lv = root.findViewById(R.id.listReparatur);
        mydatabase = getActivity().openOrCreateDatabase("TrackingDatabase", android.content.Context.MODE_PRIVATE, null);
        loadList(lv);
        return root;
    }

    public void loadList(ListView lv) {
        ArrayList<Object> arr = new ArrayList();
        Cursor resultset = mydatabase.rawQuery("Select * from Devices where status = 'reparatur'", null);
        while (resultset.moveToNext()) {
            Object o = new Object();
            o.setId(resultset.getInt(0));
            o.setName(resultset.getString(1));
            o.setInventoryNumber(resultset.getString(2));
            o.setStatus((resultset.getString(3)));
            arr.add(o);
        }
        RepairListAdapter myadapter = new RepairListAdapter(this.getActivity(), R.layout.list_view_repair, arr);
        lv.setAdapter(myadapter);
    }
}