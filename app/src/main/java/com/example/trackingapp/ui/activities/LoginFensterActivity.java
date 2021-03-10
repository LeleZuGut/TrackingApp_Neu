package com.example.trackingapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;
import com.example.trackingapp.model.Users;
import com.google.gson.Gson;

import java.io.Serializable;

public class LoginFensterActivity extends AppCompatActivity {
    EditText edit_text_benutzername, edit_text_passwort;
    TextView error;
    Button einloggen;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fenster);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        edit_text_benutzername = findViewById(R.id.editText_Register_username);
        edit_text_passwort = findViewById(R.id.editText_Register_Password);
        edit_text_benutzername.addTextChangedListener(mytextwatcher);
        edit_text_passwort.addTextChangedListener(mytextwatcher);
        einloggen = findViewById(R.id.button_Login);
        error = findViewById(R.id.text_view_error);
        MyDatabaseManager mdm = new MyDatabaseManager(this);
        db = mdm.getReadableDatabase();
        einloggen.setEnabled(false);
    }

    private TextWatcher mytextwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            error.setText("");
            String usernameInput = edit_text_benutzername.getText().toString().trim();
            String passwordInput = edit_text_passwort.getText().toString().trim();
            einloggen.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
//            if(einloggen.isEnabled()){
//                einloggen.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void handleButtonEinloggen(View view) {
        String vorname = "";
        try {
            vorname = edit_text_benutzername.getText().toString();
            String password = edit_text_passwort.getText().toString();
            Cursor resultSet = db.rawQuery("Select * from Users where Username = '" + vorname + "' and Password = '" + password + "'", null);
            resultSet.moveToFirst();
            Log.i("Lolo", resultSet.getString(0));

            if (vorname.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                Users u = new Users(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                Gson gson = new Gson();
                String json = gson.toJson(u);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginFensterActivity.this);
                prefs.edit().putString("user", json).commit();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("signedinUser", (Serializable) u);
                startActivity(intent);
            } else {
                error.setText("Upps, beim Login ist etwas schiefgelaufen. \n  Passwort oder Username sind falsch.");
            }
        }catch(CursorIndexOutOfBoundsException ci)
        {
            error.setText("Upps, beim Login ist etwas schiefgelaufen. \n  Passwort oder Username sind falsch.");
        }

    }

    public void handleButtonRegistrieren(View view) {
        Intent intent = new Intent(this, RegisterinFensterActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}