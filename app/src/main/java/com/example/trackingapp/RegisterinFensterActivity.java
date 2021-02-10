package com.example.trackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterinFensterActivity extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    Button b1,b2;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydatabase = openOrCreateDatabase("TrackingDatabase",MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Users(ID Integer,Username VARCHAR, Password VARCHAR, Email VARCHAR);");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fenster);

        e1 = findViewById(R.id.editText_Register_username);
        e2 = findViewById(R.id.editText_Register_Password);
        e3 = findViewById(R.id.edittext_Register_maill);
        e4 = findViewById(R.id.editText_Register_Passwordbestätigung);

        b1 = findViewById(R.id.button_Register);
        b2 = findViewById(R.id.button_RegistertoLogin);



    }

    public void handleButtonRegister(View view)
    {

        Cursor resultset = mydatabase.rawQuery("Select Max(id) from Users",null);
        resultset.moveToFirst();
        int i = resultset.getInt(0);
        i++;
        String Username = e1.getText().toString();
        String email = e3.getText().toString();
        String password= e2.getText().toString();
        String passwordbestätigung = e4.getText().toString();
        if (Username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordbestätigung.isEmpty())
        {
            Toast t = Toast.makeText(this, "Gib bitte in allen Eingabfeldern was ein", Toast.LENGTH_LONG);
            t.show();
        }
        else
        {
            if(password.equals(passwordbestätigung))
            {
                mydatabase.execSQL("Insert into Users Values("+ i + ",'" + Username + "','"+ password + "','" + email +"');");

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast t = Toast.makeText(this, "Bestätige dein Passwort richtig", Toast.LENGTH_LONG);
                t.show();
            }

        }



    }

    public void handleButtonRegistertoLogin(View view)
    {
        Intent intent = new Intent(this, LoginFensterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
