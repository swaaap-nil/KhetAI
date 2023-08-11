package com.example.khetai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Land;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_xx extends Fragment implements OnMapReadyCallback {

    String cropName;
    View view;
    String added, remaining;
    ArrayList<Trio<String, Integer, Integer>> inputs;
    TextView textViewInputs, textViewAdded, textViewRemaining, cropFarmingPlaylist;
    FrameLayout mapLayout;
    CalendarView calendarView;
    ImageView playListImage;
    private static int NUM_PAGES;
    double latitude, longitude;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    SharedPreferences preferences;
    Gson gson = new Gson();
    ArrayList<Crop> myCropList = new ArrayList<>();
    ArrayList<Land> myLandList = new ArrayList<>();
    ArrayList<Integer> landNumberList = new ArrayList<>();
    private String sowingDateString;


    public Fragment_xx(String cropName, ArrayList<Trio<String, Integer, Integer>> inputs, double latitude, double longitude,String sowingDateString) {
        this.cropName = cropName;
        this.inputs = inputs;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sowingDateString = sowingDateString;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.framelayout2, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);

        textViewInputs = view.findViewById(R.id.textViewInputs);
        textViewAdded = view.findViewById(R.id.textViewAdded);
        textViewRemaining = view.findViewById(R.id.textViewRemaining);
        cropFarmingPlaylist = view.findViewById(R.id.textView_cropFarmingPlaylist);
        mapLayout = view.findViewById(R.id.framelayout2);
        playListImage = view.findViewById(R.id.playlistImage);
        calendarView = view.findViewById(R.id.calendarView2);
        viewPager = view.findViewById(R.id.viewpager2_in_each_fragment);

        preferences = getContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        cropFarmingPlaylist.setText(cropName + "Farming Playlist");

        String inputName = "";
        for (int i = 0; i < inputs.size(); i++)
            inputName = inputName.concat(inputs.get(i).getKey() + "\n");
        textViewInputs.setText(inputName);

        String inputAdded = "";
        for (int i = 0; i < inputs.size(); i++)
            inputAdded = inputAdded.concat("0\n");
        textViewAdded.setText(inputAdded);

        String inputRemaining = "";
        for (int i = 0; i < inputs.size(); i++)
            inputRemaining = inputRemaining.concat(inputs.get(i).getValue() + "\n");
        textViewRemaining.setText(inputRemaining);

        NUM_PAGES = landNumberList.size();

        pagerAdapter = new CalendarPageViewerAdapter(getActivity());
        viewPager.setAdapter(pagerAdapter);


        playListImage.setOnClickListener(v -> {
            Intent playListIntent = new Intent(getActivity(), PlaylistActivity.class);
            playListIntent.putExtra("CropName", "Rice");
            startActivity(playListIntent);
        });

        String json_cropList = preferences.getString("myCropList", null);
        myCropList = gson.fromJson(json_cropList,
                new TypeToken<List<Crop>>() {
                }.getType());

        String json_landList = preferences.getString("myLandList", null);
        myLandList = gson.fromJson(json_landList,
                new TypeToken<List<Land>>() {
                }.getType());
        Date sowingDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sowingDate = dateFormat.parse(sowingDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("excep","catch from Fragment_xx");
        }
        calendarView.setMinDate(sowingDate.getTime());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Intent intent = new Intent(getActivity(), ViewCropByDate.class);
            intent.putExtra("cropName", cropName);

            Date date = new Date(year - 1900, month, dayOfMonth);
            intent.putExtra("clickedDate", dateFormat.format(date));
            startActivity(intent);
        });
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ( getParentFragmentManager().findFragmentById(R.id.frameLayout2)).onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        ( getParentFragmentManager().findFragmentById(R.id.frameLayout2)).onPause();
//    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng Gzb;
        if (!(latitude == 0 || longitude == 0))
            Gzb = new LatLng(latitude, longitude);
        else
            Gzb = new LatLng(28.75500165, 77.49497748);

        googleMap.addMarker(new MarkerOptions()
                .position(Gzb)
                .title("Marker"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Gzb));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setTrafficEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    private class CalendarPageViewerAdapter extends FragmentStateAdapter {
        public CalendarPageViewerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new FragmentForCalendar(position + 1, landNumberList.get(position), myLandList);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}
