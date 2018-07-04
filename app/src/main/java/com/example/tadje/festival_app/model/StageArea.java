package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 04.07.2018.
 */
@Entity(tableName = "stageArea")
public class StageArea {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("latStageArea")
    @Expose
    @ColumnInfo(name = "latStageArea")
    private double latStageArea;

    @SerializedName("lngStageArea")
    @Expose
    @ColumnInfo(name = "lngStageArea")
    private double lngStageArea;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatStageArea() {
        return latStageArea;
    }

    public void setLatStageArea(double latStageArea) {
        this.latStageArea = latStageArea;
    }

    public double getLngStageArea() {
        return lngStageArea;
    }

    public void setLngStageArea(double lngStageArea) {
        this.lngStageArea = lngStageArea;
    }
}

