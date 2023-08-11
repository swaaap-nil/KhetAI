package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.khetai.model.Farmer;
import com.google.android.material.textfield.TextInputLayout;

public class FormActivityTest extends AppCompatActivity {

    TextInputLayout name;
    TextInputLayout age;
    TextInputLayout helpersCount;
    TextInputLayout area;
    TextInputLayout landCount;
    AppCompatButton nextButton;
    int count;
    Farmer farmer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_test);

        name =  findViewById(R.id.ip_name);
        age = findViewById(R.id.ip_age);
        helpersCount =  findViewById(R.id.ip_helperCount);
        area =  findViewById(R.id.ip_totalLandArea);
        landCount =  findViewById(R.id.ip_landCount);
        nextButton =  findViewById(R.id.save_btn);


    }

    @Override
    protected void onStart() {
        super.onStart();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(landCount.getEditText().getText().toString());

                Log.d("MyApp", "Now count is: " + count);
                preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name",name.getEditText().getText().toString());
                editor.putInt("age",Integer.parseInt( age.getEditText().getText().toString()));
                editor.putFloat("area",Float.parseFloat(area.getEditText().getText().toString()));
                editor.putInt("landCount",Integer.parseInt(landCount.getEditText().getText().toString()));
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
}