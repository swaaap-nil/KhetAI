package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.khetai.adapter.Trio;
import com.example.khetai.adapter.YourCropsAdapter;
import com.example.khetai.model.Crop;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class CropChooseActivity4A extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<Crop> completeList;
    AppCompatButton saveCropListButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int LAUNCH_SECOND_ACTIVITY = 1;
    ArrayList<Crop> myCropList = MySingleton.getInstance().multipleCropHolder;
    Gson gson;
    int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_choose_4a);

        recyclerView = findViewById(R.id.cropChoose_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        saveCropListButton = findViewById(R.id.save_crop_selection_btn);
        preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        pushCropsInCompleteList();
        saveCompleteListToStorage();

        recyclerView.setAdapter(new YourCropsAdapter(completeList, this::onItemClick));
    }

    private void saveCompleteListToStorage() {
        String json = gson.toJson(completeList);
        editor.putString("completeList", json);
        editor.commit();

    }

    private void pushCropsInCompleteList() {
        completeList = new ArrayList<>();

        completeList.add(new Crop("Tomato", R.drawable.img_tomato,
                        17,30124,479876,160,"90-100","Pusa Ruby",
                new ArrayList<>(Arrays.asList(
                        Trio.createTrio("Urea", 84,R.drawable.urea),
                        Trio.createTrio("NPK 13-0-45", 84,R.drawable.vermicompost),
                        Trio.createTrio("NPK 19-19-19", 44,R.drawable.image1),
                        Trio.createTrio("NPK 12-61-0", 30,R.drawable.image1),

                        Trio.createTrio("Vermicompost", 1000,R.drawable.image1),
                        Trio.createTrio("Poultry Manure", 500,R.drawable.image1),
                        Trio.createTrio("Neem Cake", 50,R.drawable.image1)

                ))));


        completeList.add(new Crop("Broad Beans", R.drawable.img_broad_beans,
                    6,30812,178600,5000,"90-120","Pusa Sumeet",
                new ArrayList<>(Arrays.asList(
                        Trio.createTrio("Urea",23,R.drawable.image1),
                        Trio.createTrio("NPK 20-10-10",30,R.drawable.image1),
                        Trio.createTrio("NPK 19-19-19",40,R.drawable.image1),
                        Trio.createTrio("NPK 13-0-45",48,R.drawable.image1),
                        Trio.createTrio("Vermicompost",230,R.drawable.image1),
                        Trio.createTrio("Neem Cake", 80,R.drawable.image1),
                        Trio.createTrio("Cow Dung", 320,R.drawable.image1)
                ))));


        completeList.add(new Crop("Beetroot", R.drawable.img_beetroot,
                20, 34793, 171900, 3000, "70-80", "Detroit Dark Red",
                new ArrayList<>(Arrays.asList(
                        Trio.createTrio("NPK 19-19-19", 17,R.drawable.image1),
                        Trio.createTrio("NPK 12-61-0", 21,R.drawable.image1),
                        Trio.createTrio("NPK 13-0-45", 64,R.drawable.image1),
                        Trio.createTrio("NPK 0-0-50", 16,R.drawable.image1),

                        Trio.createTrio("Cow Dung", 260,R.drawable.image1),
                        Trio.createTrio("Neem Cake", 100,R.drawable.image1),
                        Trio.createTrio("Farm Yard Manure", 180,R.drawable.image1),
                        Trio.createTrio("Urea",74,R.drawable.image1)

                ))));

        completeList.add(new Crop("French Beans", R.drawable.img_frnch_beans,
                    8,47474,203000,35000,"55-60","Contender",
                new ArrayList<>(Arrays.asList(
                        Trio.createTrio("Urea", 74,R.drawable.image1),
                        Trio.createTrio("NPK 20-10-10", 40,R.drawable.image1),
                        Trio.createTrio("NPK 19-19-19", 80,R.drawable.image1),
                        Trio.createTrio("NPK 15-15-30", 40,R.drawable.image1),

                        Trio.createTrio("Vermicompost", 170,R.drawable.image1),
                        Trio.createTrio("Rhizobium", 1,R.drawable.image1),
                        Trio.createTrio("Farm Yard Manure", 200,R.drawable.image1)
                ))));


        completeList.add(new Crop("Bitter Gourd", R.drawable.img_bitter_gourd,
                    4,35120,198000,800,"100-120","CO-1",
                new ArrayList<>(Arrays.asList(
                        Trio.createTrio("NPK 19-19-19", 21,R.drawable.image1),
                        Trio.createTrio("NPK 13-0-45", 80,R.drawable.image1),
                        Trio.createTrio("NPK 12-61-0", 10,R.drawable.image1),
                        Trio.createTrio("Poultry Manure", 800,R.drawable.image1),
                        Trio.createTrio("Vermicompost", 120,R.drawable.image1),
                        Trio.createTrio("Neem Cake", 50,R.drawable.image1),
                        Trio.createTrio("Urea", 42,R.drawable.image1)
                ))));
    }


    @Override
    protected void onStart() {
        super.onStart();

        saveCropListButton.setOnClickListener(v -> {

            if (myCropList.size() == 0)
                Toast.makeText(getApplicationContext(), "You have not added any crop to your List", Toast.LENGTH_SHORT).show();
            else {
                unlockEditorAndSaveUserListToStorage();

                //start Main Activity
                Intent HomeIntent = new Intent(CropChooseActivity4A.this, MainActivity.class);
                startActivity(HomeIntent);
            }
        });

    }

    private void unlockEditorAndSaveUserListToStorage() {

        //myCropList = MySingleton.getInstance().multipleCropHolder;
        editor = preferences.edit();
        editor.putBoolean("registered", true);
        String json = gson.toJson(myCropList);
        editor.putString("myCropList", json);
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(
        Toast.makeText(getApplicationContext(), "There is no Back Action", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {

        Intent i = new Intent(this, CropChooseActivity4B.class);
        posi = position;

        i.putExtra("Name", completeList.get(position).getCropName());
        i.putExtra("cropImageID", completeList.get(position).getCropImage());

        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("result", 0);

                if (result == 1) {

//                    if(!myCropList.contains(completeList.get(posi))) {
//                        myCropList.add(completeList.get(posi));
                    Log.d("Addition", "Added " + completeList.get(posi).getCropName() + " to yourCropList succesfully");
//                        Toast.makeText(getApplicationContext(),
//                                "Added " + completeList.get(posi).getCropName() + " to your List",
//                                        Toast.LENGTH_SHORT).show();
//                    }
//                    else    Toast.makeText(getApplicationContext(),
//                            completeList.get(posi).getCropName() + " is already in your List",
//                            Toast.LENGTH_SHORT).show();
//                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    // Write your code if there's no result
                }
            }
        }
    }
}