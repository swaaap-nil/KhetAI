package com.example.khetai.model;


import com.example.khetai.adapter.Trio;

import java.util.ArrayList;
import java.util.Objects;

public class Crop {

    private String cropName;
    private int cropImage;
    private ArrayList<Integer> landNumber = new ArrayList<>();
    Integer yield;
    Integer investment;
    Integer profit;
    ArrayList<Trio<String, Integer, Integer>> inputs = new ArrayList<>();
    Integer seedRequirement;
    String timePeriod;
    String variety;
    String sowingDate ;

    public Crop(String cropName, int cropImage) {
        this.cropName = cropName;
        this.cropImage = cropImage;
    }

    public String getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(String sowingDate) {
        this.sowingDate = sowingDate;
    }

    public Integer getYield() {
        return yield;
    }

    public Integer getInvestment() {
        return investment;
    }

    public Integer getProfit() {
        return profit;
    }

    public ArrayList<Trio<String, Integer, Integer>> getInputs() {
        return inputs;
    }

    public Integer getSeedRequirement() {
        return seedRequirement;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public String getVariety() {
        return variety;
    }

    public Crop(String cropName, int cropImage, Integer yield, Integer investment, Integer profit, Integer seedRequirement, String timePeriod, String variety, ArrayList<Trio<String, Integer, Integer>> inputs) {
        this.cropName = cropName;
        this.cropImage = cropImage;
        this.yield = yield;
        this.investment = investment;
        this.profit = profit;
        this.inputs = inputs;
        this.seedRequirement = seedRequirement;
        this.timePeriod = timePeriod;
        this.variety = variety;
    }


    public ArrayList<Integer> getLandNumberList() {
        return landNumber;
    }

    public void setLandNumber(ArrayList<Integer> landNumber) {
        this.landNumber = landNumber;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getCropImage() {
        return cropImage;
    }

    public void setCropImage(int cropImage) {
        this.cropImage = cropImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crop crop = (Crop) o;
        return cropName.equals(crop.cropName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cropName);
    }
}
