package com.example.trackingapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trackingapp.MainActivity;
import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.model.statusEnum;
import com.example.trackingapp.spinnermodel.StatusSpinnerAdapter;
import com.example.trackingapp.ui.home.HomeFragment;

public class show_device extends AppCompatActivity {

    Object o;
    TextView name, inventorynumber, status;
    Spinner spinner;
    Button speichern, zurück;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_device);
        Intent i = getIntent();

        o = (Object) i.getSerializableExtra("object");
        name = findViewById(R.id.text_view_showdevice_name);
        inventorynumber = findViewById(R.id.text_view_showdevice_inventoryNumber);
        status = findViewById(R.id.text_view_showdevice_status);
        spinner = (Spinner) findViewById(R.id.spinner_show_device_status);
        speichern = findViewById(R.id.button_showdevice_speichern);
        zurück = findViewById(R.id.button_showdevice_zurück);

        String[] statuse = {"Verfügbar", "Besetzt", "Defekt"};
        StatusSpinnerAdapter myAdapter = new StatusSpinnerAdapter(this, statuse);
        spinner.setAdapter(myAdapter);

        zurück.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setValues(o);
    }

    private void setValues(Object o) {
        name.setText(o.getName());
        inventorynumber.setText(o.getInventoryNumber());
        status.setText(o.getStatus());
    }


}