package com.example.tadje.festival_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tadje.festival_app.Persistence.AppDatabase;
import com.example.tadje.festival_app.Reader.CoordinatesJsonReader;
import com.example.tadje.festival_app.model.CampArea;
import com.example.tadje.festival_app.model.CoordinatesFestival;
import com.example.tadje.festival_app.model.CoordinatesStage;
import com.example.tadje.festival_app.model.Festival;
import com.example.tadje.festival_app.model.StageArea;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by tadje on 02.07.2018.
 */


public class MapsFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private MapView viewMap;
    private GoogleMap mMap;
    private Polyline polylineStage;
    private Polyline polylineCamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {


        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        viewMap = view.findViewById(R.id.mapView);
        viewMap.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        viewMap.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        new CoordinatesJsonReader()
                .informationsForCoordinatesReader
                        ("Hurricane", this.getContext());
        List<CoordinatesFestival> coordinatesFestivalList = AppDatabase.getInstance()
                .coordinatesFestivalDao().getAll();
        Festival selectedFestival = FestivalManager.getInstance().getSelectedFestival();
        String selectedFestivalName = selectedFestival.getFestivalName();
        long festivalID = AppDatabase.getInstance().coordinatesDao().getidForCoordinates
                (selectedFestivalName);

        double festivalLat = coordinatesFestivalList.get((int) festivalID).getLatFestival();
        double festivalLng = coordinatesFestivalList.get((int) festivalID).getLngFestival();

        mMap = googleMap;
        LatLng hurricane = new LatLng(festivalLat, festivalLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hurricane));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        this.mMap = googleMap;

        createStageMarker();

        createPolylines();

        viewMap.onResume();
    }

    private void createStageMarker() {

        List<CoordinatesStage> coordinatesStageList = AppDatabase.getInstance()
                .coordinatesStageDao().getAll();

        for (int i = 0; i < (coordinatesStageList.size()); i++) {

            LatLng markerCoordinates = new LatLng(coordinatesStageList.get(i).getLatStage(),
                    coordinatesStageList.get(i).getLngStage());

//            String stageName = coordinatesStageList.get(i).getStagename();
//            float markerColor;
//
//            switch (stageName){
//                case "white" : markerColor = HUE_ ;
//                break;
//                case "green" : markerColor = 142;
//                break;
//                case "blue" : markerColor = 211;
//                break;
//                case "red" : markerColor = 9;
//                break;
//                default: markerColor = 46;
//        }
//

            mMap.addMarker(new MarkerOptions().position(markerCoordinates)
                    .title(coordinatesStageList.get(i).getStagename())
                     );
        }
    }

    private void createPolylines() {

        List<StageArea> stageAreaList = AppDatabase.getInstance().stageAreaDao().getAll();

        for (int i = 0; i < (stageAreaList.size() - 1); ++i) {
            polylineStage = mMap.addPolyline(new PolylineOptions()
                    .add(
                            new LatLng(stageAreaList.get(i).getLatStageArea(), stageAreaList.get(i)
                                    .getLngStageArea()),
                            new LatLng(stageAreaList.get((i + 1)).getLatStageArea(), stageAreaList
                                    .get((i + 1)).getLngStageArea())
                    )
                    .color(getResources().getColor(R.color.colorBlue))
            );
        }

        List<CampArea> campAreaList = AppDatabase.getInstance().campAreaDao().getAll();

        for (int i = 0; i < (campAreaList.size() - 1); ++i) {
            polylineStage = mMap.addPolyline(new PolylineOptions()
                    .add(
                            new LatLng(campAreaList.get(i).getLatCampArea(), campAreaList.get(i).getLngCampArea()),
                            new LatLng(campAreaList.get((i + 1)).getLatCampArea(), campAreaList
                                    .get((i + 1)).getLngCampArea())

                    )
                    .color(getResources().getColor(R.color.colorPinkViolett))
            );
        }

    }


    public interface OnFragmentInterActionListener {
    }

    public void onStart() {
        super.onStart();
        if (mMap != null) {
            mMap.clear();
        }
    }
}