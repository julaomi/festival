package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.tadje.festival_app.model.Band;
import com.example.tadje.festival_app.model.CampArea;
import com.example.tadje.festival_app.model.Coordinates;
import com.example.tadje.festival_app.model.CoordinatesFestival;
import com.example.tadje.festival_app.model.CoordinatesStage;
import com.example.tadje.festival_app.model.Festival;
import com.example.tadje.festival_app.model.MyTypeConverter;
import com.example.tadje.festival_app.model.StageArea;

/**
 * Created by tadje on 31.05.2018.
 */
@Database(entities = {Festival.class, Band.class, Coordinates.class, CoordinatesFestival.class,
        CoordinatesStage.class, StageArea.class, CampArea.class},
        version = 1)
@TypeConverters({MyTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase Instance = null;

    public static AppDatabase getInstance() {
        if (Instance == null) {
            throw new IllegalStateException("AppDatabase not initialized yet.");
        }
        return Instance;
    }

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context,
                    AppDatabase.class, "festival.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public abstract FestivalDao festivalDao();
    public abstract BandDao bandDao();
    public abstract CoordinatesDao coordinatesDao();
    public abstract CoordinatesFestivalDao coordinatesFestivalDao();
    public abstract CoordinatesStageDao coordinatesStageDao();
    public abstract CampAreaDao campAreaDao();
    public  abstract StageAreaDao stageAreaDao();

}
