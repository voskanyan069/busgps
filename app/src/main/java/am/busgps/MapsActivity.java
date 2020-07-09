/*
    * NAME:         BUS GPS
    * AUTHOR:       ANDRANIK VOSKANYAN
    * IDEA:         AREN MESROPYAN
    * VERSION:      0.0.2
    * CREATED:      1 JUN 2020
    * LAST UPDATE:  9 JUN 2020
    * GITHUB:       https://github.com/voskanyan069/busgps
 */


//    <----------------------------------------------------------->
//                              IMPORTS
//    <----------------------------------------------------------->

package am.busgps;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//    <----------------------------------------------------------->

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener, OnMyLocationClickListener {

//    <----------------------------------------------------------->
//                            VARIABLES
//    <----------------------------------------------------------->

    private static GoogleMap mMap;
    private int delayMillis = 5000;
    private Handler handler = new Handler();
    private int index;
    private List<IndexLatLng> list;
    private DatabaseReference locRef;
    private LatLng location;
    private LocationManager locationManager;
    private FirebaseDatabase mDatabase;
    private LatLng marker_1;
    private LatLng myLoc;
    private int size;
    private LatLng vanadzor;

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                       ON ACTIVITY CREATE
//    <----------------------------------------------------------->

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapsActivity.this);

        setLocationManager();
        init();
        checkLocationManager();
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                          ON MAP LOADED
//    <----------------------------------------------------------->

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mapSettings();
            markersToDb();
            moveCameraToMyLocation();
            refresh();
        } else {
            changeToDisabledGeolocation();
        }
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                     VARIABLES INITIALIZATION
//    <----------------------------------------------------------->

    public void init() {
        mDatabase = FirebaseDatabase.getInstance();
        locRef = mDatabase.getReference().child("Location");
        setLocationManager();
        locationsInit();
        list = new ArrayList();
        Toast.makeText(this, "Please if app opened incorrect, restart it", Toast.LENGTH_LONG).show();
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                           SET LOCATION MANAGER
//    <----------------------------------------------------------->

    public void setLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                      LOCATIONS INITIALIZATION
//    <----------------------------------------------------------->

    public void locationsInit() {
        vanadzor = new LatLng(40.811161d, 44.485173d);
        marker_1 = new LatLng(40.80884d, 44.492382d);
        location = new LatLng(40.8108623d, 44.4892391d);
    }

//    <----------------------------------------------------------->
//                              MAP SETTINGS
//    <----------------------------------------------------------->

    public void mapSettings() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(this.vanadzor));
            mMap.setMaxZoomPreference(18.0f);
            mMap.setMinZoomPreference(16.0f);
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(MapsActivity.this);
            mMap.setOnMyLocationClickListener(MapsActivity.this);
        }
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                      ADD MARKERS TO DATABASE
//    <----------------------------------------------------------->

    public void markersToDb() {
        index = 0;
        locRef.removeValue();

        locRef.child(String.valueOf(index++)).setValue(vanadzor);
        locRef.child(String.valueOf(index++)).setValue(marker_1);
        locRef.child(String.valueOf(index++)).setValue(location);
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                  MOVE CAMERA TO USER LOCATION
//    <----------------------------------------------------------->

    public void moveCameraToMyLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                myLoc = new LatLng(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(vanadzor));
            }
        } catch (NullPointerException e) {
            setLocationManager();
            moveCameraToMyLocation();
        }
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                      CHECK LOCATION MANAGER
//    <----------------------------------------------------------->

    public void checkLocationManager() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            refresh();
        } else {
            changeToDisabledGeolocation();
        }
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                CHANGE ACTIVITY IF GEOLOCATION DISABLED
//    <----------------------------------------------------------->

    public void changeToDisabledGeolocation() {
        System.out.println("<= ACTIVITY CHANGED TO DISABLED_GEOLOCATION_ACTIVITY =>");
        Intent intent = new Intent(MapsActivity.this, DisabledGeolocationActivity.class);
        startActivity(intent);
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                 ADD MARKERS FROM DATABASE TO MAP
//    <----------------------------------------------------------->

    public void addMarkersToMap() {
        this.locRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
                mMap.clear();
                list.clear();
                for (int ind = 0; ind < size; ++ind) {
                    try {
                        IndexLatLng indexLatLng = new IndexLatLng(ind, (Double) dataSnapshot.child(String.valueOf(ind)).child("latitude").getValue(), (Double) dataSnapshot.child(String.valueOf(ind)).child("longitude").getValue());
                        list.add(indexLatLng);

                        LatLng loc = new LatLng(list.get(ind).getLat(), list.get(ind).getLng());

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_bus);
//                        BitmapDescriptor myLocIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_location);

                        if (myLoc != null) {
                            int time = (int) distanceCalculate(loc, myLoc)[0] / 13;

                            mMap.addMarker(new MarkerOptions().position(loc).title("~" + time + " sec").icon(icon));
//                            mMap.addMarker(new MarkerOptions().position(myLoc).title("My Location").icon(myLocIcon));

//                            mMap.addMarker(new MarkerOptions().position(loc).title(timeText.toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus)));

                        } else {
                            mMap.addMarker(new MarkerOptions().position(loc).icon(icon));
                        }

                    } catch (Exception e) {
                        Toast.makeText(MapsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

//    <----------------------------------------------------------->
//                             REFRESH
//    <----------------------------------------------------------->

    public void refresh() {
        addMarkersToMap();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkLocationManager();
                refreshMethods();
                handler.postDelayed(this, delayMillis);
            }
        }, delayMillis);
    }

    public void refreshMethods() {
        System.out.println("<= METHOD REFRESHING =>");
        checkLocationManager();
        locationsInit();
        markersToDb();
        addMarkersToMap();
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//              CALCULATE DISTANCE BETWEEN TWO LOCATIONS
//    <----------------------------------------------------------->

    public float[] distanceCalculate(LatLng from, LatLng to) {
        float[] result = new float[1];
        Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, result);
        return result;
    }

//    <----------------------------------------------------------->

//    <----------------------------------------------------------->
//                     UNUSED IMPLEMENTATIONS
//    <----------------------------------------------------------->

    public boolean onMyLocationButtonClick() { return false; }

    public void onMyLocationClick(Location location2) {}

//    <----------------------------------------------------------->

}
