package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    // number of selected tab. we have 4 tabs so value must lie between 1-4. default value is 1 because first tab is selected by default.
    private int selectedTab = 1;
    boolean registered ;
    boolean doubleBackToExitPressedOnce = false;

    LinearLayout homeLayout,marketLayout,calendarLayout,bookLayout;

    ImageView homeImage,marketImage,calendarImage,bookImage;

    TextView homeTxt,marketTxt,calendarTxt,bookTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("FormFilled",MODE_PRIVATE);
        registered = preferences.getBoolean("registered",false);

        if (!registered) {
            Log.d("MyApp", "Showing form because registered is: " + registered);
            Intent registerIntent = new Intent(this, NameAndLoginActivity1test.class);
            startActivity(registerIntent);
        } else {

            Log.d("MyApp", "Not showing form because registered is: " + registered);

            setContentView(R.layout.activity_main);

//            final LinearLayout homeLayout = findViewById(R.id.homeLayout);
//            final LinearLayout marketLayout = findViewById(R.id.marketLayout);
//            final LinearLayout calendarLayout = findViewById(R.id.calendarLayout);
//            final LinearLayout bookLayout = findViewById(R.id.bookLayout);

            homeLayout = findViewById(R.id.homeLayout);
             marketLayout = findViewById(R.id.marketLayout);
              calendarLayout = findViewById(R.id.calendarLayout);
            bookLayout = findViewById(R.id.bookLayout);

//            final ImageView homeImage = findViewById(R.id.homeImage);
//            final ImageView marketImage = findViewById(R.id.marketImage);
//            final ImageView calendarImage = findViewById(R.id.calendarImage);
//            final ImageView bookImage = findViewById(R.id.bookImage);

             homeImage = findViewById(R.id.homeImage);
            marketImage = findViewById(R.id.marketImage);
             calendarImage = findViewById(R.id.calendarImage);
             bookImage = findViewById(R.id.bookImage);

            homeTxt = findViewById(R.id.homeTxt);
            marketTxt = findViewById(R.id.marketTxt);
             calendarTxt = findViewById(R.id.calendarTxt);
             bookTxt = findViewById(R.id.bookTxt);


            // set home fragment by default
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, HomeFragment.class, null)
                    .commit();

            homeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // check if home is already selected or not.
                    if (selectedTab != 1) {

                        // set home fragment
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, new HomeFragment(), null)
                                .commit();

                        // unselect other tabs expect home tab
                        marketTxt.setVisibility(View.GONE);
                        calendarTxt.setVisibility(View.GONE);
                        bookTxt.setVisibility(View.GONE);

                        marketImage.setImageResource(R.drawable.market_icon);
                        calendarImage.setImageResource(R.drawable.calendar_icon);
                        bookImage.setImageResource(R.drawable.book_icon);

                        marketLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        calendarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        bookLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        // select home tab
                        homeTxt.setVisibility(View.VISIBLE);
                        homeImage.setImageResource(R.drawable.home_selected_icon);
                        homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        homeLayout.startAnimation(scaleAnimation);

                        // set 1st tab as selected tab
                        selectedTab = 1;
                    }
                }
            });

            marketLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // check if market tab is already selected or not.
                    if (selectedTab != 2) {

                        // set market fragment
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, MarketFragment.class, null)
                                .commit();

                        // unselect other tabs expect like tab
                        homeTxt.setVisibility(View.GONE);
                        calendarTxt.setVisibility(View.GONE);
                        bookTxt.setVisibility(View.GONE);

                        homeImage.setImageResource(R.drawable.home_icon);
                        calendarImage.setImageResource(R.drawable.calendar_icon);
                        bookImage.setImageResource(R.drawable.book_icon);

                        homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        calendarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        bookLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        // select home tab
                        marketTxt.setVisibility(View.VISIBLE);
                        marketImage.setImageResource(R.drawable.market_selected_icon);
                        marketLayout.setBackgroundResource(R.drawable.round_back_home_100);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        marketLayout.startAnimation(scaleAnimation);

                        // set 2st tab as selected tab
                        selectedTab = 2;
                    }
                }
            });

            calendarLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // check if notification tab is already selected or not.
                    if (selectedTab != 3) {

                        // set notification fragment
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, CalendarFragment.class, null)
                                .commit();

                        // unselect other tabs expect notification tab
                        homeTxt.setVisibility(View.GONE);
                        marketTxt.setVisibility(View.GONE);
                        bookTxt.setVisibility(View.GONE);

                        homeImage.setImageResource(R.drawable.home_icon);
                        marketImage.setImageResource(R.drawable.market_icon);
                        bookImage.setImageResource(R.drawable.book_icon);

                        homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        marketLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        bookLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        // select home tab
                        calendarTxt.setVisibility(View.VISIBLE);
                        calendarImage.setImageResource(R.drawable.calendar_selected_icon);
                        calendarLayout.setBackgroundResource(R.drawable.round_back_home_100);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        calendarLayout.startAnimation(scaleAnimation);

                        // set 3rd tab as selected tab
                        selectedTab = 3;
                    }
                }
            });

            bookLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // check if profile tab is already selected or not.
                    if (selectedTab != 4) {

                        // set profile fragment
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, SchoolFragment.class, null)
                                .commit();

                        // unselect other tabs expect profile tab
                        homeTxt.setVisibility(View.GONE);
                        marketTxt.setVisibility(View.GONE);
                        calendarTxt.setVisibility(View.GONE);

                        homeImage.setImageResource(R.drawable.home_icon);
                        marketImage.setImageResource(R.drawable.market_icon);
                        calendarImage.setImageResource(R.drawable.calendar_icon);

                        homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        marketLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        calendarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        // select home tab
                        bookTxt.setVisibility(View.VISIBLE);
                        bookImage.setImageResource(R.drawable.book_selected_icon);
                        bookLayout.setBackgroundResource(R.drawable.round_back_home_100);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        bookLayout.startAnimation(scaleAnimation);

                        // set 4th tab as selected tab
                        selectedTab = 4;
                    }
                }
            });

        }
    }



        @Override
    public void onBackPressed() {
        if(selectedTab==1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 3000);
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, new HomeFragment(), null)
                    .commit();

            // unselect other tabs expect home tab
            marketTxt.setVisibility(View.GONE);
            calendarTxt.setVisibility(View.GONE);
            bookTxt.setVisibility(View.GONE);

            marketImage.setImageResource(R.drawable.market_icon);
            calendarImage.setImageResource(R.drawable.calendar_icon);
            bookImage.setImageResource(R.drawable.book_icon);

            marketLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            calendarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            bookLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // select home tab
            homeTxt.setVisibility(View.VISIBLE);
            homeImage.setImageResource(R.drawable.home_selected_icon);
            homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

            // create animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            homeLayout.startAnimation(scaleAnimation);

            // set 1st tab as selected tab
            selectedTab = 1;
    }
}}