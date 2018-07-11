package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.festival_app.model.CampArea;

import java.util.List;

/**
 * Created by tadje on 04.07.2018.
 */
@Dao
public abstract class CampAreaDao {
    @Update
    public abstract void update(CampArea campArea);

    @Insert
    abstract long[] insertAllRaw(List<CampArea> campArea);

    public void insertAll(List<CampArea> campArea) {
        long[] ids = insertAllRaw(campArea);
        for (int i = 0; i < ids.length; ++i) {
            campArea.get(i).setId(ids[i]);
        }
    }

    @Query("SELECT * FROM campArea WHERE id IS :id")
    public abstract List<CampArea> getAllFromID(int id);

    @Query("SELECT * FROM campArea")
    public abstract List<CampArea> getAll();

    @Query("SELECT * FROM campArea WHERE festivalName IS :festivalName")
    public abstract List<CampArea> getAllCampAreaWhereFestivalName(String festivalName);




}
