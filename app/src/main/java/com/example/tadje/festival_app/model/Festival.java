package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tadje on 31.05.2018.
 */

@Entity(tableName = "festival")
public class Festival {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "festivalName")
    private String festivalName;

    @SerializedName("location")
    @Expose
    @ColumnInfo(name = "location")
    private String festivalLocation;

    @SerializedName("festivaldate")
    @Expose
    @ColumnInfo(name = "festivalDate")
    private String festivalDate;

    @SerializedName("bands")
    @Expose
    @TypeConverters({MyTypeConverter.class})
    private List<Band> bands;


    public Festival(String festivalName, String festivalLocation, String festivalDate) {
        this.festivalName = festivalName;
        this.festivalLocation = festivalLocation;
        this.festivalDate = festivalDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getFestivalLocation() {
        return festivalLocation;
    }

    public void setFestivalLocation(String festivalLocation) {
        this.festivalLocation = festivalLocation;
    }

    public String getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(String festivalDate) {
        this.festivalDate = festivalDate;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }
}