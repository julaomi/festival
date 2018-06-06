package com.example.tadje.festival_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.GridView;

import com.example.tadje.festival_app.model.Band;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 05.06.2018.
 */

public class BandsFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<Band> bandList = FestivalManager.getInstance().getBandList();

        GridView gridView = view.findViewById(R.id.gridViewDays);
        gridView.setAdapter(new BandsViewAdapter((ArrayList<Band>) bandList, this));
    }
}
