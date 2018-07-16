package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.Tent;

import java.util.List;

/**
 * Created by tadje on 13.07.2018.
 */

@Dao
public abstract class TentDao {


    @Query("SELECT * FROM tent")
    public abstract List<Tent> getAll();

    @Insert
    public abstract void insert(Tent tent);

    @Query("SELECT * FROM tent WHERE festivalName = (:festivalName) AND  tentFrom = (:tentFrom)")
    public abstract List<Tent> getAllWhereFestivalNameAndTentFrom(String festivalName, String
            tentFrom);


}

