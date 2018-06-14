package com.example.tadje.festival_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 11.06.2018.
 */

public class MyBandsFragment extends android.support.v4.app.Fragment {
    MyBandsViewAdapter myBandsViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_bands, null, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMyBands);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        myBandsViewAdapter = new MyBandsViewAdapter(this);
        recyclerView.setAdapter(myBandsViewAdapter);
        myBandsViewAdapter.notifyDataSetChanged();

    }





    public void deleteBandFromListDialog(final int position, final List<Band> mbDataset) {
        AlertDialog.Builder bandDeleteAlertDialog = new AlertDialog.Builder(getActivity());
        bandDeleteAlertDialog.setTitle("Band aus Liste Löschen");
        bandDeleteAlertDialog.setMessage("Möchtest du diese Band wirklich Löschen?");

        bandDeleteAlertDialog.setPositiveButton(getString(R.string.delete), new DialogInterface
                .OnClickListener
                () {
            public void onClick(DialogInterface arg0, int arg1) {
                List<Band> listOfMyBands;

                listOfMyBands = FestivalManager.getInstance()
                       .getSelectetBandList();

                if (listOfMyBands.size() == 0) {
                    listOfMyBands = AppDatabase.getInstance().bandDao()
                            .getAllWhereFavouriteTrue();
                }

                String nameForDatabase = listOfMyBands.get(position).getBandName();

                AppDatabase.getInstance().bandDao().setFavourite(0, nameForDatabase);

                listOfMyBands.remove(position);

                FestivalManager.getInstance().setSelectetBandList(listOfMyBands);

                myBandsViewAdapter.notifyDataSetChanged();

            }
        });

        bandDeleteAlertDialog.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        bandDeleteAlertDialog.show();
    }



    public interface OnFragmentInterActionListener {
    }
}

