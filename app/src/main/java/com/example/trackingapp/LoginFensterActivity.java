package com.example.trackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginFensterActivity extends AppCompatActivity {
    EditText e1,e2;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fenster);
        e1 = findViewById(R.id.editText_Register_username);
        e2 = findViewById(R.id.editText_Register_Password);
        mydatabase = openOrCreateDatabase("TrackingDatabase",MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Users(ID Integer,Username VARCHAR, Password VARCHAR, Email VARCHAR);");


    }

    public void handleButtonEinloggen(View view)
    {
        try {


            String vorname = e1.getText().toString();
            String password = e2.getText().toString();
            Cursor resultSet = mydatabase.rawQuery("Select * from Users where Username = '" + vorname + "' and Password = '" + password + "'", null);
            resultSet.moveToFirst();

            Log.i("Lolo", resultSet.getString(0));


            if (vorname.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast t = Toast.makeText(this, "Falsche Eingaben", Toast.LENGTH_LONG);

                t.show();
            }
        }catch(CursorIndexOutOfBoundsException ci)
        {
            Toast t = Toast.makeText(this, "Falsche Eingaben", Toast.LENGTH_LONG);

            t.show();
        }
    }

    public void handleButtonRegistrieren(View view)
    {
        Intent intent = new Intent(this,RegisterinFensterActivity.class);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }


}