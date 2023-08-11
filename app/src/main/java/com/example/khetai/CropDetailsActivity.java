package com.example.khetai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.khetai.adapter.TabAdapter;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Land;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CropDetailsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ArrayList<Crop> myCropList;
    Gson gson = new Gson();
    String cropName;
    private ArrayList<Land> myLandList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_details_activity);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        Bundle bundle = getIntent().getExtras();
        cropName = bundle.getString("Name");

        preferences = getApplicationContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("myCropList", null);

        myCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());

        String json_landList = preferences.getString("myLandList", null);
        myLandList = gson.fromJson(json_landList,
                new TypeToken<List<Land>>() {
                }.getType());

        if(myCropList.size()<4) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);}

        List<androidx.fragment.app.Fragment> fragmentList = new ArrayList<>();
        int initiallySelectedTabIndex = 0;


        double latitude=0.0;
        double  longitude=0.0;
        for (int i = 0; i < myCropList.size(); i++) {

            Crop cropInContext = myCropList.get(i);
            ArrayList<Integer>LandNumberList = new ArrayList<>();
            LandNumberList = cropInContext.getLandNumberList();
            int landNumber = LandNumberList.get(0);

        //TODO bug here code breaks when two crops are on same land
            for(int k=0;k< myLandList.size();k++){
                if (myLandList.get(k).getLandNumber()==landNumber){
                    latitude=myLandList.get(k).getLatitude();
                    longitude=myLandList.get(k).getLongitude();
                }

            }

            fragmentList.add(new Fragment_xx(cropInContext.getCropName(), cropInContext.getInputs(),latitude,longitude,cropInContext.getSowingDate()));


            if (myCropList.get(i).getCropName().equalsIgnoreCase(cropName)) {
                initiallySelectedTabIndex = i;
                Log.d("initiallySelected", "" + initiallySelectedTabIndex);
            }
        }

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setAdapter(tabAdapter);
        viewPager2.setCurrentItem(initiallySelectedTabIndex);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText((myCropList.get(position).getCropName()))).attach();

    }
}
