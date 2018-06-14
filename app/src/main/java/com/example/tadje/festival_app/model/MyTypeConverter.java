package com.example.tadje.festival_app.model;

import android.arch.persistence.room.TypeConverter;

import com.example.tadje.festival_app.Persistence.AppDatabase;

import java.util.List;

/**
 * Created by tadje on 04.06.2018.
 */

public class MyTypeConverter {

    @TypeConverter
    public static String toIds(List<Band> bands) {
        String result = "";
        for (Band band : bands) {
            result += band.getId() + "/";
        }
        return result;
    }

    @TypeConverter
    public static List<Band> fromIds (String ids) {
        String[] idList;
        List<Band> bands = null;
        idList = ids.split("/");

        for (int i = 0; i <= (idList.length -1 ); i++) {
            int id = Integer.parseInt(idList[i]);
            bands = AppDatabase.getInstance().bandDao().getAllFromID(id);
        }
        return bands;
    }

}
