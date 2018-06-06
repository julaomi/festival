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

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Festival;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


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

        Context context = this;



        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);



//        if (this.getFirstRun()) {
//            this.setRunned();
//            firstStartDialog();
//        } else {
//            fillTextViewWithFestival();
//        }
        firstStartDialog();


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
                                                  String fileName = adapter.getItem(position).toString(); //declaration with the
                                                  FestivalManager.getInstance().setFileName(fileName);
                                                  FestivalManager.getInstance().setListFrom(position);
                                                  // position of Spinner
                                                  FestivalManager.getInstance();
                                                  new FestivalJsonReader().informationsForReader
                                                          (fileName, context);
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
        Date festivalToDate = null;
        Date festivalFromDate = null;

        int position = FestivalManager.getInstance().getListFrom() + 1;

        List<Festival> festivalInformations = AppDatabase.getInstance().festivalDao().getAll();

        String festivalName = festivalInformations.get(position).getFestivalName();
        String festivalLocation = festivalInformations.get(position).getFestivalLocation();
        String festivalFrom = festivalInformations.get(position).getFestivalfrom();
        String festivalTo = festivalInformations.get(position).getFestivalto();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            festivalFromDate = (Date) simpleDateFormat.parse(festivalFrom);
            festivalToDate = (Date) simpleDateFormat.parse(festivalTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        daysBetweenDates(festivalFromDate, festivalToDate);

        festivalNameView.setText(festivalName);
        festivalOrtView.setText(festivalLocation);
        festivalDatum.setText(festivalFromDate + " \n - \n " + festivalToDate);
    }

    private void daysBetweenDates(Date festivalFromDate, Date festivalToDate) {

        Calendar calendarFrom = new GregorianCalendar();
        calendarFrom.setTime(festivalFromDate);

        Calendar calendarTo = new GregorianCalendar();
        calendarTo.setTime(festivalToDate);

        int festivalDays = 0;
        List<Integer> listOfWeekDays = null;

        while (calendarTo.after(calendarFrom)) {

            int dayOfWeek = calendarFrom.get(Calendar.DAY_OF_WEEK);
            listOfWeekDays.add(dayOfWeek);
            festivalDays++;

            calendarFrom.add(calendarFrom.DATE, 1);
        }
        FestivalManager.getInstance().setFestivalDays(festivalDays);
        FestivalManager.getInstance().setListOfWeekdays(listOfWeekDays);

    }

}