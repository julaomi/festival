package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tadje on 04.07.2018.
 */
@Entity(tableName = "coordinatesFestival")
public class CoordinatesFestival {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("latFestival")
    @Expose
    @ColumnInfo(name = "latFestival")
    private double latFestival;

    @SerializedName("lngFestival")
    @Expose
    @ColumnInfo(name = "lngFestival")
    private double lngFestival;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatFestival() {
        return latFestival;
    }

    public void setLatFestival(double latFestival) {
        this.latFestival = latFestival;
    }

    public double getLngFestival() {
        return lngFestival;
    }

    public void setLngFestival(double lngFestival) {
        this.lngFestival = lngFestival;
    }
}
