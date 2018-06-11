package com.example.tadje.festival_app.Bands;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.tadje.festival_app.FestivalManager;
import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.R;
import com.example.tadje.festival_app.model.Band;

import java.util.List;


/**
 * Created by tadje on 07.06.2018.
 */

public class BandsOnADayActivity extends Activity {

    private BandsOnADayViewAdapter uAdapter;
    private RecyclerView mRecyclerView;
    private List<Band> bandList;
    TextView day;
    List<Band> myBandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bands_on_a_day_layout);


        AppDatabase.getInstance(this.getApplicationContext());

        day = findViewById(R.id.textViewForDay);
        createTextViewOfThisDay();

        bandList = FestivalManager.getInstance().getBandList();

        if (bandList != null) {
            mRecyclerView = findViewById(R.id.recyclerViewBands);
            mRecyclerView.setHasFixedSize(true);

            uAdapter = new BandsOnADayViewAdapter(bandList, this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            mRecyclerView.setAdapter(uAdapter);
            uAdapter.notifyDataSetChanged();
        }

    }

    private void createTextViewOfThisDay() {


        int festivalDayPosition = FestivalManager.getInstance().getFestivalDayPosition();
        List<Integer> listOfWeekdays = FestivalManager.getInstance().getListOfWeekdays();

        int weekday = listOfWeekdays.get(festivalDayPosition);

        switch (weekday) {
            case 1:
                day.setText("monday");
                break;
            case 2:
                day.setText("tuesday");
                break;
            case 3:
                day.setText("wendsday");
                break;
            case 4:
                day.setText("thursday");
                break;
            case 5:
                day.setText("friday");
                break;
            case 6:
                day.setText("saturday");
                break;
            case 7:
                day.setText("sunday");
                break;
        }

    }

}
