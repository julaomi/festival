package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tadje on 02.07.2018.
 */
@Entity(tableName = "coordinates")
public class Coordinates {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String coordFestivalName;

    @SerializedName("coordinatesFestival")
    @Expose
    @TypeConverters({MyTypeConverter.class})
    private List<CoordinatesFestival> coordinatesFestival;

    @SerializedName("stageArea")
    @Expose
    @TypeConverters({MyTypeConverter.class})
    private List<StageArea> stageArea;

    @SerializedName("coordinatesStage")
    @Expose
    @TypeConverters({MyTypeConverter.class})
    private List<CoordinatesStage> coordinatesStage;


    @SerializedName("campArea")
    @Expose
    @TypeConverters({MyTypeConverter.class})
    private List<CampArea> campArea;



    public Coordinates(String coordFestivalName) {
        this.coordFestivalName = coordFestivalName;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoordFestivalName() {
        return coordFestivalName;
    }

    public void setCoordFestivalName(String coordFestivalName) {
        this.coordFestivalName = coordFestivalName;
    }

    public List<CoordinatesFestival> getCoordinatesFestival() {
        return coordinatesFestival;
    }

    public void setCoordinatesFestival(List<CoordinatesFestival> coordinatesFestival) {
        this.coordinatesFestival = coordinatesFestival;
    }

    public List<CoordinatesStage> getCoordinatesStage() {
        return coordinatesStage;
    }

    public void setCoordinatesStage(List<CoordinatesStage> coordinatesStage) {
        this.coordinatesStage = coordinatesStage;
    }

    public List<StageArea> getStageArea() {
        return stageArea;
    }

    public void setStageArea(List<StageArea> stageArea) {
        this.stageArea = stageArea;
    }

    public List<CampArea> getCampArea() {
        return campArea;
    }

    public void setCampArea(List<CampArea> campArea) {
        this.campArea = campArea;
    }
}
