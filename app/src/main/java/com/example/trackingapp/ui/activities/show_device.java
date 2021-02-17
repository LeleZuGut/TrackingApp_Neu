package com.example.trackingapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trackingapp.model.Object;
import com.example.trackingapp.R;
import com.example.trackingapp.database.MyDatabaseManager;
import com.example.trackingapp.spinnermodel.StatusSpinnerAdapter;
import com.example.trackingapp.ui.home.HomeFragment;

public class show_device extends AppCompatActivity {

    Object o;
    TextView name, inventorynumber, repairmessage;
    Spinner spinner;
    Button speichern, zurück;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_device);
        Intent i = getIntent();

        MyDatabaseManager mdm = new MyDatabaseManager(this);
        db = mdm.getReadableDatabase();

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
        int spinnerposition = 0;
        switch (o.getStatus()) {
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
                speichern();
            }
        });

        setValues(o);
    }

    private void setValues(Object o) {
        name.setText(o.getName());
        inventorynumber.setText(inventorynumber.getText().toString() + " " + o.getInventoryNumber());
        repairmessage.setText(o.getRepairmessage());
    }

    public void speichern() {
        String selection = "";
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                selection = "'frei'";
                break;
            case 1:
                selection = "'besetzt'";
                break;
            case 2:
                selection = "'reparatur'";
                break;

            default:
                selection = "'reparatur'";
                break;
        }
        db.execSQL("Update Devices set Status = "+ selection + " where ID = " + o.getId());
        finish();
        HomeFragment.getInstance().loadList();

    }


}