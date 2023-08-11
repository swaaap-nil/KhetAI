package com.example.khetai.model;

import java.util.ArrayList;

public class Farmer {

    private String name;
    private int age;
    private int helpersCount;
    private float totalArea;
    private int landCount;
    ArrayList<Float> individualLandSize;

    public ArrayList<Float> getIndividualLandSize() {
        return individualLandSize;
    }

    public void setIndividualLandSize(ArrayList<Float> individualLandSize) {
        this.individualLandSize = individualLandSize;
    }

    public Farmer()
    {

    }

    public Farmer(String name, int age, int helpersCount, float totalArea, int landCount,ArrayList<Float> individualLandSize) {
        this.name = name;
        this.age = age;
        this.helpersCount = helpersCount;
        this.totalArea = totalArea;
        this.landCount = landCount;
        this.individualLandSize=individualLandSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHelpersCount() {
        return helpersCount;
    }

    public void setHelpersCount(int helpersCount) {
        this.helpersCount = helpersCount;
    }

    public float getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(float totalArea) {
        this.totalArea = totalArea;
    }

    public int getLandCount() {
        return landCount;
    }

    public void setLandCount(int landCount) {
        this.landCount = landCount;
    }
}
