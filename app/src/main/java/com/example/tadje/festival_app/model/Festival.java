package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tadje on 31.05.2018.
 */

@Entity(tableName = "festival")
public class Festival {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "festivalName")
    private String festivalName;

    @ColumnInfo (name = "festivalOrt")
    private String festivalOrt;

    @ColumnInfo (name = "festivalDatum")
    private String festivalDatum;

    public Festival( String festivalName, String festivalOrt, String festivalDatum) {
       this.festivalName = festivalName;
       this.festivalOrt = festivalOrt;
       this.festivalDatum = festivalDatum;
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

    public String getFestivalOrt() {
        return festivalOrt;
    }

    public void setFestivalOrt(String festivalOrt) {
        this.festivalOrt = festivalOrt;
    }

    public String getFestivalDatum() {
        return festivalDatum;
    }

    public void setFestivalDatum(String festivalDatum) {
        this.festivalDatum = festivalDatum;
    }
}
