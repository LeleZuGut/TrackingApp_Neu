package com.example.trackingapp.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.trackingapp.ui.home.HomeFragment;

public class MyDatabaseManager extends SQLiteOpenHelper {

    SQLiteDatabase mydatabase;
    Activity activity;

    private final static String DB_NAME = "TrackingDatabase";
    private final static int DB_VERSION = 1;


    public MyDatabaseManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Cursor selectEverythingFromDevices(){
        //mydatabase.execSQL("Update Devices set Status = 'besetzt' where ID = 111112", null);
        return  mydatabase.rawQuery("Select * from Devices", null);
    }

    public Cursor selectPart(String where){
        return  mydatabase.rawQuery("Select * from Devices where "+where, null);
    }

    public boolean updateStatus(int id, String status, String update){
        mydatabase.execSQL("Update Devices set "+ update + " = "+ status +" where ID = "+id);
        HomeFragment.getInstance().loadList();
        return true;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Drop Table if exists Devices;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer,Name VARCHAR, Inventorynumber VARCHAR, Status VARCHAR, RepairMessage VARCHAR);");
        db.execSQL("INSERT INTO Devices VALUES (111111, 'Bosch Schlagbohrmaschine GSB 20-2', 'M432K32', 'besetzt', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111112, 'Makita Bohrhammer für SDS-PLUS 24 mm', 'HR2470', 'reparatur', 'Motor defekt');");
        db.execSQL("INSERT INTO Devices VALUES (111113, 'Makita Bohrhammer HR2478', 'RJFKDN', 'frei', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111114, 'Bohrhammer GXB223', 'M84HFJI', 'reparatur', 'Wird extrem heiß');");
        db.execSQL("INSERT INTO Devices VALUES (111115, 'Testobjekt', 'M84HFJI', 'frei', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111116, 'Akkubohrer 3D47f', 'M84HFJI', 'reparatur', 'Akuu muss getauscht werden');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists Devices;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer,Name VARCHAR, Inventorynumber VARCHAR, Status VARCHAR, RepairMessage VARCHAR);");
        db.execSQL("INSERT INTO Devices VALUES (111111, 'Bosch Schlagbohrmaschine GSB 20-2', 'M432K32', 'besetzt', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111112, 'Makita Bohrhammer für SDS-PLUS 24 mm', 'HR2470', 'reparatur', 'Motor defekt');");
        db.execSQL("INSERT INTO Devices VALUES (111113, 'Makita Bohrhammer HR2478', 'RJFKDN', 'frei', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111114, 'Bohrhammer GXB223', 'M84HFJI', 'reparatur', 'Wird extrem heiß');");
        db.execSQL("INSERT INTO Devices VALUES (111115, 'Testobjekt', 'M84HFJI', 'frei', NULL);");
        db.execSQL("INSERT INTO Devices VALUES (111116, 'Akkubohrer 3D47f', 'M84HFJI', 'reparatur', 'Akuu muss getauscht werden');");
    }
}
