package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.Bands;

import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */
@Dao
public interface BandsDao {
    @Query("SELECT * FROM bands")
    List<Bands> getAll();

    @Insert
    void insertAll(Bands... bands);
}
