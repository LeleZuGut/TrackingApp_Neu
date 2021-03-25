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
    private final static int DB_VERSION = 5;


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
        db.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer PRIMARY KEY, Name VARCHAR, Inventorynumber VARCHAR, Status VARCHAR, RepairMessage VARCHAR, firmenID Integer, FOREIGN KEY(firmenID) REFERENCES Firma(ID));");
        db.execSQL("INSERT INTO Devices VALUES (111111, 'Bosch Schlagbohrmaschine GSB 20-2', 'M432K32', 'besetzt', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111112, 'Makita Bohrhammer für SDS-PLUS 24 mm', 'HR2470', 'reparatur', 'Motor defekt', 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111113, 'Makita Bohrhammer HR2478', 'RJFKDN', 'frei', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111114, 'Bohrhammer GXB223', 'M84HFJI', 'reparatur', 'Wird extrem heiß', 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111115, 'Testobjekt', 'M84HFJI', 'frei', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111116, 'Akkubohrer 3D47f', 'M84HFJI', 'reparatur', 'Akuu muss getauscht werden', 3423);");

        db.execSQL("INSERT INTO Devices VALUES (111117, 'KS 12-1E ATSR - KÖNNER & SÖHNEN BENZIN GENERATOR', 'M84HFJI', 'frei', NULL, 4543);");
        db.execSQL("INSERT INTO Devices VALUES (111118, 'Champion Warrior LDG6500E DIESEL STROMERZEUGER', 'M84HFJI', 'frei', NULL, 4543);");

        db.execSQL("Drop Table if exists Users;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY, Username VARCHAR, Password VARCHAR, Email VARCHAR, firmenID Integer, FOREIGN KEY(firmenID) REFERENCES Firma(ID));");
        db.execSQL("INSERT INTO Users VALUES (1, 'admin', 'admin', 'admin@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (2, 'bgaubinger', 'admin', 'bgaubinger@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (3, 'jwimmer', 'admin', 'jwimmer@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (4, 'lberrer', 'admin', 'lberrer.müller@trackingApp.at', 4543);");

        db.execSQL("Drop Table if exists Firma;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Firma(ID Integer PRIMARY KEY, Name VARCHAR);");
        db.execSQL("INSERT INTO Firma VALUES (3423, 'Muster AG');");
        db.execSQL("INSERT INTO Firma VALUES (4543, 'Müller GmbH');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists Devices;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer PRIMARY KEY, Name VARCHAR, Inventorynumber VARCHAR, Status VARCHAR, RepairMessage VARCHAR, firmenID Integer, FOREIGN KEY(firmenID) REFERENCES Firma(ID));");
        db.execSQL("INSERT INTO Devices VALUES (111111, 'Bosch Schlagbohrmaschine GSB 20-2', 'M432K32', 'besetzt', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111112, 'Makita Bohrhammer für SDS-PLUS 24 mm', 'HR2470', 'reparatur', 'Motor defekt', 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111113, 'Makita Bohrhammer HR2478', 'RJFKDN', 'frei', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111114, 'Bohrhammer GXB223', 'M84HFJI', 'reparatur', 'Wird extrem heiß', 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111115, 'Testobjekt', 'M84HFJI', 'frei', NULL, 3423);");
        db.execSQL("INSERT INTO Devices VALUES (111116, 'Akkubohrer 3D47f', 'M84HFJI', 'reparatur', 'Akuu muss getauscht werden', 3423);");

        db.execSQL("INSERT INTO Devices VALUES (111117, 'KS 12-1E ATSR - KÖNNER & SÖHNEN BENZIN GENERATOR', 'M84HFJI', 'frei', NULL, 4543);");
        db.execSQL("INSERT INTO Devices VALUES (111118, 'Champion Warrior LDG6500E DIESEL STROMERZEUGER', 'M84HFJI', 'frei', NULL, 4543);");

        db.execSQL("Drop Table if exists Users;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY, Username VARCHAR, Password VARCHAR, Email VARCHAR, firmenID Integer, FOREIGN KEY(firmenID) REFERENCES Firma(ID));");
        db.execSQL("INSERT INTO Users VALUES (1, 'admin', 'admin', 'admin@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (2, 'bgaubinger', 'admin', 'bgaubinger@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (3, 'jwimmer', 'admin', 'jwimmer@trackingApp.at', 3423);");
        db.execSQL("INSERT INTO Users VALUES (4, 'lberrer', 'admin', 'lberrer.müller@trackingApp.at', 4543);");

        db.execSQL("Drop Table if exists Firma;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Firma(ID Integer PRIMARY KEY, Name VARCHAR);");
        db.execSQL("INSERT INTO Firma VALUES (3423, 'Muster AG');");
        db.execSQL("INSERT INTO Firma VALUES (4543, 'Müller GmbH');");
    }
}
