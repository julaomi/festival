package com.example.tadje.festival_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 11.06.2018.
 */

class MyBandsViewAdapter extends RecyclerView.Adapter<MyBandsViewAdapter.ViewHolder>{

    List<Band> mbDataset;
    MyBandsFragment mbParent;


    public MyBandsViewAdapter(MyBandsFragment myBands) {
     this.mbParent = myBands;
     mbDataset =  FestivalManager.getInstance().getSelectetBandList();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .holder_my_bands, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Band bandFromList = mbDataset.get(position);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setTag(position);
            }
        });


        holder.mBand.setText(bandFromList.getBandName());
        holder.mWeekday.setText(bandFromList.getDate());
        holder.mTime.setText(bandFromList.getTime());
        holder.mStage.setText(bandFromList.getStage());
    }

    @Override
    public int getItemCount() {
        return mbDataset.size() ;
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
