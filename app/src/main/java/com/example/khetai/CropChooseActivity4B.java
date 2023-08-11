package com.example.khetai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khetai.adapter.InputsAdapter;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Land;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CropChooseActivity4B extends AppCompatActivity {

    AppCompatButton cropAddButton;
    TextView headerText, yield, investement, profit, ipX_heading, ipY_heading, seed_requirement, time_period, T6, T7, overView;
    ShapeableImageView cropImage;
    MaterialCardView cardView;
    AutoCompleteTextView dropDownTextForLandChoice;
    String clickedCropName;
    int cropImageID;
    ArrayList<Crop> myCropList;
    SharedPreferences preferences;
    Gson gson = new Gson();
    Crop clickedCrop;
    ArrayList<Crop> completeList;
    TextInputEditText datepicker;
    SharedPreferences.Editor editor;
    String sowingDate;
    boolean hasSelectedDate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_choose_4b);

        Bundle bundle = getIntent().getExtras();
        clickedCropName = bundle.getString("Name");
        cropImageID = bundle.getInt("cropImageID");
        MySingleton.getInstance().multipleLandHolder = new ArrayList<>();
        myCropList = MySingleton.getInstance().multipleCropHolder;

        headerText = findViewById(R.id.header_text);
        yield = findViewById(R.id.variable1);
        investement = findViewById(R.id.variable2);
        profit = findViewById(R.id.variable3);
        seed_requirement = findViewById(R.id.variable4);
        time_period = findViewById(R.id.variable5);

        overView = findViewById(R.id.overView);
        cropImage = findViewById(R.id.shapeableImageView2);
        cardView = findViewById(R.id.cardview7);
        dropDownTextForLandChoice = findViewById(R.id.dropdown_text_choose_land_number);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropChooseActivity4B.this, NotAvPage.class);
                startActivity (intent);
            }
        });






        overView.setText("Overview For " + clickedCropName + " Farming");
        cropImage.setImageResource(cropImageID);

        datepicker = findViewById(R.id.editText_yourName1);
        datepicker.setFocusable(false);
        datepicker.setFocusableInTouchMode(false);


        datepicker.setOnClickListener(v -> {
            pickDate();
            hasSelectedDate=true;
        });

        preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json_cropList = preferences.getString("completeList", null);
        completeList = gson.fromJson(json_cropList,
                new TypeToken<List<Crop>>() {
                }.getType());

        //finding index of clicked crop in myCropList
        int index = -1;
        for (int i = 0; i < completeList.size(); i++)
            if (completeList.get(i).equals(new Crop(clickedCropName, 0))) {
                Log.d("qwer", i + " " + clickedCropName);
                index = i;
                break;
            }

        // index = completeList.indexOf(new Crop(clickedCropName,0));

        clickedCrop = completeList.get(index);
        profit.setText("₹ " + clickedCrop.getProfit());
        headerText.setText(clickedCrop.getCropName() + "(" + clickedCrop.getVariety() + ")");
        investement.setText("₹ " + clickedCrop.getInvestment());
        yield.setText(clickedCrop.getYield() + " tonne/acre");
        seed_requirement.setText(clickedCrop.getSeedRequirement() + "g");
        time_period.setText(clickedCrop.getTimePeriod() + " Days");

        RecyclerView inputsRV = findViewById(R.id.inputs_RV);
        inputsRV.setAdapter(new InputsAdapter(clickedCrop.getInputs()));
        inputsRV.setLayoutManager(new LinearLayoutManager(this));
        inputsRV.setNestedScrollingEnabled(false);


        cropAddButton = findViewById(R.id.addCropButton);

        String json_landList = preferences.getString("myLandList", null);
        ArrayList<Land> myLandList = new Gson().fromJson(json_landList,
                new TypeToken<List<Land>>() {
                }.getType());

        String[] items = new String[myLandList.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = "Land " + (i + 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                CropChooseActivity4B.this,
                R.layout.dropdown_item,
                items
        );

        dropDownTextForLandChoice.setAdapter(adapter);


    }

    private void pickDate() {

        // Get the current date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

// Initialize a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Set the selected date to a TextView or other view
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate =new Date(year - 1900, monthOfYear, dayOfMonth);
                    sowingDate = sdf.format(currentDate);
                    Log.d("loginDate", sowingDate);
                    editor = preferences.edit();
                    editor.putString("loginDate", sowingDate);
                    editor.commit();
                    datepicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }, mYear, mMonth, mDay);

        //get current date and disable previous days
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());



        calendar.add(Calendar.DAY_OF_YEAR,2);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", 0);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();


        cropAddButton.setOnClickListener(v -> {

            if (dropDownTextForLandChoice.getText().toString().length() != 0) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", 1);

                //extract Land Number from dropdown
                String complete = dropDownTextForLandChoice.getText().toString();
                int landNumber = Integer.parseInt(complete.substring(5));
                Log.d("land Number detectd :",""+landNumber);

                //Add Crop to List Crop with name cropClicked and image id from previous activity,and land Number from drop down text
                addCropToList(clickedCrop, landNumber);
                Log.d("cropImageID", String.valueOf(cropImageID));

                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            } else
                Toast.makeText(getApplicationContext(), "Please Choose Land Number", Toast.LENGTH_SHORT).show();
        });

    }

    private void addCropToList(Crop crop, int LandNumber) {
        if (!(MySingleton.getInstance().multipleCropHolder.contains(crop))) {
            //if global crop list do not have this crop already

            //Add current Land number to its land list
            ArrayList<Integer> landNumberList =  crop.getLandNumberList();
            if(!landNumberList.contains(LandNumber))
                landNumberList.add(LandNumber);

            //set sowing date
            if(sowingDate!=null)
            crop.setSowingDate(sowingDate);
            else {
                Toast.makeText(getApplicationContext(),"Added Today's date as Sowing Date",Toast.LENGTH_SHORT).show();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);
                crop.setSowingDate(formattedDate);
            }


            //add the crop to global list
            myCropList.add(crop);

            //Log it
            Log.d("if", "Added " + crop.getCropName() + " to yourCropList");

            //Notify User
            Toast.makeText(getApplicationContext(),
                    "Added " + crop.getCropName() + " to your Land " + LandNumber,
                    Toast.LENGTH_SHORT).show();

        } else {

            //get index of the crop in crop list
            int index = -1;


            for (int i = 0; i < MySingleton.getInstance().multipleCropHolder.size(); i++) {
                if (MySingleton.getInstance().multipleCropHolder.get(i).equals(crop)) {
                    index = i;
                    break;
                }
            }

            //TODO check if the land number is already added to the crop

            //Add the land Number to already present crop in List
            MySingleton.getInstance().multipleCropHolder.get(index).getLandNumberList().add(LandNumber);

            //Log it
            Log.d("else", "Added Land Number " + LandNumber + " to " + crop.getCropName());

            //Notify User
            Toast.makeText(getApplicationContext(),
                    "Added " + crop.getCropName() + " to your Land " + LandNumber,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
