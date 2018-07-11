package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.festival_app.model.CoordinatesFestival;

import java.util.List;

/**
 * Created by tadje on 04.07.2018.
 */
@Dao
public abstract class CoordinatesFestivalDao {

    @Update
    public abstract void update(CoordinatesFestival coordinatesFestival);

    @Insert
    abstract long[] insertAllRaw(List<CoordinatesFestival> coordinatesFestivals);

    public void insertAll(List<CoordinatesFestival> coordinatesFestivals) {
        long[] ids = insertAllRaw(coordinatesFestivals);
        for (int i = 0; i < ids.length; ++i) {
            coordinatesFestivals.get(i).setId(ids[i]);
        }
    }

    @Query("SELECT * FROM coordinatesFestival WHERE id IS :id")
    public abstract List<CoordinatesFestival> getAllFromID(int id);

    @Query("SELECT * FROM coordinatesFestival")
    public abstract List<CoordinatesFestival> getAll();

    @Query("SELECT * FROM coordinatesFestival WHERE festivalName IS :festivalName")
    public abstract List<CoordinatesFestival> getAllCoordinatesFestivalWhereFestivalName(String
                                                                                            festivalName);

}
