//package com.example.khetai;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.os.Bundle;
//
//public class MapTestActivity extends AppCompatActivity {
//
//    private ViewPager2 viewPager;
//    private PagerAdapter pagerAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       // setContentView(R.layout.activity_map_test);
//
//        Fragment fragment = new Map_Fragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager2layout,fragment).commit();
//    }
//
//}