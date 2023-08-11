package com.example.khetai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khetai.adapter.TodaysActivityAdapter;
import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Range;
import com.example.khetai.model.Task;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;


public class FragmentToday extends Fragment {

    private Context context;
    View view;
    private TodaysActivityAdapter mAdapter;
    RecyclerView mRecyclerView;
    String clickedCropName;
    SharedPreferences preferences;
    ArrayList<Task> completeTaskList;
    ArrayList<Crop> myCropList;
    Gson gson =new Gson();
    LinkedHashMap  <String,Trio<String,Integer,Integer>> activitiesDesc= new LinkedHashMap<>();
    Crop detectedCrop;

    public FragmentToday(String cropName) {
        this.clickedCropName =cropName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_today, container, false);

        mRecyclerView= view.findViewById(R.id.todays_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<ArrayList<String>> dataHolder = new ArrayList<>();
        for (ArrayList<String> x : dataHolder )
            x = new ArrayList<>();


        preferences = getContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("completeTaskList", null);


         completeTaskList = MySingleton.getInstance().completeTaskList;
//        completeTaskList = gson.fromJson(json,
//                new TypeToken<List<Task>>() {
//                }.getType());

        String json_cropList = preferences.getString("myCropList", null);
        myCropList = gson.fromJson(json_cropList,
                new TypeToken<List<Crop>>() {
                }.getType());

        /* Search for Crop in cropList using cropName
                get sowing date from object
                get complete Task list
                pass gap 0 wala list
                search

                */
        detectCrop();
        long daysGap =0;
        for (int i = 0; i < completeTaskList.size(); i++) {
            //TODO add && range contains gap condition......DONE!!!
            Task task = completeTaskList.get(i);
            String cropName = task.getCropName();
            Range range = task.getRange();
            if (Objects.equals(cropName, clickedCropName)) {
                Log.d("true1", "crop match found");
                if (range.contains(Integer.parseInt(String.valueOf(daysGap)))) {
                    Log.d("true2", "range match found");
                    activitiesDesc = task.getActivityListByHeading();
                    break;
                }
            } else {
                Log.d("else block ", "asfs");
                activitiesDesc = new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
                    put("DefaultHeading1", Trio.createTrio("Activitydesc", 0, 0));
                    put("DeaultHEading2", Trio.createTrio("Activitydesc", 0, 0));
                    put("DefaultHeading3", Trio.createTrio("Activitydesc", 0, 0));
                }};
            }
        }

//        List<String> data = Arrays.asList("Item 1", "Item 2", "Item 3");
//
//        LinkedHashMap<String,Trio<String,Integer,Integer>> xxx= new LinkedHashMap<>();
//        xxx.put("ads", Trio.createTrio( "ads",0,0));
//        xxx.put("asadds", Trio.createTrio( "ads",0,0));
//        xxx.put("aaddsds", Trio.createTrio( "ads",0,0));
//        xxx.put("adfss", Trio.createTrio( "ads",0,0));

        //mAdapter = new TodaysActivityAdapter(xxx);
        context = getContext();
        mRecyclerView.setAdapter(new TodaysActivityAdapter(0,activitiesDesc));

        return view;
    }
    private void detectCrop(){

        for(int i=0;i< myCropList.size();i++){
            if (myCropList.get(i).equals(new Crop(clickedCropName,0))) {
                detectedCrop = myCropList.get(i);
                break;
            }
        }


    }
}