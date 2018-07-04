package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 04.07.2018.
 */
@Entity(tableName = "coordinatesStage")
public class CoordinatesStage {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("stagename")
    @Expose
    @ColumnInfo(name = "stagename")
    private String stagename;

    @SerializedName("latStage")
    @Expose
    @ColumnInfo(name = "latStage")
    private double latStage;

    @SerializedName("lngStage")
    @Expose
    @ColumnInfo(name = "lngStage")
    private double lngStage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
    }


    public double getLatStage() {
        return latStage;
    }

    public void setLatStage(double latStage) {
        this.latStage = latStage;
    }

    public double getLngStage() {
        return lngStage;
    }

    public void setLngStage(double lngStage) {
        this.lngStage = lngStage;
    }
}
