package com.example.khetai.model;



import com.example.khetai.adapter.Pair;
import com.example.khetai.adapter.Trio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Task {

    private String cropName;
    Range range;
    private Trio<String,Range,LinkedHashMap< String, Trio<String,Integer,Integer>>> activityListByRange;
    LinkedHashMap<String,Trio<String,Integer,Integer>> activityListByHeading;

    public Task( Trio<String,Range,LinkedHashMap<String,Trio<String,Integer,Integer>>> activityListByRange) {
        this.activityListByRange = activityListByRange;
        this.activityListByHeading = activityListByRange.getElement2();
        this.range = activityListByRange.getValue();
        this.cropName= activityListByRange.getKey();
    }

    public String getCropName() {
        return cropName;
    }

    public LinkedHashMap<String, Trio<String,Integer,Integer>> getActivityListByHeading() {
        return activityListByHeading;
    }

    public Task(String cropName) {
        this.cropName = cropName;
    }

    public Range getRange() {
        return range;
    }

    public Trio<  String  ,   Range   ,  LinkedHashMap<String,Trio<String,Integer,Integer>>> getActivitiesByRange() {
        return activityListByRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return cropName.equals(task.cropName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cropName);
    }
}
