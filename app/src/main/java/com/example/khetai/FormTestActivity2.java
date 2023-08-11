package com.example.khetai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormTestActivity2 extends AppCompatActivity {

    private AutoCompleteTextView dropDownText;
    private ImageView helperMinus;
    private ImageView helperPlus;
    private ImageView landPlus;
    private ImageView landMinus;
    private TextView LandCountText, helperCountText;
    private AppCompatButton nextButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RadioButton organic;
    RadioButton inorganic;
    int helperCount = 1;
    int landCount = 1;
    private TextInputEditText EditTextPinCode, EditTextVillage;
    List<String> villageList;
    String[] items;
    String state;
    ArrayList<String> arr = new ArrayList<>();
    TextView LocationDetails;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_final_2);


        HashMap<String, ArrayList<String>> entries = new HashMap<>();
        organic = findViewById(R.id.radio_button_organic);
        inorganic = findViewById(R.id.radio_button_inorganic);
        nextButton = findViewById(R.id.next_btn);
        dropDownText = findViewById(R.id.dropdown_text);
        helperMinus = findViewById(R.id.floatingActionButton1);
        helperPlus = findViewById(R.id.floatingActionButton2);
        landMinus = findViewById(R.id.floatingActionButton3);
        landPlus = findViewById(R.id.floatingActionButton4);
        LocationDetails = findViewById(R.id.textView_locationDetails);
        LandCountText = findViewById(R.id.textView_landCount);
        helperCountText = findViewById(R.id.textView_helperCount);
        EditTextPinCode = findViewById(R.id.editTextPincode1);
        preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
        editor = preferences.edit();


        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("pincodes.csv"));
            CSVReader reader = new CSVReader(is);
            String[] parts;

            while ((parts = reader.readNext()) != null) {

                String key = parts[2];
                String village = parts[0];
                state = parts[4];


                if (!entries.containsKey(key)) {
                    ArrayList<String> xxx = new ArrayList<>();
                    xxx.add(state);

                    entries.put(key, xxx);


                } else {
                    if (!entries.get(key).contains(village))
                        (entries.get(key)).add(village);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        items = new String[]{"1", "2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                FormTestActivity2.this,
                R.layout.dropdown_item,
                items
        );

        dropDownText.setAdapter(adapter);

        EditTextPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (EditTextPinCode.getText().toString().length() == 6) {
                    arr = (ArrayList<String>) entries.get(EditTextPinCode.getText().toString());
                    if (arr != null) {
                        Log.d("Arraylist working", entries.get("812001").size() + "");
                        items = new String[arr.size()];
                        for (int i = 0; i < items.length; i++) {
                            items[i] = (String) arr.get(i);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                FormTestActivity2.this,
                                R.layout.dropdown_item,
                                items
                        );
                        dropDownText.setAdapter(adapter);

                    }

                } else {
                    items = new String[]{""};
                    dropDownText.setText("");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FormTestActivity2.this,
                            R.layout.dropdown_item,
                            items
                    );

                    dropDownText.setAdapter(adapter);
                }
            }
        });


        nextButton.setOnClickListener(v -> {


//                landCount = Integer.parseInt(LandCountText.getText().toString()) ;
//                helperCount = Integer.parseInt(helperCountText.getText().toString()) ;


            editor.putInt("helperCount", helperCount);
            editor.putInt("landCount", landCount);
            if (dropDownText.getText().toString().length() > 2)
                editor.putString("Address", dropDownText.getText().toString().concat(","+items[0]));
            else editor.putString("Address", "Ghaziabad,Uttar Pradesh");

            editor.commit();

            goToLocationSetActivity();
        });

        LocationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putInt("helperCount", helperCount);
                if(preferences.getInt("landCount",0)==0)
                    editor.putInt("landCount", landCount);
                else editor.putInt("landCount",preferences.getInt("landCount",0)+landCount);

                if (dropDownText.getText().toString().length() > 2)
                    editor.putString("Address", dropDownText.getText().toString().concat(","+items[0]));
                else editor.putString("Address", "Ghaziabad,Uttar Pradesh");

                editor.commit();

                goToLocationSetActivity();

            }
        });


    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "There is no Back Action", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        organic.setOnClickListener(v -> inorganic.setChecked(false));

        inorganic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organic.setChecked(false);
            }
        });

        landMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (landCount - 1 > 0) {
                    LandCountText.setText("" + --landCount);
                }

            }
        });

        landPlus.setOnClickListener(v -> LandCountText.setText("" + ++landCount));

        helperMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helperCount - 1 > 0) {
                    helperCountText.setText("" + --helperCount);
                }

            }
        });

        helperPlus.setOnClickListener(v -> helperCountText.setText("" + ++helperCount));


    }

    private void goToLocationSetActivity() {
        Intent intent = new Intent(FormTestActivity2.this, LocationSetActivity3.class);
        intent.putExtra("landCount", landCount);
        startActivity(intent);
    }

}
