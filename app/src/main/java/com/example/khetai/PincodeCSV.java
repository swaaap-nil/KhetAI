package com.example.khetai;

import android.content.Context;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PincodeCSV {
    Context context;
    String fileName;
    //List<String[]> rows = new ArrayList<>();
    HashMap<String,List<String>> entries = new HashMap<>();

    public PincodeCSV(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

//
public void readCSV() throws IOException {


    try {
        InputStreamReader is = new InputStreamReader(context.getAssets().open(fileName));
        CSVReader reader = new CSVReader(is);

        String[] parts;

        while ((parts = reader.readNext()) != null) {

            String key = parts[2];
            String value = parts[0];

            if (!entries.containsKey(key))
                entries.put(key, new ArrayList<>());
            else {
                if (!entries.get(key).contains(value))
                    (entries.get(key)).add(value);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (CsvValidationException e) {
        e.printStackTrace();
    }
}

    List<String> searchPincode(String pinCode)
    {
        return entries.get(pinCode);
    }
}

