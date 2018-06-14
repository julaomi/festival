package com.example.tadje.festival_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 11.06.2018.
 */

public class MyBandsViewAdapter extends RecyclerView.Adapter<MyBandsViewAdapter.ViewHolder> {

    List<Band> mbDataset;
    MyBandsFragment mbParent;


    public MyBandsViewAdapter(MyBandsFragment myBands) {
        this.mbParent = myBands;
        mbDataset = AppDatabase.getInstance().bandDao().getAllWhereFavouriteTrue();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .holder_my_bands, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setTag(position);

                mbParent.deleteBandFromListDialog(position, mbDataset);
            }
        });
        List<Band> selectetBandList = FestivalManager.getInstance().getSelectetBandList();

        holder.mBand.setText(selectetBandList.get(position).getBandName());
        holder.mWeekday.setText(selectetBandList.get(position).getDate());
        holder.mTime.setText(selectetBandList.get(position).getTime());
        holder.mStage.setText(selectetBandList.get(position).getStage());
    }


    @Override
    public int getItemCount() {

        return FestivalManager.getInstance().getSelectetBandList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBand;
        TextView mWeekday;
        TextView mTime;
        TextView mStage;

        ViewGroup container;

        public ViewHolder(View itemView) {
            super(itemView);
            mBand = itemView.findViewById(R.id.textViewBandNameInMyBands);
            mWeekday = itemView.findViewById(R.id.textViewWeekday);
            mStage = itemView.findViewById(R.id.textViewStage);
            mTime = itemView.findViewById(R.id.textViewTimeInMyBAnds);

            container = itemView.findViewById(R.id.holderMyBands);
        }
    }
}
