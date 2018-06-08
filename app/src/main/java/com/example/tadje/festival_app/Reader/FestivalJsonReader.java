package com.example.tadje.festival_app.Reader;

import android.content.Context;

import com.example.tadje.festival_app.FestivalManager;
import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Festival;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalJsonReader {
    private ArrayList<Festival> festivalList = new ArrayList<>();


    public ArrayList<Festival> informationsForReader(String fileName, Context context) {
        FestivalManager.getInstance().getFileName();
        festivalList.clear();

      String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Festival festival = gson.fromJson(json, Festival.class);
        addFestival(festival);



        return festivalList;

    }

    private Festival addFestival(Festival festival) {

        AppDatabase.getInstance().bandDao().insertAll(festival.getBands());
        FestivalManager.getInstance().setBandList(festival.getBands());
        AppDatabase.getInstance().festivalDao().insert(festival);
        return festival;
    }

}
