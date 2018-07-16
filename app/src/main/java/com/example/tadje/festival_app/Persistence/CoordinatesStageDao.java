package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.CoordinatesStage;

import java.util.List;

/**
 * Created by tadje on 04.07.2018.
 */
@Dao
public abstract class CoordinatesStageDao {


    @Insert
    abstract long[] insertAllRaw(List<CoordinatesStage> coordinatesStage);

    public void insertAll(List<CoordinatesStage> coordinatesStage) {
        long[] ids = insertAllRaw(coordinatesStage);
        for (int i = 0; i < ids.length; ++i) {
            coordinatesStage.get(i).setId(ids[i]);
        }
    }

    @Query("SELECT * FROM coordinatesStage WHERE id IS :id")
    public abstract List<CoordinatesStage> getAllFromID(int id);

    @Query("SELECT * FROM coordinatesStage")
    public abstract List<CoordinatesStage> getAll();

    @Query("SELECT * FROM coordinatesStage WHERE festivalName IS :festivalName")
    public abstract List<CoordinatesStage> getAllCoordinatesStageWhereFestivalName(String
                                                                                           festivalName);

}
