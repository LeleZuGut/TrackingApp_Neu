package com.example.trackingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trackingapp.listmodel.DevicesListAdapter;
import com.example.trackingapp.ui.dashboard.DashboardFragment;
import com.example.trackingapp.ui.home.HomeFragment;
import com.example.trackingapp.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button scanBtn;
    SQLiteDatabase mydatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Datenbank für Geräte
        mydatabase = openOrCreateDatabase("TrackingDatabase", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Devices(ID Integer,Name VARCHAR, Inventorynumber VARCHAR, Status VARCHAR);");
        mydatabase.execSQL("DELETE FROM Devices;");
        mydatabase.execSQL("Insert into Devices Values(" + 111111 + ",'" + "Bosch Schlagbohrmaschine GSB 20-2" + "','" + "M432K32" + "','" + "frei" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111112 + ",'" + "Bosch Professional Schlagbohrmaschine GSB 13 RE" + "','" + "M432K33" + "','" + "frei" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111113 + ",'" + "Bosch Professional Schlagbohrmaschine GSB 14 RE" + "','" + "M432K34" + "','" + "frei" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111114 + ",'" + "Makita Bohrhammer für SDS-PLUS 24 mm HR2470" + "','" + "M432K34" + "','" + "besetzt" + "');");
        mydatabase.execSQL("Insert into Devices Values(" + 111115 + ",'" + "Makita Bohrhammer HR2478" + "','" + "M432K34" + "','" + "reparatur" + "');");
        mydatabase.execSQL("INSERT INTO Devices VALUES (111116, 'Testobjekt', 'M84HFJI', 'besetzt');");

        //Navigationbar bereitstellen
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        navView.setSelectedItemId(R.id.navigation_dashboard);
        navView.setOnNavigationItemSelectedListener(this);


        scanBtn = findViewById(R.id.scanBtn);

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


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                //ViewGroup vg = ((ViewGroup)fragment.getView().getParent()).getId();
                //+
                // View root = fragment.getLayoutInflater().inflate(R.layout.fragment_home, , false);
                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;
        }

        return loadFragment(fragment);
    }
}