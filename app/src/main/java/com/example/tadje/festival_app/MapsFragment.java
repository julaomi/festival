package com.example.tadje.festival_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by tadje on 02.07.2018.
 */


public class MapsFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private MapView viewMap;
    private GoogleMap mMap;
    private Polyline polylineStage;
    private Polyline polylineCamp;
    FloatingActionButton myTentButton;
    FloatingActionButton friendsTentButton;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    // The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;
    private double latitude;
    private double longitude;
    String selectedFestivalName;
    Festival selectedFestival;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {


        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        viewMap = view.findViewById(R.id.mapView);
        viewMap.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        viewMap.getMapAsync(this);

        myTentButton = view.findViewById(R.id.buttonForTent);
        myTentButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                locationInitialize();
                createMarkerForMyTent();
            }
        }
        ));

        friendsTentButton = view.findViewById(R.id.buttonForFriendsTend);
        friendsTentButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

    private Location locationInitialize() {

        locationManager = (LocationManager) getContext().getSystemService
                (LOCATION_SERVICE);

        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager
                .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);

        checkForPermissions();

        return location;
    }

    private void checkForPermissions() {
        if (isGPSEnabled || isNetworkEnabled) {

            this.canGetLocation = true;


            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                            .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager
                                .GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        (LocationListener) this);
                locationManager.requestLocationUpdates(LocationManager
                                .NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        (LocationListener) this);

                lastLocationFromProvider();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void lastLocationFromProvider() {
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);

            if (location == null) {
                location = locationManager.getLastKnownLocation(android.location.LocationManager
                        .NETWORK_PROVIDER);
            }

            initializeLocationVariablen(location);
        }
    }

    // TODO Koordinaten müssen noch in Datenbank !
    private void initializeLocationVariablen(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }
    }

    private void createMarkerForMyTent() {
        LatLng myTent = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
                .position(myTent)
                .title("Mein Zelt"));

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