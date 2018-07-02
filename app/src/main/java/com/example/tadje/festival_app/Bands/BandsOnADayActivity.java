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
    private List<Band> allBandsFromFestival;
    TextView day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bands_on_a_day_layout);


        AppDatabase.getInstance(this.getApplicationContext());

        day = findViewById(R.id.textViewForDay);
        createTextViewOfThisDay();

        allBandsFromFestival = AppDatabase.getInstance().bandDao().getAll();

        if (allBandsFromFestival != null) {
            mRecyclerView = findViewById(R.id.recyclerViewBands);
            mRecyclerView.setHasFixedSize(true);

            uAdapter = new BandsOnADayViewAdapter(allBandsFromFestival, this);

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
                day.setText(getString(R.string.monday));
                break;
            case 2:
                day.setText(getString(R.string.tuesday));
                break;
            case 3:
                day.setText(getString(R.string.wednesday));
                break;
            case 4:
                day.setText(getString(R.string.thursday));
                break;
            case 5:
                day.setText(getString(R.string.friday));
                break;
            case 6:
                day.setText(getString(R.string.saturday));
                break;
            case 7:
                day.setText(getString(R.string.sunday));
                break;
        }

    }

}
