package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 01.06.2018.
 */
@Entity(tableName = "bands")

public class Band {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("bandname")
    @Expose
    @ColumnInfo(name = "bandName")
    private String bandName;

    @SerializedName("stage")
    @Expose
    @ColumnInfo(name = "stage")
    private String stage;

    @SerializedName("date")
    @Expose
    @ColumnInfo(name = "date")
    private String date;

    @SerializedName("time")
    @Expose
    @ColumnInfo(name = "time")
    private String time;


    public Band(String bandName,  String stage, String date, String time) {
        this.bandName = bandName;
        this.stage = stage;
        this.date = date;
        this.time = time;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}