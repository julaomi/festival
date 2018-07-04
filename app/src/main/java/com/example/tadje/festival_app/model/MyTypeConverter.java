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


    @TypeConverter
    public static String toIdsCoordinatesFestival(List<CoordinatesFestival> coordinatesFestivals) {
        String result = "";
        for (CoordinatesFestival coordinatesFestival: coordinatesFestivals) {
            result += coordinatesFestival.getId() + "/";
        }
        return result;
    }

    @TypeConverter
    public static List<CoordinatesFestival> fromIdsCoordinatesFestival (String ids) {
        String[] idList;
        List<CoordinatesFestival> coordinatesFestivals = null;
        idList = ids.split("/");

        for (int i = 0; i <= (idList.length -1 ); i++) {
            int id = Integer.parseInt(idList[i]);
            coordinatesFestivals = AppDatabase.getInstance().coordinatesFestivalDao().getAllFromID(id);
        }
        return coordinatesFestivals;
    }


    @TypeConverter
    public static String toIdsCoordinatesStag(List<CoordinatesStage> coordinatesStages) {
        String result = "";
        for (CoordinatesStage coordinatesStage: coordinatesStages) {
            result += coordinatesStage.getId() + "/";
        }
        return result;
    }

    @TypeConverter
    public static List<CoordinatesStage> fromIdsCoordinatesStage (String ids) {
        String[] idList;
        List<CoordinatesStage> coordinatesStages = null;
        idList = ids.split("/");

        for (int i = 0; i <= (idList.length -1 ); i++) {
            int id = Integer.parseInt(idList[i]);
            coordinatesStages = AppDatabase.getInstance().coordinatesStageDao().getAllFromID(id);
        }
        return coordinatesStages;
    }


    @TypeConverter
    public static String toIdsCampArea(List<CampArea> campAreas) {
        String result = "";
        for (CampArea campArea : campAreas) {
            result += campArea.getId() + "/";
        }
        return result;
    }

    @TypeConverter
    public static List<CampArea> fromIdsCampArea(String ids) {
        String[] idList;
        List<CampArea> campAreas = null;
        idList = ids.split("/");

        for (int i = 0; i <= (idList.length -1 ); i++) {
            int id = Integer.parseInt(idList[i]);
            campAreas = AppDatabase.getInstance().campAreaDao().getAllFromID(id);
        }
        return campAreas;
    }


    @TypeConverter
    public static String toIdsStageArea(List<StageArea> stageAreas) {
        String result = "";
        for (StageArea stageArea : stageAreas) {
            result += stageArea.getId() + "/";
        }
        return result;
    }

    @TypeConverter
    public static List<StageArea> fromIdsStageArea (String ids) {
        String[] idList;
        List<StageArea> stageAreas = null;
        idList = ids.split("/");

        for (int i = 0; i <= (idList.length -1 ); i++) {
            int id = Integer.parseInt(idList[i]);
            stageAreas = AppDatabase.getInstance().stageAreaDao().getAllFromID(id);
        }
        return stageAreas;
    }




}
