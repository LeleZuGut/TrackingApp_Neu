package com.example.trackingapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackingapp.R;

public class show_user extends AppCompatActivity {

    TextView username, email, password;
    Button zurück;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_person);

        username = findViewById(R.id.text_view_benutzername_show_user);
        email = findViewById(R.id.text_view_email_show_benutzer);
        password = findViewById(R.id.text_view_email_show_benutzer);

        zurück = findViewById(R.id.button_zurück_show_benutzer);
        zurück.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}