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
import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalJsonReader {
    private ArrayList<Festival> festivalList = new ArrayList<>();


    public ArrayList<Festival> informationsForReader(String fileName, Context context) {
        festivalList.clear();


        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName + ".json");
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

        List<String> existFestivalNames = AppDatabase.getInstance().festivalDao().getFestivalName();
        String festivalName = festival.getFestivalName();

        for (int i = 0; i < existFestivalNames.size(); ++i) {
            String existfesivalName = existFestivalNames.get(i);
            if (existfesivalName != festivalName) {
                putInDataBase(festival);
            }
        }
        if (existFestivalNames.size() == 0) {
            putInDataBase(festival);
        }

        FestivalManager.getInstance().setSelectedFestival(festival);
        festival.setSelected(true);
        AppDatabase.getInstance().festivalDao().setSelectedFestivalName(true, festivalName);

        return festival;
    }

    private void putInDataBase(Festival festival) {

        AppDatabase.getInstance().festivalDao().insert(festival);
        AppDatabase.getInstance().bandDao().insertAll(festival.getBands());

        AppDatabase.getInstance().festivalDao()
                .setSelectedFromAllWhereNot(false,
                        festival.getFestivalName());
    }

}
