package com.example.tadje.festival_app;

import android.os.Environment;
import android.util.JsonReader;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Bands;
import com.example.tadje.festival_app.model.Festival;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalJsonReader {
    private ArrayList<Festival> festivalList = new ArrayList<>();

    public ArrayList<Festival> informationsForReader(String fileName) {
        FestivalManager.getInstance().getFileName();
        festivalList.clear();

        String stream = Environment.getExternalStorageDirectory() + File.separator + fileName;
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(stream));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonReader reader = new JsonReader(new InputStreamReader(dataInputStream));

        festivalList = readFestivalArray(reader);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivalList;
    }

    private ArrayList<Festival> readFestivalArray(JsonReader reader) {
        ArrayList<Festival> festivalList = new ArrayList<>();

        try {
            reader.beginArray();
            while (reader.hasNext()) {
                Festival festival = readFestival(reader);
                festivalList.add(festival);
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivalList;
    }

    private Festival readFestival(JsonReader reader) {
        String name = null;
        String festivalName = null;
        String festivalLocation = null;
        String festivalDate = null;
        String bandName = null;
        String stage = null;
        String date = null;
        String time = null;

        try {
            reader.beginObject();
            name = reader.nextName();


            if (name.equals("name")) {
                festivalName = reader.nextString();
            }
            name = reader.nextName();
            if (name.equals("location")) {
                festivalLocation = reader.nextString();
            }
            name = reader.nextName();
            if (name.equals("date")) {
                festivalDate = reader.nextString();
            }
            name = reader.nextName();
            if (name.equals("bands"))
                reader.beginArray();
            reader.beginObject();
            if (name.equals("bandname")) {
                bandName = reader.nextString();
            }
            name = reader.nextName();
            if(name.equals("stage")){
                stage = reader.nextString();
            }
            name = reader.nextName();
            if(name.equals("date")){
                date = reader.nextString();
            }
            name = reader.nextName();
            if(name.equals("time")){
                time = reader.nextName();
            }
            reader.endObject();
            reader.endArray();
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Festival festival = new Festival(festivalName, festivalLocation, festivalDate);
        addFestival(festival);

        Bands bands = new Bands(bandName, stage, date, time);
        addBands(bands);

        return festival;


    }

    private Bands addBands(Bands bands) {
        AppDatabase.getInstance().bandsDao().insertAll(bands);
        return bands;
    }

    private Festival addFestival(Festival festival) {
        AppDatabase.getInstance().festivalDao().insertAll(festival);
        return festival;
    }

}
