package com.example.tadje.festival_app.Reader;

import android.content.Context;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Coordinates;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tadje on 02.07.2018.
 */

public class CoordinatesJsonReader {


    public Coordinates informationsForCoordinatesReader(String fileName, Context context) {

        String json = null;
        String fileNameForCoordinations= fileName + "Koord.json";

        try {
            InputStream inputStream = context.getAssets().open(fileNameForCoordinations);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Coordinates coordinates = gson.fromJson(json, Coordinates.class);

        String existFestivalName = AppDatabase.getInstance().coordinatesDao().getCoorName();

        if (existFestivalName != coordinates.getCoordFestivalName()){

        AppDatabase.getInstance().coordinatesDao().insert(coordinates);

        AppDatabase.getInstance().coordinatesFestivalDao().insertAll(coordinates.getCoordinatesFestival());
        AppDatabase.getInstance().coordinatesStageDao().insertAll(coordinates.getCoordinatesStage());
        AppDatabase.getInstance().campAreaDao().insertAll(coordinates.getCampArea());
        AppDatabase.getInstance().stageAreaDao().insertAll(coordinates.getStageArea());
    }

        return coordinates;

    }

}





