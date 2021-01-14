package com.example.trackingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.trackingapp.listmodel.DevicesListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button scanBtn;
    ArrayList<Object> arr = new ArrayList<>();
    ListView listView;
    SQLiteDatabase mydatabase;
    DevicesListAdapter myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Datenbank für Geräte
        mydatabase = openOrCreateDatabase("TrackingDatabase", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer,Name VARCHAR, InventoryNumber VARCHAR, Status VARCHAR);");
        mydatabase.execSQL("Insert into Devices Values(" + 111111 + ",'" + "Bosch Schlagbohrmaschine GSB 20-2" + "','" + "M432K32" + "','" + "frei" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111112 + ",'" + "Bosch Professional Schlagbohrmaschine GSB 13 RE" + "','" + "M432K33" + "','" + "frei" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111112 + ",'" + "Bosch Professional Schlagbohrmaschine GSB 14 RE" + "','" + "M432K34" + "','" + "frei" + "');");



        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        listView = findViewById(R.id.listUebersicht);
        scanBtn = findViewById(R.id.scanBtn);

        loadList();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_ausloggen:
                Intent in = new Intent(this, LoginFensterActivity.class);
                startActivity(in);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_logout, menu);
        return true;
    }


    public void scanBtnClicked(View view) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(result.getContents());
//                builder.setTitle("Scanning Result");
//                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        scanCode();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();

//                String[] res = result.getContents().split(";");
//                Object object = new Object(res[0], res[1]);
//                arr.add(object);
//                bindAdapter(listView);
            } else {
                Toast.makeText(this, "no Result", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void loadList() {
        Cursor resultset = mydatabase.rawQuery("Select * from Devices", null);
        while (resultset.moveToNext()) {
            Object o = new Object();
            o.setId(resultset.getInt(0));
            o.setName(resultset.getString(1));
            o.setInventoryNumber(resultset.getString(2));
            o.setStatus((resultset.getString(3)));
            arr.add(o);
        }
        //bindAdapterToList();
        Toast.makeText(this, "Alles wurde geladen", Toast.LENGTH_SHORT).show();
    }

    public void bindAdapterToList() {
       myadapter = new DevicesListAdapter(this, R.layout.list_view_devices, arr);
       listView.setAdapter(myadapter);
    }
}