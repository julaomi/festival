package com.example.tadje.festival_app.Bands;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tadje.festival_app.BandsFragment;
import com.example.tadje.festival_app.FestivalManager;
import com.example.tadje.festival_app.R;
import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 05.06.2018.
 */

public class DaysViewAdapter extends RecyclerView.Adapter<DaysViewAdapter.ViewHolder>
        implements ListAdapter {

    List<Band> mDataset;
    BandsFragment mParent;
    private LayoutInflater layoutInflater;
    int days;
    ImageView daysImageView;
    TextView daysTextView;
    List<Integer> listOfWeekdays;
    Context mContext;


    public DaysViewAdapter(BandsFragment parent) {
        this.mParent = parent;
        listOfWeekdays = FestivalManager.getInstance().getListOfWeekdays();
    }


    @Override
    public DaysViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .bands_fragment, parent, false);

        layoutInflater = LayoutInflater.from(parent.getContext());


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaysViewAdapter.ViewHolder holder, int position) {
        Band band = mDataset.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return this.listOfWeekdays.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mDataset.get(position);
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        mContext = parent.getContext();

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.band_days_layout, parent, false);
            daysTextView = convertView.findViewById(R.id.textViewDay);
            daysImageView = convertView.findViewById(R.id.imageViewForDays);
            listOfWeekdays = FestivalManager.getInstance().getListOfWeekdays();
            convertView.setTag(position);

            addViews(position);

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                FestivalManager.getInstance().setFestivalDayPosition(position);

                final Intent intent = new Intent(mContext.getApplicationContext(), BandsOnADayActivity
                        .class);
                mParent.startActivity(intent);
            }
        });

        return convertView;

    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    @SuppressLint("NewApi")
    private void addViews(int position) {
           Integer weekday = position;

        if (position < listOfWeekdays.size()) {

            weekday = listOfWeekdays.get(position);

            switch (weekday) {
                case 1:
                    daysTextView.setText("monday");
                    break;
                case 2:
                    daysTextView.setText("tuesday");
                    break;
                case 3:
                    daysTextView.setText("wendsday");
                    break;
                case 4:
                    daysTextView.setText("thursday");
                    break;
                case 5:
                    daysTextView.setText("friday");
                    break;
                case 6:
                    daysTextView.setText("saturday");
                    break;
                case 7:
                    daysTextView.setText("sunday");
                    break;
            }


            switch (position) {
                case 0:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_first_round, null));
                    break;
                case 1:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_second_round, null));
                    break;
                case 2:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_third_round, null));
                    break;
                case 3:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_fourth_round, null));
                    break;
                case 4:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_fifth_round, null));
                    break;
                case 5:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_sixth_round, null));
                    break;
                case 6:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_seventh_round, null));
                    break;
                case 7:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_eighth_round, null));
                    break;
                case 9:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_ninth_round, null));
                    break;

            }

        }


    }
}
