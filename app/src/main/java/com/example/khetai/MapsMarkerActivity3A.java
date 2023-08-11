// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.khetai;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
public class MapsMarkerActivity3A extends AppCompatActivity
        implements OnMapReadyCallback {

    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    private GoogleMap mMap;
    private ExtendedFloatingActionButton saveButton;
    private ExtendedFloatingActionButton currentLocationButton;
    double latitude;
    double longitude;
    int landNumber;
    Context context;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.

        Bundle bundle = getIntent().getExtras();
        landNumber = bundle.getInt("landNumber");

        setContentView(R.layout.activity_maps);




        saveButton = findViewById(R.id.extended_fab);
        currentLocationButton = findViewById(R.id.currentLocation_fab);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Latitude", latitude);
                returnIntent.putExtra("Longitude", longitude);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();


            }
        });

        currentLocationButton.setOnClickListener(view -> {

            getLocation();
            if(latitude == 0){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        getLocation();
                    }
                }, 2000);
            }
            else
            moveMarkerToCurrentLocation();
        });


    }

    void GeocodeAndGetCoordinates() throws IOException {
        Geocoder geocoder = new Geocoder(this);
        preferences = getSharedPreferences("FormFilled",MODE_PRIVATE);
        Log.d("checkingAddress",""+preferences.getString("Address","default address"));
        List<Address> addresses = geocoder.getFromLocationName(preferences.getString("Address","default address"), 1);
        latitude = addresses.get(0).getLatitude();
        longitude = addresses.get(0).getLongitude();
    }

    private void moveMarkerToCurrentLocation() {

        LatLng currentLocation = new LatLng(latitude, longitude);
        mMap.clear();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        mMap.addMarker(new MarkerOptions()
                        .position(currentLocation)
                        .title("Marker in current location"))
                .setDraggable(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //getLocation();
        try {
            GeocodeAndGetCoordinates();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title("Current Location"))
                .setDraggable(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                LatLng position = marker.getPosition();
                latitude = position.latitude;
                longitude = position.longitude;
                context = getApplicationContext();
                Toast.makeText(context, "Latitude : " + String.format("%.5f",latitude) + "\nLongitude : " + String.format("%.5f",longitude), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
            }
        });

        // [END_EXCLUDE]
    }

    public void getLocation() {
        locationTrack = new LocationTrack(MapsMarkerActivity3A.this);
        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();


            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }
    }


    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MapsMarkerActivity3A.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}

