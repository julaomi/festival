package com.example.tadje.festival_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.Reader.FestivalJsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BandsFragment
        .OnFragmentInterActionListener, MyBandsFragment.OnFragmentInterActionListener,
        MapsFragment.OnFragmentInterActionListener {

    SharedPreferences mPrefs;
    String[] listOfFiles;
    Spinner festivalSpinner;
    String fileName;
    private IFestivalSelectedCallback festivalSelectedCallback;
    List<String> listOfNames = new ArrayList<>();


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

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#734C80")));


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
                                fragment = new InformationsStartFragment();
                                break;
                            case R.id.Camera:
                                fragment = new InformationsStartFragment();
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

            for (int i = 0; i < listOfFiles.length; i++){
                String [] fileNameSplit = listOfFiles[i].split("\\.");
                listOfNames.add(fileNameSplit[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
            listOfFiles = new String[0];
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(),
                android.R.layout.simple_spinner_item, listOfNames);

        final Context context = this;

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde
                                                  ((TextView)parent.getChildAt(0)).setTextColor(Color
                                                          .WHITE);

                                                  fileName = adapter.getItem(position).toString();

                                                  FestivalManager.getInstance().setFileName(fileName);

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

