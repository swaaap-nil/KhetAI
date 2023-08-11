package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.khetai.adapter.InputsAtOnePlaceAdapter;
import com.example.khetai.model.Crop;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InputsAtOnePlace extends AppCompatActivity {

    SharedPreferences preferences;
    ArrayList<Crop> myCropList;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputs_at_one_place);

        preferences= getApplicationContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("myCropList", null);

        myCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());

        RecyclerView recyclerView = findViewById(R.id.xxx);
        recyclerView.setAdapter(new InputsAtOnePlaceAdapter(myCropList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}