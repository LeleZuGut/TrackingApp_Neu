package com.example.trackingapp.database;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trackingapp.ui.home.HomeFragment;

public class MyDatabaseManager {

    SQLiteDatabase mydatabase;
    Activity activity;

    public MyDatabaseManager(Activity activity) {
        this.activity = activity;
        this.mydatabase = activity.openOrCreateDatabase("TrackingDatabase", android.content.Context.MODE_PRIVATE, null);
    }

    public Cursor selectEverythingFromDevices(){
        //mydatabase.execSQL("Update Devices set Status = 'besetzt' where ID = 111112", null);
        return  mydatabase.rawQuery("Select * from Devices", null);
    }

    public Cursor selectPart(String where){
        return  mydatabase.rawQuery("Select * from Devices where "+where, null);
    }

    public boolean updateStatus(int id, String status){
        mydatabase.execSQL("Update Devices set Status = "+ status +" where ID = "+id);
        HomeFragment.getInstance().loadList();
        return true;
    }
}
