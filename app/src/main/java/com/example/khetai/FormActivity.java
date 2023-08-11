package com.example.khetai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.khetai.model.Farmer;

import java.util.ArrayList;


public class FormActivity extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText helpersCount;
    EditText area;
    EditText landCount;
    AppCompatButton nextButton;
    int count;
    Farmer farmer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = (EditText) findViewById(R.id.ip_name);
        age = (EditText) findViewById(R.id.ip_age);
        helpersCount = (EditText) findViewById(R.id.ip_helperCount);
        area = (EditText) findViewById(R.id.ip_totalLandArea);
        landCount = (EditText) findViewById(R.id.ip_landCount);
        nextButton = (AppCompatButton) findViewById(R.id.save_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = Integer.parseInt(landCount.getText().toString());
                preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
                editor = preferences.edit();
               //saveFarmerDetails();
                editor.putString("name",name.getText().toString());
                editor.putInt("age",Integer.parseInt( age.getText().toString()));
                editor.putFloat("area",Float.parseFloat(area.getText().toString()));
                editor.putInt("landCount",Integer.parseInt(landCount.getText().toString()));
                editor.putBoolean("registered", true);
                editor.commit();
                //Log.d("MyApp", "registered flag set to true");

                boolean registered = preferences.getBoolean("registered", false);
                Log.d("MyApp", "Now registered is: " + registered);
                Intent intent = new Intent(getApplicationContext(), LocationSetActivity3.class);
                intent.putExtra("count",count );
                startActivity(intent);
            }

        });


    }

    private void saveFarmerDetails() {

        farmer = new Farmer();
        farmer.setName(name.getText().toString());
        farmer.setAge(Integer.parseInt( age.getText().toString()));
        farmer.setHelpersCount(Integer.parseInt(helpersCount.getText().toString()));
        farmer.setTotalArea(Float.parseFloat(area.getText().toString()));
        farmer.setLandCount(Integer.parseInt(landCount.getText().toString()));
        farmer.setIndividualLandSize(new ArrayList<Float>() );

        editor.putString("Name", farmer.getName());
        editor.putInt("Age",farmer.getAge());
        editor.putInt("HelpersCount",farmer.getHelpersCount());
        editor.putFloat("TotalArea",farmer.getTotalArea());
        editor.putInt("LandCount",farmer.getLandCount());

    }


}
