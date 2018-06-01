package com.example.tadje.festival_app;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.model.Festival;

import java.util.ArrayList;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalManager {

    private static FestivalManager instance = null;

    private String fileName = "Hurricane.json";
    private ArrayList<Festival> holidayList= (ArrayList<Festival>) AppDatabase.getInstance()
            .festivalDao().getAll();
    private int listFrom;

    private FestivalManager() {

    }
    public static  FestivalManager getInstance() {

        if (instance == null){
            instance = new FestivalManager();
        }

        return instance;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Festival> getHolidayList() {
        return holidayList;
    }

    public void setHolidayList(ArrayList<Festival> holidayList) {
        this.holidayList = holidayList;
    }

    public int getListFrom() {
        return listFrom;
    }

    public void setListFrom(int listFrom) {
        this.listFrom = listFrom;
    }
}
