package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.khetai.adapter.TabAdapter;
import com.example.khetai.model.Crop;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TodaysActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ArrayList<Crop> yourCropList;
    Gson gson = new Gson();


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_test);

        preferences = getApplicationContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("myCropList", null);

        yourCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());

        ViewPager2 viewPager2 = findViewById(R.id.view_pager_today);
        TabLayout tabLayout = findViewById(R.id.tab_layout_today);

        List<Fragment> fragmentList = new ArrayList<>();
        int initiallySelectedTabIndex = 0;

        for(int i=0;i< yourCropList.size();i++)
        fragmentList.add(new FragmentToday(yourCropList.get(i).getCropName()));

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setAdapter(tabAdapter);
        viewPager2.setCurrentItem(initiallySelectedTabIndex);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(yourCropList.get(position).getCropName())).attach();
    }
}