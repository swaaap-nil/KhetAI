package com.example.khetai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.khetai.model.Crop;
import com.example.khetai.model.Land;
import com.example.khetai.model.Scheme;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends FragmentActivity  {


    ArrayList<Scheme> schemeData;
    ArrayList<Land> myLandList;
    ArrayList<Crop> myCropList;
    private static int NUM_PAGES;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    IndicatorView indicatorView;
    SharedPreferences preferences;
    Gson gson = new Gson();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test);
        indicatorView = findViewById(R.id.indicator_view);
        ShapeableImageView add_land = findViewById(R.id.button4);
        viewPager = findViewById(R.id.viewpager2layout);
        pagerAdapter = new ProfilePageViewerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        ShapeableImageView add_crop = findViewById(R.id.btn_add_crop);

        add_land.setOnClickListener(v -> {
            Intent landIntent = new Intent(ProfileActivity.this,FormTestActivity2.class);
            startActivity(landIntent);
        });

        preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);


        String json_landList = preferences.getString("myLandList", null);
        myLandList = gson.fromJson(json_landList,
                new TypeToken<List<Land>>() {
                }.getType());


        String json_cropList = preferences.getString("myCropList", null);
        myCropList = gson.fromJson(json_cropList,
                new TypeToken<List<Crop>>() {
                }.getType());

        NUM_PAGES=myLandList.size();

        indicatorView.setSliderColor(getResources().getColor(R.color.light_gray), getResources().getColor(R.color.green));
        indicatorView.setSliderWidth(getResources().getDimension(R.dimen.dp_10));
        indicatorView.setSliderHeight(getResources().getDimension(R.dimen.dp_5));
        indicatorView.setSlideMode(IndicatorSlideMode.WORM);
        indicatorView.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        indicatorView.setupWithViewPager(viewPager);

//        add_land.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProfileActivity.this,FormTestActivity2.class);
//                startActivity(intent);
//            }
//        });

        add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,CropChooseActivity4A.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
       // if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
    }



//    private void pushSchemeData() {
//
//        schemeData= new ArrayList<>();
//        schemeData.add(new Scheme(R.drawable.ic_launcher_foreground,"Scheme 1","Description 1"));
//        schemeData.add(new Scheme(R.drawable.ic_launcher_foreground,"Scheme 2","Description 2"));
//        schemeData.add(new Scheme(R.drawable.ic_launcher_foreground,"Scheme 3","Description 3"));
//        schemeData.add(new Scheme(R.drawable.ic_launcher_foreground,"Scheme 4","Description 4"));
//        schemeData.add(new Scheme(R.drawable.ic_launcher_foreground,"Scheme 5","Description 5"));
//    }


    private class ProfilePageViewerAdapter extends FragmentStateAdapter {
        public ProfilePageViewerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new FragmentForProfile(position+1,
                                        myLandList.get(position).getLatitude(),
                                        myLandList.get(position).getLongitude());
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}


