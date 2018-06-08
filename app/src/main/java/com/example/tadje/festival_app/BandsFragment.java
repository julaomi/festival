package com.example.tadje.festival_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tadje.festival_app.Bands.DaysViewAdapter;

/**
 * Created by tadje on 05.06.2018.
 */

public class BandsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bands_fragment, null, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = view.findViewById(R.id.gridViewDays);
        DaysViewAdapter bandsViewAdapter = new DaysViewAdapter(this);
        gridView.setAdapter(bandsViewAdapter);
    }

    public interface OnFragmentInterActionListener {
    }
}
