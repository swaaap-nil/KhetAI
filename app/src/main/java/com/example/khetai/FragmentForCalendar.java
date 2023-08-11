package com.example.khetai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.khetai.model.Land;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;


public class FragmentForCalendar extends Fragment implements OnMapReadyCallback {

    View view;
    FrameLayout mapLayout;
    int fragmentNumber;
    TextView textViewCropName;
    Double latitude,longitude;
    int landNumber;
    ArrayList<Land> myLandList;
    Gson gson = new Gson();
    SharedPreferences preferences;

    public FragmentForCalendar(int fragmentNumber,int landNumber,ArrayList<Land> myLandList){

        this.fragmentNumber=fragmentNumber;
        this.landNumber = this.landNumber;
        this.myLandList = myLandList;

//        preferences = this.getActivity().getSharedPreferences("FormFilled", Context.MODE_PRIVATE);
//        String json_landList = preferences.getString("myLandList", null);
//        myLandList = gson.fromJson(json_landList,
//                new TypeToken<List<Land>>() {
//                }.getType());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_for_map_slide_in_calendar, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_for_calendar,container,false);

        mapLayout = view.findViewById(R.id.frame_layout_for_map_slide_in_calendar);
        textViewCropName=view.findViewById(R.id.crop_name_inside_fragment_in_calendar);

        textViewCropName.setText("Land "+fragmentNumber);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        latitude = myLandList.get(landNumber).getLatitude();

        longitude = myLandList.get(landNumber).getLongitude();

        LatLng Gzb = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(Gzb)
                .title("Marker"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Gzb));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setTrafficEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
}
