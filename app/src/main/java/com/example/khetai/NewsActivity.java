package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    TextView newsHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");

        newsHeading=findViewById(R.id.newsHeading);

        newsHeading.setText("News Heading "+position);




    }
}