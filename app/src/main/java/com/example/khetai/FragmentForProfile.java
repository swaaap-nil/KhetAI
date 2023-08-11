package com.example.khetai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentForProfile extends Fragment implements OnMapReadyCallback {

    View view;
    FrameLayout mapLayout;
    int fragmentNumber;
    TextView textViewCropName;
    Double latitude,longitude;

        public FragmentForProfile(int number,Double latitude,Double longitude){

            this.fragmentNumber=number;
            this.latitude=latitude;
            this.longitude=longitude;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_for_map, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yy,container,false);

        mapLayout = view.findViewById(R.id.frame_layout_for_map);
        textViewCropName=view.findViewById(R.id.crop_name_inside_fragment);

        textViewCropName.setText("Land "+fragmentNumber);

        return view;
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

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
