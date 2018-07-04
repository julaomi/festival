package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.Coordinates;

import java.util.List;

/**
 * Created by tadje on 02.07.2018.
 */
@Dao
public abstract class CoordinatesDao {


    @Insert
    abstract long[] insertAllRaw(List<Coordinates> coordinates);

    public void insertAll(List<Coordinates> coordinates) {
        long[] ids = insertAllRaw(coordinates);
        for (int i = 0; i < ids.length; ++i) {
            coordinates.get(i).setId(ids[i]);
        }
    }


    @Query("SELECT name FROM coordinates")
    public abstract String getCoorName();

    @Query("SELECT id FROM coordinates WHERE name IS :festivalName")
    public abstract long getidForCoordinates(String festivalName);

    @Insert
    public abstract void insert(Coordinates coordinates);


    @Query("SELECT * FROM coordinates")
    public abstract List<Coordinates> getAll();
}

