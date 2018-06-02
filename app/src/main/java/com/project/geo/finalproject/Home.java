package com.project.geo.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Home extends AppCompatActivity implements OnMapReadyCallback, LocationListener {


    boolean GpsStatus;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private Button Go;

    private String Email, Fname, Lname, Gender, Birthdate, ProfileImage;
    private GpsTracker gpsTracker;
    public Double aLong, lati;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Email = getIntent().getStringExtra("Username");
        Fname = getIntent().getStringExtra("Fname");
        Lname = getIntent().getStringExtra("Lname");
        Gender = getIntent().getStringExtra("Gender");
        ProfileImage = getIntent().getStringExtra("ProfileImage");
        Birthdate = getIntent().getStringExtra("Birthdte");

        getLocation();
        CheckGpsStatus();

        if (GpsStatus == true) {

            gpsTracker = new GpsTracker(Home.this);

            if (gpsTracker.canGetLocation()) {
                lati = gpsTracker.getLatitude();
                aLong = gpsTracker.getLongitude();

            } else {
                gpsTracker.showSettingsAlert();
            }


            Go = findViewById(R.id.button2);


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map1);
            mapFragment.getMapAsync(this);


        } else {

            Toast.makeText(this, " Open GPS to  select your location ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Point = new LatLng(lati, aLong);
        mMap.addMarker(new MarkerOptions().position(Point).title(Fname + " " + Lname));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Point, 14));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.addCircle(new CircleOptions()
                .center(new LatLng(lati,aLong))
                .radius(300)
                .strokeColor(Color.RED)
                .fillColor(0x220000FF)
                .strokeWidth(3)
        );

    }


    public void CheckGpsStatus() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void GoFun(View v) {
        try {
            Intent intent2 = new Intent(Home.this, Main2Activity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("Username", Email);
            intent2.putExtra("Fname", Fname);
            intent2.putExtra("Lname", Lname);
            intent2.putExtra("Gender", Gender);
            intent2.putExtra("ProfileImage", ProfileImage);
            intent2.putExtra("Birthdte", Birthdate);
            startActivity(intent2);
        } catch (Exception e) {

        }

    }
}
