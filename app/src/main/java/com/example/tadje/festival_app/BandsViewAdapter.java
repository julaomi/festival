package com.example.tadje.festival_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tadje.festival_app.model.Band;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 05.06.2018.
 */

public class BandsViewAdapter extends RecyclerView.Adapter<BandsViewAdapter.ViewHolder>
        implements ListAdapter {

    ArrayList<Band> mDataset;
    BandsFragment mParent;
    private LayoutInflater layoutInflater;
    int days;
    ImageView daysImageView;
    TextView daysTextView;

    private Context context;


    public BandsViewAdapter(ArrayList<Band> bandList, BandsFragment parent) {
        this.mDataset = bandList;
        this.mParent = parent;
    }


    @Override
    public BandsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .bands_fragment, parent, false);
        days = FestivalManager.getInstance().getFestivalDays();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BandsViewAdapter.ViewHolder holder, int position) {
        Band band = mDataset.get(position);


    }

    @Override
    public int getItemCount() {
        return 0;
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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = getLayoutInflater().inflate(R.layout.band_days_layout, parent);
            GridView gridView = convertView.findViewById(R.id.gridViewDays);
            daysTextView = gridView.findViewById(R.id.textViewDay);
            daysImageView = gridView.findViewById(R.id.imageViewForDays);

            addViews(days, convertView, gridView);

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start new activty
            }
        });

//        TextView dayTextView = convertView.findViewById(R.id.textViewDay);
//
//        if (position == 0) {
//            dayTextView.setText("Montag");
//        }


        return null;

    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @SuppressLint("NewApi")
    private void addViews(int days, View convertView, GridView gridView){
        List<Integer> listOfWeekdays = FestivalManager.getInstance().getListOfWeekdays();
        Integer weekday = null;

        Context context = mParent.getContext();

        for (int i = 0; i <= days; i++) {
            weekday = listOfWeekdays.get(i);

            switch (weekday){
                case 1:
                    daysTextView.setText("monday");
                break;
                case 2:
                    daysTextView.setText("tuesday");
                break;
                case 3: daysTextView.setText("wendsday");
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_donnerstag_round ,null));
                break;
                case 4:
                    daysTextView.setText("thursday");
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_donnerstag_round ,null));
                break;
                case 5:
                    daysTextView.setText("friday");
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_fr_round ,null));
                break;
                case 6:
                    daysTextView.setText("saturday");
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_sa_round ,null));
                break;
                case 7:
                    daysTextView.setText("sunday");
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_so_round ,null));
                break;
            }

            switch (i){
                case 1:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_first_round,null));
                    break;
                case 2:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_second_round ,null));
                    break;
                case 3:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_third_round ,null));
                    break;
                case 4:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_fourth_round,null));
                    break;
                case 5:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_fifth_round ,null));
                    break;
                case 6:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_sixth_round ,null));
                    break;
                case 7:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_seventh_round,null));
                    break;
                case 8:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_eighth_round ,null));
                    break;
                case 9:
                    daysImageView.setBackground(ResourcesCompat.getDrawable(mParent.getResources(), R
                            .mipmap.ic_launcher_donnerstag_round ,null));
                    break;

            }

        }

    }
}
