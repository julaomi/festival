package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.Band;

import java.util.List;

/**
 * Created by tadje on 01.06.2018.
 */
@Dao
public abstract class BandDao {
    @Query("SELECT * FROM bands")
    abstract List<Band> getAll();

    @Insert
    abstract long[] insertAllRaw(List<Band> band);

    public void insertAll(List<Band> bands) {
        long[] ids = insertAllRaw(bands);
        for(int i=0; i < ids.length; ++i) {
            bands.get(i).setId(ids[i]);

        }
    }

//     @Query("SELECT * FROM bands WHERE favorite ")
//     public abstract List<Band> getAllFavoritBands();

    @Query("SELECT * FROM bands WHERE id IN (:id)")
    public abstract List<Band> getAllFromID(int id);

}
