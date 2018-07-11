package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 04.07.2018.
 */
@Entity(tableName = "campArea")
public class CampArea {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("festivalName")
    @Expose
    @ColumnInfo(name = "festivalName")
    private String festivalName;

    @SerializedName("latCampArea")
    @Expose
    @ColumnInfo(name = "latCampArea")
    private double latCampArea;

    @SerializedName("lngCampArea")
    @Expose
    @ColumnInfo(name = "lngCampArea")
    private double lngCampArea;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatCampArea() {
        return latCampArea;
    }

    public void setLatCampArea(double latCampArea) {
        this.latCampArea = latCampArea;
    }

    public double getLngCampArea() {
        return lngCampArea;
    }

    public void setLngCampArea(double lngCampArea) {
        this.lngCampArea = lngCampArea;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }
}


