package com.example.tadje.festival_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tadje on 01.06.2018.
 */
@Entity(tableName = "bands")
public class Bands {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "bandName")
        private String bandName;

        @ColumnInfo (name = "stage")
        private String stage;

        @ColumnInfo (name = "date")
        private String date;

        @ColumnInfo (name = "time")
        private String time;


        public Bands (String bandName, String stage, String date, String time) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
