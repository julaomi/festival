package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.festival_app.model.StageArea;

import java.util.List;

/**
 * Created by tadje on 04.07.2018.
 */
@Dao
public abstract class StageAreaDao {

    @Update
    public abstract void update(StageArea stageArea);

    @Insert
    abstract long[] insertAllRaw(List<StageArea> stageArea);

    public void insertAll(List<StageArea> stageAreas) {
        long[] ids = insertAllRaw(stageAreas);
        for (int i = 0; i < ids.length; ++i) {
            stageAreas.get(i).setId(ids[i]);
        }
    }

    @Query("SELECT * FROM stageArea WHERE id IS :id")
    public abstract List<StageArea> getAllFromID(int id);

    @Query("SELECT * FROM stageArea")
     public abstract List<StageArea> getAll();

    @Query("SELECT * FROM stageArea WHERE festivalName IS :festivalName")
    public abstract List<StageArea> getAllStageAreaWhereFestivalName(String festivalName);
}
