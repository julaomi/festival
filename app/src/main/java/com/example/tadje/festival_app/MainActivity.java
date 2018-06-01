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
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    int festivalPosition;


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


        Spinner festivalSpinner = findViewById(R.id.spinnerFirstStart);
        festivalPosition = festivalSpinner.getSelectedItemPosition();

        firstStartDialog.setPositiveButton("GO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fillTextViewWithFestival();
            }
        });
        firstStartDialog.show();


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