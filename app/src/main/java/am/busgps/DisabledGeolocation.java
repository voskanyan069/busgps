//package am.busgps;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//
//public class DisabledGeolocation {
//    private int REQUEST_CHECK_SETTINGS = 0;
//    private Button checkButton;
//    private GoogleApiClient googleApiClient;
//    private LocationListener locationListener;
//    private LocationManager locationManager;
//    private LocationRequest locationRequest;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_disabled_geolocation);
//
//        init();
//        clickCheckButton();
//    }
//
//    public void init() {
////        checkButton = findViewById(R.id.check_again);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        locationRequest = LocationRequest.create();
//    }
//
//    public void clickCheckButton() {
////        checkButton.setOnClickListener(new OnClickListener() {
////            public void onClick(View view) {
////                checkAgain();
////            }
////        });
//    }
//
//    public void checkAgain() {
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            changeActivityToMaps();
//            return;
//        }
//        Toast.makeText(DisabledGeolocationActivity.this, "Error: Please check and try again ...", Toast.LENGTH_LONG).show();
//        displayLocationSettingsRequest(DisabledGeolocationActivity.this);
//    }
//
//    public void changeActivityToMaps() {
//        Intent intent = new Intent(DisabledGeolocationActivity.this, MapsActivity.class);
//        startActivity(intent);
//    }
//
//    public void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient build = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
//        googleApiClient = build;
//        build.connect();
//        locationRequest.setPriority(100);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(500);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build()).setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            public void onResult(LocationSettingsResult result) {
//                Status status = result.getStatus();
//                int statusCode = status.getStatusCode();
//                if (statusCode == 0) {
//                    Log.i("TAG", "All location settings are satisfied.");
//                    locationListener = new LocationListener() {
//                        public void onLocationChanged(Location location) {
//                        }
//                    };
//                    if (ActivityCompat.checkSelfPermission(DisabledGeolocationActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(DisabledGeolocationActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10.0f, locationListener);//package am.busgps;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//
//public class DisabledGeolocation {
//    private int REQUEST_CHECK_SETTINGS = 0;
//    private Button checkButton;
//    private GoogleApiClient googleApiClient;
//    private LocationListener locationListener;
//    private LocationManager locationManager;
//    private LocationRequest locationRequest;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_disabled_geolocation);
//
//        init();
//        clickCheckButton();
//    }
//
//    public void init() {
////        checkButton = findViewById(R.id.check_again);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        locationRequest = LocationRequest.create();
//    }
//
//    public void clickCheckButton() {
////        checkButton.setOnClickListener(new OnClickListener() {
////            public void onClick(View view) {
////                checkAgain();
////            }
////        });
//    }
//
//    public void checkAgain() {
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            changeActivityToMaps();
//            return;
//        }
//        Toast.makeText(DisabledGeolocationActivity.this, "Error: Please check and try again ...", Toast.LENGTH_LONG).show();
//        displayLocationSettingsRequest(DisabledGeolocationActivity.this);
//    }
//
//    public void changeActivityToMaps() {
//        Intent intent = new Intent(DisabledGeolocationActivity.this, MapsActivity.class);
//        startActivity(intent);
//    }
//
//    public void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient build = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
//        googleApiClient = build;
//        build.connect();
//        locationRequest.setPriority(100);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(500);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build()).setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            public void onResult(LocationSettingsResult result) {
//                Status status = result.getStatus();
//                int statusCode = status.getStatusCode();
//                if (statusCode == 0) {
//                    Log.i("TAG", "All location settings are satisfied.");
//                    locationListener = new LocationListener() {
//                        public void onLocationChanged(Location location) {
//                        }
//                    };
//                    if (ActivityCompat.checkSelfPermission(DisabledGeolocationActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(DisabledGeolocationActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10.0f, locationListener);
//                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                            changeActivityToMaps();
//                        }
//                    }
//                } else if (statusCode == 6) {
//                    Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//                    try {
//                        status.startResolutionForResult(DisabledGeolocationActivity.this, REQUEST_CHECK_SETTINGS);
//                    } catch (IntentSender.SendIntentException e) {
//                        Log.i("TAG", "PendingIntent unable to execute request.");
//                    }
//                }
//            }
//        });
//    }
//}

//                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                            changeActivityToMaps();
//                        }
//                    }
//                } else if (statusCode == 6) {
//                    Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//                    try {
//                        status.startResolutionForResult(DisabledGeolocationActivity.this, REQUEST_CHECK_SETTINGS);
//                    } catch (IntentSender.SendIntentException e) {
//                        Log.i("TAG", "PendingIntent unable to execute request.");
//                    }
//                }
//            }
//        });
//    }
//}
