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
import android.widget.TextView;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.Reader.FestivalJsonReader;
import com.example.tadje.festival_app.model.Festival;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements BandsFragment
        .OnFragmentInterActionListener, MyBandsFragment.OnFragmentInterActionListener,
        MapsFragment.OnFragmentInterActionListener {

    SharedPreferences mPrefs;
    String[] listOfFiles;
    Spinner festivalSpinner;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = this.getApplicationContext().getSharedPreferences("myAppPrefs", 0);
        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(this.getApplicationContext());

        setToFragment(new InformationsStartFragment());

        startDialog();
//        if (this.getFirstRun()) {
//           this.setRunned();
//
//       } else {
//           fillTextViewWithFestival();
//       }


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

        final Context context = this;

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde

                                                  fileName = adapter.getItem(position).toString();

                                                  FestivalManager.getInstance().setFileName(fileName);

                                                  String[] name = fileName.split("\\.");

                                                  AppDatabase.getInstance().festivalDao()
                                                          .setSelected(true, (name[0]));


                                                  FestivalManager.getInstance();
                                                  new FestivalJsonReader().informationsForReader
                                                          (fileName, context);

                                                  fillTextViewWithFestival();
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


    public void fillTextViewWithFestival() {

        TextView festivalNameView = findViewById(R.id.textViewFestivalName);
        TextView festivalOrtView = findViewById(R.id.textViewFestivalOrt);
        TextView festivalDatum = findViewById(R.id.textViewFestivalDatum);

        java.util.Date festivalToDate = null;
        java.util.Date festivalFromDate = null;

        Festival festivalInformations = FestivalManager.getInstance().getSelectedFestival();

        if (festivalInformations == null) {
            festivalInformations = AppDatabase.getInstance().festivalDao()
                    .allFromSelectedFestival();

        }

        String festivalName = festivalInformations.getFestivalName();
        String festivalLocation = festivalInformations.getFestivalLocation();
        String festivalFrom = festivalInformations.getFestivalfrom();
        String festivalTo = festivalInformations.getFestivalto();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            festivalFromDate = simpleDateFormat.parse(festivalFrom);
            festivalToDate = simpleDateFormat.parse(festivalTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        daysBetweenDates(festivalFromDate, festivalToDate);

        festivalNameView.setText(festivalName);
        festivalOrtView.setText(festivalLocation);
        festivalDatum.setText(festivalFrom + "\n - \n" + festivalTo);

    }


    private void daysBetweenDates(java.util.Date festivalFromDate, java.util.Date festivalToDate) {

        Calendar calendarFrom = new GregorianCalendar(TimeZone.getTimeZone("Germany"));
        calendarFrom.setLenient(true);
        calendarFrom.setTime(festivalFromDate);
        calendarFrom.set(Calendar.HOUR, 0);
        calendarFrom.set(Calendar.MINUTE, 0);
        calendarFrom.set(Calendar.SECOND, 0);
        calendarFrom.set(Calendar.MILLISECOND, 0);


        Calendar calendarTo = new GregorianCalendar(TimeZone.getTimeZone("Germany"));
        calendarTo.setTime(festivalToDate);
        calendarTo.set(Calendar.HOUR, 0);
        calendarTo.set(Calendar.MINUTE, 0);
        calendarTo.set(Calendar.SECOND, 0);
        calendarTo.set(Calendar.MILLISECOND, 0);

        List<Integer> listOfWeekDays = new ArrayList<>();
        List<Calendar> listOfDates = new ArrayList<>();


        while (calendarFrom.getTimeInMillis() <= calendarTo.getTimeInMillis()) {

            int dayOfWeek = calendarFrom.get(Calendar.DAY_OF_WEEK);
            listOfWeekDays.add(dayOfWeek);

            Calendar clone = (Calendar) calendarFrom.clone();
            clone.add(Calendar.DATE, 1);
            listOfDates.add(clone);

            calendarFrom.add(Calendar.DATE, 1);
        }

        FestivalManager.getInstance().setListOfWeekdays(listOfWeekDays);
        FestivalManager.getInstance().setListOfDates(listOfDates);
    }

}

