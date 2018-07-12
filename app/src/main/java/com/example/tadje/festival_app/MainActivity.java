package com.example.tadje.festival_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.Reader.FestivalJsonReader;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements BandsFragment
        .OnFragmentInterActionListener, MyBandsFragment.OnFragmentInterActionListener,
        MapsFragment.OnFragmentInterActionListener {

    SharedPreferences mPrefs;
    String[] listOfFiles;
    Spinner festivalSpinner;
    String fileName;
    private IFestivalSelectedCallback festivalSelectedCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = this.getApplicationContext().getSharedPreferences("myAppPrefs", 0);
        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(this.getApplicationContext());
        Fragment startingFragment = new InformationsStartFragment();
        this.festivalSelectedCallback = (IFestivalSelectedCallback) startingFragment;
        setToFragment(startingFragment);


        if (this.getFirstRun()) {
            this.setRunned();
            startDialog();

        } else {
            if (festivalSelectedCallback != null) {
                festivalSelectedCallback.onFestivalSelected(getApplicationContext());
            }
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.yourBands:
                                fragment = new MyBandsFragment();
                                break;
                            case R.id.bands:
                                fragment = new BandsFragment();
                                break;
                            case R.id.Map:
                                fragment = new MapsFragment();
                                break;
                            case R.id.Calender:
                                break;
                            case R.id.Camera:
                                break;
                        }

                        return setToFragment(fragment);
                    }
                });
    }


    private boolean setToFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return true;
    }


    private void startDialog() {

        AlertDialog.Builder firstStartDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogViewFirstStart = inflater.inflate(R.layout.first_start_dialog,
                null);
        firstStartDialog.setView(dialogViewFirstStart);


        festivalSpinner = dialogViewFirstStart.findViewById(R.id.spinnerFirstStart);

        populateFileSpinner(dialogViewFirstStart, festivalSpinner);

        firstStartDialog.setPositiveButton("GO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                new FestivalJsonReader().informationsForReader(fileName, getApplicationContext());

                if (MainActivity.this.festivalSelectedCallback != null) {

                    MainActivity.this.festivalSelectedCallback.onFestivalSelected(getApplicationContext());
                }
            }
        });
        firstStartDialog.show();


    }

    private void populateFileSpinner(View view, Spinner spinner) {
        // List of Existing Files for the Spinner with Adapter

        try {
            listOfFiles = this.getAssets().list("festivals");


        } catch (IOException e) {
            e.printStackTrace();
            listOfFiles = new String[0];
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(),
                android.R.layout.simple_spinner_item, listOfFiles);

        final Context context = this;

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde

                                                  fileName = adapter.getItem(position).toString();

                                                  FestivalManager.getInstance().setFileName(fileName);

                                                  String[] name = fileName.split("\\.");

                                              }


                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) { //wenn nichts ausgewählt wurde
                                                  startDialog();
                                              }
                                          }
        );
    }

    public boolean getFirstRun() {
        return mPrefs.getBoolean("firstRun", true);
    }

    public void setRunned() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean("firstRun", false);
        edit.commit();
    }


}

