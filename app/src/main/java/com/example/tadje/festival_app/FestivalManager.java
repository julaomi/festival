package com.example.tadje.festival_app;

import com.example.tadje.festival_app.model.Festival;

import java.util.Calendar;
import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalManager {

    private static FestivalManager instance = null;

    private Festival selectedFestival;

    private String fileName = "festivals/Hurricane.json";
    private List<Integer> listOfWeekdays;
    private int festivalDayPosition;
    private List<Calendar> listOfDates;

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



    public List<Integer> getListOfWeekdays() {
        return listOfWeekdays;
    }

    public void setListOfWeekdays(List<Integer> listOfWeekdays) {
        this.listOfWeekdays = listOfWeekdays;
    }


    public int getFestivalDayPosition() {
        return festivalDayPosition;
    }

    public void setFestivalDayPosition(int festivalDayPosition) {
        this.festivalDayPosition = festivalDayPosition;
    }

    public void setListOfDates(List<Calendar> listOfDates) {
        this.listOfDates = listOfDates;
    }

    public List<Calendar> getListOfDates() {
        return listOfDates;
    }


    public Festival getSelectedFestival() {
        return selectedFestival;
    }

    public void setSelectedFestival(Festival selectedFestival) {
        this.selectedFestival = selectedFestival;
    }
}
