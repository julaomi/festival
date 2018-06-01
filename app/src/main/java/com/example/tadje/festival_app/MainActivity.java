package com.example.tadje.festival_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    int festivalPosition;
    String[] listOfFiles;
    Spinner festivalSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context mContext = this.getApplicationContext();
        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

        if (this.getFirstRun()) {
            this.setRunned();
            firstStartDialog();
        } else {
            fillTextViewWithFestival();
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.yourBands:
                                return true;
                            case R.id.bands:
                                return true;
                            case R.id.Map:
                                return true;
                            case R.id.Calender:
                                return true;
                            case R.id.Camera:
                                return true;
                        }

                        return false;
                    }
                });


    }

    private void firstStartDialog() {

        AlertDialog.Builder firstStartDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogViewFirstStart = inflater.inflate(R.layout.first_start_dialog,
                null);
        firstStartDialog.setView(dialogViewFirstStart);


        festivalSpinner = findViewById(R.id.spinnerFirstStart);
        populateFileSpinner(dialogViewFirstStart, festivalSpinner);

        festivalPosition = festivalSpinner.getSelectedItemPosition();


        firstStartDialog.setPositiveButton("GO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fillTextViewWithFestival();
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


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde
                                                  String fileName = adapter.getItem(position).toString(); //declaration with the
                                                  FestivalManager.getInstance().setFileName(fileName);
                                                  FestivalManager.getInstance().setListFrom(position);
                                                  // position of Spinner
                                                  FestivalManager.getInstance();
                                                  new FestivalJsonReader().informationsForReader
                                                          (fileName);
                                              }


                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) { //wenn nichts ausgewählt wurde
                                                firstStartDialog();
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

    public void fillTextViewWithFestival() {
        TextView festivalNameView = findViewById(R.id.textViewFestivalName);
        TextView festivalOrtView = findViewById(R.id.textViewFestivalOrt);
        TextView festivalDatum = findViewById(R.id.textViewFestivalDatum);

        festivalNameView.setText("Hurricane");
        festivalOrtView.setText("Scheeßel");
        festivalDatum.setText("21.06. \n - \n 24.06.2018");
    }

}