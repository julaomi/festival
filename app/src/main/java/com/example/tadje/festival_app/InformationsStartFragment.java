package com.example.tadje.festival_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Festival;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by tadje on 11.06.2018.
 */

@SuppressLint("ValidFragment")
public class InformationsStartFragment extends Fragment implements IFestivalSelectedCallback {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.fragmentView == null) {

            setupView(inflater);
        }

        return this.fragmentView;
    }

    private void setupView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.start_festival_fragment, null, false);
        this.fragmentView = view;
    }

    public interface OnFragmentInterActionListener {

    }


    public void fillTextViewWithFestival() {

        TextView festivalNameView = this.fragmentView.findViewById(R.id.textViewFestivalName);
        TextView festivalOrtView = this.fragmentView.findViewById(R.id.textViewFestivalOrt);
        TextView festivalDatum = this.fragmentView.findViewById(R.id.textViewFestivalDatum);


        java.util.Date festivalToDate = null;
        java.util.Date festivalFromDate = null;

        Festival festivalInformations = AppDatabase.getInstance().festivalDao()
                .allFromSelectedFestival();

        if (festivalInformations != null) {
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

    @Override
    public void onFestivalSelected(Context context) {

        if (this.fragmentView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            this.setupView(inflater);
        }

        fillTextViewWithFestival();

    }
}

