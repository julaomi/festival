package com.example.tadje.festival_app.Bands;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.festival_app.FestivalManager;
import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.R;
import com.example.tadje.festival_app.model.Band;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tadje on 07.06.2018.
 */

public class BandsOnADayViewAdapter extends RecyclerView.Adapter<BandsOnADayViewAdapter
        .ViewHolder> {

    List<Band> mDataset;
    BandsOnADayActivity mParent;

    List<Band> bandListFromDate;

    public BandsOnADayViewAdapter(List<Band> bandList, BandsOnADayActivity parent) {
        this.mDataset =  AppDatabase.getInstance().bandDao().getAll();
        this.mParent = parent;

        int festivalDayPosition = FestivalManager.getInstance().getFestivalDayPosition();
        final String dateString = checkTheDatePosition(festivalDayPosition);

        bandListFromDate = new ArrayList<>();

        for (int i = 0; i < mDataset.size(); ++i) {
            String dateFromList = mDataset.get(i).getDate();
            if (dateFromList.equals(dateString)) {
                bandListFromDate.add(mDataset.get(i));
            }
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .holder_band_on_a_day, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setTag(position);
            }
        });


        final Band bandForList = bandListFromDate.get(position);

        if(bandForList.isFavourite()){
            holder.mAddToYourBandButton.setVisibility(View.GONE);
        } else {
            holder.mAddToYourBandButton.setVisibility(View.VISIBLE);
        }


        holder.mAddToYourBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandForList.setFavourite(true);
                AppDatabase.getInstance().bandDao().update(bandForList);
                holder.mAddToYourBandButton.setVisibility(View.GONE);
            }
        });


        holder.mBandTime.setText(bandListFromDate.get(position).getTime());
        holder.mBandName.setText(bandListFromDate.get(position).getBandName());
    }


    private String checkTheDatePosition(int position) {
        List<Calendar> listOfDates = FestivalManager.getInstance().getListOfDates();
        Calendar selectetDate = listOfDates.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = simpleDateFormat.format(selectetDate.getTime());

        return dateString;
    }



    @Override
    public int getItemCount() {
        return bandListFromDate.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBandTime;
        TextView mBandName;
        FloatingActionButton mAddToYourBandButton;

        ViewGroup container;

        public ViewHolder(View itemView) {
            super(itemView);
            mBandName = itemView.findViewById(R.id.textViewBandNameOnDay);
            mBandTime = itemView.findViewById(R.id.textViewTimeFromBand);
            mAddToYourBandButton = itemView.findViewById(R.id.floatingActionButtonAddBand);
            container = itemView.findViewById(R.id.holderTheDayBands);


        }
    }
}
