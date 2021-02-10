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

import com.example.trackingapp.database.MyDatabaseManager;
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
    SQLiteDatabase db;
    BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigationbar bereitstellen
        navView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        navView.setSelectedItemId(R.id.navigation_home);
        navView.setOnNavigationItemSelectedListener(this);



        MyDatabaseManager mdm = new MyDatabaseManager(this);
        db = mdm.getReadableDatabase();

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
                scanCode();
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;
        }

        return loadFragment(fragment);
    }

    //QR Code

    private void scanCode() {
        
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Gerät scannen");
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Object o = new Object();
        Cursor resultset = null;
        try{
             resultset = db.rawQuery("Select * from Devices where id = " + result.getContents(), null);
        }catch (Exception e){
            Toast.makeText(this, "Gerät konnte nicht gefunden werden.", Toast.LENGTH_SHORT).show();
            return;
        }

        resultset.moveToFirst();
        if(resultset.getCount()==0){
            return;
        }
        o.setId(resultset.getInt(0));
        o.setName(resultset.getString(1));
        o.setInventoryNumber(resultset.getString(2));
        o.setStatus((resultset.getString(3)));
        o.setRepairmessage(resultset.getString(4));

        if (o.getStatus().equals("frei")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Gerät " +o.getName() +" buchen?");
            builder.setPositiveButton("Buchen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.execSQL("Update Devices set Status = 'besetzt' where ID = " + result.getContents());
                    try{
                        HomeFragment.getInstance().loadList();
                    }catch (Exception e){
                        NotificationsFragment.getInstance().loadList();
                    }
                }
            });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


        } else if (o.getStatus().equals("besetzt")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Gerät freigeben?");
            builder.setPositiveButton("Freigeben", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.execSQL("Update Devices set Status = 'frei' where ID = " + result.getContents());
                    try{
                        HomeFragment.getInstance().loadList();
                    }catch (Exception e){
                        NotificationsFragment.getInstance().loadList();
                    }
                }
            });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(o.getStatus().equals("reparatur")){
            Toast.makeText(this, "Gerät leider in Reparatur", Toast.LENGTH_SHORT).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
        System.exit(0);
    }
}