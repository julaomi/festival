package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 13.07.2018.
 */

@Entity(tableName = "tent")
public class Tent {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("festivalName")
    @Expose
    @ColumnInfo(name = "festivalName")
    private String festivalName;

    @SerializedName("tentFrom")
    @Expose
    @ColumnInfo(name = "tentFrom")
    private String tentFrom;

    @SerializedName("latTent")
    @Expose
    @ColumnInfo(name = "latTent")
    private double latTent;

    @SerializedName("lngTent")
    @Expose
    @ColumnInfo(name = "lngTent")
    private double lngTent;


    public Tent(String festivalName, String tentFrom, double latTent, double lngTent) {
        this.festivalName = festivalName;
        this.tentFrom = tentFrom;
        this.latTent = latTent;
        this.lngTent = lngTent;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public double getLatTent() {
        return latTent;
    }

    public void setLatTent(double latTent) {
        this.latTent = latTent;
    }

    public double getLngTent() {
        return lngTent;
    }

    public void setLngTent(double lngTent) {
        this.lngTent = lngTent;
    }

    public String getTentFrom() {
        return tentFrom;
    }

    public void setTentFrom(String tentFrom) {
        this.tentFrom = tentFrom;
    }
}
