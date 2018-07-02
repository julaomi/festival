package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */
@Dao
public abstract class BandDao {


    @Update
    public abstract void update(Band band);

    @Insert
    abstract long[] insertAllRaw(List<Band> band);

    public void insertAll(List<Band> bands) {
        long[] ids = insertAllRaw(bands);
        for (int i = 0; i < ids.length; ++i) {
            bands.get(i).setId(ids[i]);
        }
    }

    @Query("UPDATE bands SET favourite = 0")
    public abstract int setAllFavourite();

    @Query("UPDATE bands SET favourite = (:favourite) WHERE bandName IS :bandName")
    public abstract int setFavourite(int favourite, String bandName);

    @Query("SELECT * FROM bands WHERE favourite IS 1")
    public abstract List<Band> getAllWhereFavouriteTrue();

    @Query("SELECT * FROM bands WHERE id IS :id")
    public abstract List<Band> getAllFromID(int id);

    @Query("DELETE FROM bands")
    public abstract void deleteTable();

    @Query("SELECT * FROM bands")
    public abstract List<Band> getAll();
}
