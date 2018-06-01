package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.festival_app.model.Festival;

import java.util.List;

/**
 * Created by tadje on 31.05.2018.
 */
@Dao
public interface FestivalDao {

@Query("SELECT * FROM festival")
    List<Festival> getAll();

    @Query("SELECT * FROM festival WHERE id IN (:Ids)")
    List<Festival> loadAllByIds(int[] Ids);

    @Insert
    void insertAll(Festival festival);

}
