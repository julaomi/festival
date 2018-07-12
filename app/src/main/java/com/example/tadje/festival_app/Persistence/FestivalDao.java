package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.festival_app.model.Festival;

import java.util.List;

/**
 * Created by tadje on 31.05.2018.
 */
@Dao
public interface FestivalDao {

    @Query("SELECT * FROM festival")
    List<Festival> getAll();

    @Query("SELECT * FROM festival WHERE festivalName IN (:festivalName)")
    List<Festival> getAllWhereFestival(String festivalName);

    @Query("SELECT id FROM festival WHERE festivalName IN (:festivalName)")
    int loadID(String festivalName);

    @Query("SELECT * FROM festival WHERE id IN (:Ids)")
    List<Festival> loadAllByIds(int[] Ids);


    @Query("SELECT festivalName FROM festival WHERE id IN (:id)")
    String loadFestivalNameById(int id);

    @Query("SELECT location FROM festival WHERE id IN (:id)")
    String loadFestivalLocationByID(int id);

    @Query("SELECT festivalfrom FROM festival WHERE id IN (:id)")
    String loadFestivalFromByID(int id);

    @Query("SELECT festivalto FROM festival WHERE id IN (:id)")
    String loadFestivalTOByID(int id);

    @Query("SELECT * FROM festival WHERE selected IS 1")
    Festival allFromSelectedFestival();

    @Query("UPDATE festival SET selected = (:myBoolean) WHERE festivalName IN (:festivalName)")
    int setSelected(boolean myBoolean, String festivalName);

    @Query("UPDATE festival SET selected = (:myBoolean) WHERE NOT festivalName IN (:festivalName)")
    int setSelectedFromAllWhereNot(boolean myBoolean, String festivalName);

    @Insert
    void insert(Festival festival);

    @Update
    void update(Festival festival);

    @Insert
    void insertAll(Festival... festivals);

    @Query("SELECT * FROM festival WHERE id IN (:id)")
    List<Festival> loadAllById(int id);


    @Query("DELETE FROM festival")
    void deleteTable();

    @Query( "UPDATE festival SET selected = (:myBoolean) WHERE festivalName IN (:festivalName)")
    void setSelectedFestivalName(boolean myBoolean, String festivalName);

    @Query("SELECT festivalName FROM festival")
    List <String> getFestivalName();
}
