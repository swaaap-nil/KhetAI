package com.example.khetai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.widget.TextView;
import android.widget.Toast;

import com.example.khetai.adapter.LandCountAdapter;
import com.example.khetai.model.Land;
import com.google.gson.Gson;


public class LocationSetActivity3 extends AppCompatActivity implements RecyclerViewInterface {

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    RecyclerView recyclerView;
    int newLandCount;
    AppCompatButton SaveAllLandDetailsButton;
    double longitude;
    double latitude;
    LandCountAdapter landCountAdapter;
    TextView FarmDetailsText;
    int currentLandNumber;
    int LAUNCH_SECOND_ACTIVITY = 1;



    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_set_3);
        Bundle bundle = getIntent().getExtras();
        newLandCount = bundle.getInt("landCount");
        recyclerView =  findViewById(R.id.form_RV);
        FarmDetailsText = findViewById(R.id.textView_farmDetails);

        if(MySingleton.getInstance().multipleLandHolder.isEmpty())
        for (int i = 1; i <= newLandCount; i++) {
            MySingleton.getInstance().multipleLandHolder.add(new Land(i, 0, 0, 0));
            Log.d("Singleton", "land " + i + "Added");
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        landCountAdapter = new LandCountAdapter(MySingleton.getInstance().multipleLandHolder, this::onItemClick);
        recyclerView.setAdapter(landCountAdapter);


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

        //saveButton= findViewById(R.id.save_button_location);
        SaveAllLandDetailsButton = findViewById(R.id.save_location_details_button);

        SaveAllLandDetailsButton.setOnClickListener(v -> {

            for (int i = 0; i < MySingleton.getInstance().multipleLandHolder.size(); i++) {
                Log.d("LandDetails", "Land " + i + " Area" + MySingleton.getInstance().multipleLandHolder.get(i).getLatitude());
            }

            SharedPreferences preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            Gson gson = new Gson();
            ArrayList<Land> myLandList = MySingleton.getInstance().multipleLandHolder;
            String json = gson.toJson(myLandList);
            editor.putString("myLandList", json);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String currentDate = sdf.format(new Date());
//            Log.d("loginDate",currentDate);
//            editor.putString("loginDate",currentDate);
            editor.commit();


//            for (int i = 0; i < myLandList.size(); i++) {
//
//                Log.d("landlist", " land " + i + " landNumber " + myLandList.get(i).getLandNumber());
//                Log.d("landlist", " land " + i + " latitude " + myLandList.get(i).getLatitude());
//                Log.d("landlist", " land " + i + " longitude " + myLandList.get(i).getLongitude());
//                Log.d("landlist", " land " + i + " Area " + myLandList.get(i).getArea());
//
//
//            }

            Intent intent = new Intent(LocationSetActivity3.this, CropChooseActivity4A.class);
            startActivity(intent);

        });

        FarmDetailsText.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onItemClick(int position) {


        Intent intent = new Intent(LocationSetActivity3.this, MapsMarkerActivity3A.class);
        currentLandNumber = MySingleton.getInstance().multipleLandHolder.get(position).getLandNumber();
        intent.putExtra("landNumber", currentLandNumber);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle bundle = data.getExtras();
                latitude = bundle.getDouble("Latitude", 0);
                longitude = bundle.getDouble("Longitude", 0);

                MySingleton.getInstance().multipleLandHolder.get(currentLandNumber - 1).setLatitude((float) latitude);
                MySingleton.getInstance().multipleLandHolder.get(currentLandNumber - 1).setLongitude((float) longitude);
                landCountAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
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
        new AlertDialog.Builder(LocationSetActivity3.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void getLocation() {
        locationTrack = new LocationTrack(LocationSetActivity3.this);
        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();


            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }
    }

}
