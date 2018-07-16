package com.example.tadje.festival_app;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.tadje.festival_app.model.Tent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by tadje on 02.07.2018.
 */


public class MapsFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback,
        ILocationListener {

    private MapView viewMap;
    private GoogleMap mMap;
    private Polyline polylineStage;
    private Polyline polylineCamp;
    FloatingActionButton myTentButton;
    FloatingActionButton friendsTentButton;
    private double latitude;
    private double longitude;
    String selectedFestivalName;
    Festival selectedFestival;
    String tentName;

    private boolean LocationAvailable = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {


        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        viewMap = view.findViewById(R.id.mapView);
        viewMap.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        viewMap.getMapAsync(this);

        FestivalLocationManager.getInstance().locationInitialize(getContext());

        List<Tent> tentList = AppDatabase.getInstance().tentDao().getAll();

        if (tentList!=null){
            createMarkerForFriendsTent();
            createMarkerForMyTent();
        }

        myTentButton = view.findViewById(R.id.buttonForTent);
        myTentButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createMarkerForMyTent();

            }
        }
        ));

        friendsTentButton = view.findViewById(R.id.buttonForFriendsTend);
        friendsTentButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createMarkerForFriendsTent();
            }
        }
        ));


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        selectedFestival = AppDatabase.getInstance().festivalDao().allFromSelectedFestival();
        selectedFestivalName = selectedFestival.getFestivalName();

        new CoordinatesJsonReader()
                .informationsForCoordinatesReader
                        (selectedFestivalName, this.getContext());


        long festivalID = AppDatabase.getInstance().coordinatesDao().getidForCoordinates
                (selectedFestivalName);

        List<CoordinatesFestival> coordinatesFestivalList = AppDatabase.getInstance()
                .coordinatesFestivalDao().getAllCoordinatesFestivalWhereFestivalName
                        (selectedFestivalName);


        double festivalLat = coordinatesFestivalList.get((int) festivalID - 1).getLatFestival();
        double festivalLng = coordinatesFestivalList.get((int) festivalID - 1).getLngFestival();

        mMap = googleMap;
        LatLng festival = new LatLng(festivalLat, festivalLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(festival));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        this.mMap = googleMap;

        createStageMarker();
        createPolylines();

        viewMap.onResume();
    }

    private void createStageMarker() {

        int id = (int) AppDatabase.getInstance().coordinatesDao().getidForCoordinates
                (selectedFestivalName);

        List<CoordinatesStage> coordinatesStageList = AppDatabase.getInstance().coordinatesStageDao()
                .getAllCoordinatesStageWhereFestivalName(selectedFestivalName);


        for (int i = 0; i < (coordinatesStageList.size()); i++) {

            LatLng markerCoordinates = new LatLng(coordinatesStageList.get(i).getLatStage(),
                    coordinatesStageList.get(i).getLngStage());
            mMap.addMarker(new MarkerOptions().position(markerCoordinates)
                    .title(coordinatesStageList.get(i).getStagename())
            );
        }
        LatLng demofriendsTent = new LatLng(53.149815, 9.528061);
        mMap.addMarker(new MarkerOptions()
                .position(demofriendsTent)
                .title(getString(R.string.friends))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng demofriendsTent2 = new LatLng(53.156852, 9.509998);
        mMap.addMarker(new MarkerOptions()
                .position(demofriendsTent2)
                .title(getString(R.string.friends))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng demoMyTent = new LatLng(53.154374, 9.523909);
        mMap.addMarker(new MarkerOptions()
                .position(demoMyTent)
                .title(getString(R.string.mytent))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
    }

    private void createPolylines() {

        List<StageArea> stageAreaList = AppDatabase.getInstance().stageAreaDao()
                .getAllStageAreaWhereFestivalName(selectedFestivalName);

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

        List<CampArea> campAreaList = AppDatabase.getInstance().campAreaDao()
                .getAllCampAreaWhereFestivalName(selectedFestivalName);

        for (int i = 0; i < (campAreaList.size() - 1); ++i) {
            polylineCamp = mMap.addPolyline(new PolylineOptions()
                    .add(
                            new LatLng(campAreaList.get(i).getLatCampArea(), campAreaList.get(i).getLngCampArea()),
                            new LatLng(campAreaList.get((i + 1)).getLatCampArea(), campAreaList
                                    .get((i + 1)).getLngCampArea())

                    )
                    .color(getResources().getColor(R.color.colorPinkViolett))
            );
        }

    }


    private void createMarkerForMyTent() {

        latitude = FestivalManager.getInstance().getLatitude();
        longitude = FestivalManager.getInstance().getLongitude();
        tentName = getString(R.string.mytent);

        Tent myTent = new Tent(selectedFestivalName, tentName, latitude, longitude);
        AppDatabase.getInstance().tentDao().insert(myTent);


        List<Tent> myTentList = AppDatabase.getInstance().tentDao()
                .getAllWhereFestivalNameAndTentFrom
                        (selectedFestivalName, tentName);

        for (int i = 0; i < myTentList.size(); i++) {
            latitude = myTentList.get(i).getLatTent();
            longitude = myTentList.get(i).getLngTent();

            LatLng myTentCoord = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(myTentCoord)
                    .title(tentName)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        }

    }

    private void createMarkerForFriendsTent() {

        latitude = FestivalManager.getInstance().getLatitude();
        longitude = FestivalManager.getInstance().getLongitude();
        tentName = getString(R.string.friends);

        Tent friendsTent = new Tent(selectedFestivalName, tentName, latitude, longitude);
        AppDatabase.getInstance().tentDao().insert(friendsTent);

        List<Tent> friendsTentList = AppDatabase.getInstance().tentDao()
                .getAllWhereFestivalNameAndTentFrom
                        (selectedFestivalName, tentName);

        for (int i = 0; i < friendsTentList.size(); i++) {
            latitude = friendsTentList.get(i).getLatTent();
            longitude = friendsTentList.get(i).getLngTent();

            LatLng myTentCoord = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(myTentCoord)
                    .title(tentName)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }



    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        FestivalManager.getInstance().setLatitude(latitude);
        FestivalManager.getInstance().setLongitude(longitude);

    }


    public interface OnFragmentInterActionListener {
    }

    public void onStart() {
        super.onStart();
        if (mMap != null) {
            mMap.clear();
        }
    }

    //Registration in the listener List
    @Override
    public void onResume() {
        super.onResume();
        viewMap.onResume();
        FestivalLocationManager.getInstance().registerLocationChangedListener(this);

    }

    //unregistration in the listener List
    @Override
    public void onPause() {
        super.onPause();
        FestivalLocationManager.getInstance().unregisterLocationChangedListener(this);
    }

}