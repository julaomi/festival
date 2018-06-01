package com.example.tadje.festival_app.Persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tadje.festival_app.model.Festival;

/**
 * Created by tadje on 31.05.2018.
 */
@Database(entities = {Festival.class}, version = 1)
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
                    AppDatabase.class, "locations.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public abstract FestivalDao festivalDao();


}
