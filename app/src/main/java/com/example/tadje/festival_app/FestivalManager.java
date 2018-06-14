package com.example.tadje.festival_app;

import com.example.tadje.festival_app.model.Band;
import com.example.tadje.festival_app.model.Festival;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */

public class FestivalManager {

    private static FestivalManager instance = null;

    private Festival selectedFestival;

    private String fileName = "festivals/Hurricane.json";
    private List<Band> bandList;
    private List<Integer> listOfWeekdays;
    private int festivalDays;
    private int festivalDayPosition;
    private List<Calendar> listOfDates;
    private List<Band> selectetBandList = new ArrayList<>();

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



    public List<Band> getBandList() {
        return bandList;
    }

    public void setBandList(List<Band> bandList) {
        this.bandList = bandList;
    }

    public List<Integer> getListOfWeekdays() {
        return listOfWeekdays;
    }

    public void setListOfWeekdays(List<Integer> listOfWeekdays) {
        this.listOfWeekdays = listOfWeekdays;
    }

    public int getFestivalDays() {
        return festivalDays;
    }

    public void setFestivalDays(int festivalDays) {
        this.festivalDays = festivalDays;
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

    public List<Band> getSelectetBandList() {
        return selectetBandList;
    }

    public void setSelectetBandList(List<Band> selectetBandList) {
        this.selectetBandList = selectetBandList;
    }

    public Festival getSelectedFestival() {
        return selectedFestival;
    }

    public void setSelectedFestival(Festival selectedFestival) {
        this.selectedFestival = selectedFestival;
    }
}
