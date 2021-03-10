package com.example.trackingapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackingapp.R;

import java.nio.file.Files;

public class RegisterinFensterActivity extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    Button b1, b2;
    SQLiteDatabase mydatabase;
    TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mydatabase = openOrCreateDatabase("TrackingDatabase", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Users(ID Integer,Username VARCHAR, Password VARCHAR, Email VARCHAR);");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fenster);

        e1 = findViewById(R.id.editText_Register_username);
        e2 = findViewById(R.id.editText_Register_Password);
        e3 = findViewById(R.id.edittext_Register_maill);
        e4 = findViewById(R.id.editText_Register_Passwordbestätigung);

        b1 = findViewById(R.id.button_Register);
        b2 = findViewById(R.id.button_RegistertoLogin);

        error = findViewById(R.id.text_view_error_register);


    }

    public void handleButtonRegister(View view) {
        String Username;

        Cursor resultset = mydatabase.rawQuery("Select Max(id) from Users", null);
        resultset.moveToFirst();
        int i = resultset.getInt(0);
        i++;
        Username = e1.getText().toString();
        String email = e3.getText().toString();
        String password = e2.getText().toString();
        String passwordbestätigung = e4.getText().toString();
        if (Username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordbestätigung.isEmpty()) {
            Toast t = Toast.makeText(this, "Gib bitte in allen Eingabfeldern was ein", Toast.LENGTH_LONG);
            t.show();
        } else {
            if (password.equals(passwordbestätigung)) {
                mydatabase.execSQL("Insert into Users Values(" + i + ",'" + Username + "','" + password + "','" + email + "');");

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                error.setText("Passwort stimmen nicht überein");
            }

        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegisterinFensterActivity.this);
        prefs.edit().putString("user", Username.toString()).commit();


    }

    public void handleButtonRegistertoLogin(View view) {
        Intent intent = new Intent(this, LoginFensterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
