package com.example.khetai.adapter;

import com.example.khetai.model.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class Trio<K, V, X> {

    private  K element0;
    private  V element1;
    private  X element2;


    public static <K, V ,X > Trio<K, V, X> createTrio(K element0, V element1, X element2) {
        return new Trio<K, V, X>(element0, element1 , element2);
    }

    public Trio(K element0, V element1, X element2) {
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
    }

    public Trio(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

//    public  <E> Trio<String, Range, HashMap<String, ArrayList<String>>> createTrio(String beetroot, Range range, String activityHeading, ArrayList<E> es) {
//        this.element0 = (K) beetroot;
//        this.element1 = (V) range;
//        this.element2 = ;
//    }



    public K getKey() {
        return element0;
    }

    public V getValue() {
        return element1;
    }

    public X getElement2() {
        return element2;
    }
}
