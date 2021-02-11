package com.example.trackingapp.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackingapp.CaptureAct;
import com.example.trackingapp.MainActivity;
import com.example.trackingapp.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.model.statusEnum;
import com.example.trackingapp.spinnermodel.StatusSpinnerAdapter;
import com.example.trackingapp.ui.home.HomeFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class show_device extends AppCompatActivity {

    Object o;
    TextView name, inventorynumber, repairmessage;
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
        repairmessage = findViewById(R.id.text_view_showdevice_repairmessage);
        spinner = (Spinner) findViewById(R.id.spinner_show_device_status);
        speichern = findViewById(R.id.button_showdevice_speichern);
        zurück = findViewById(R.id.button_showdevice_zurück);

        String[] statuse = {"Verfügbar", "Besetzt", "Defekt"};
        StatusSpinnerAdapter myAdapter = new StatusSpinnerAdapter(this, statuse);
        spinner.setAdapter(myAdapter);
        int spinnerposition  = 0;
        switch (o.getStatus()){
            case "frei":
                spinnerposition = 0;
                break;
            case "besetzt":
                spinnerposition = 1;
                break;
            case "reparatur":
                spinnerposition = 2;
                break;
        }
        spinner.setSelection(spinnerposition);


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
        inventorynumber.setText(inventorynumber.getText().toString()+" "+o.getInventoryNumber());
        repairmessage.setText(o.getRepairmessage());
    }



}