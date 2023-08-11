package com.example.khetai;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberExtractor {

    private static NumberExtractor instance=null;
    public static NumberExtractor getInstance() {
        if (instance == null)
            instance = new NumberExtractor();
        return instance;
    }
    private NumberExtractor(
    ) {}

    public static ArrayList<Integer> extractNumbers(String str) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String numString = matcher.group();
            int num = Integer.parseInt(numString);
            numbers.add(num);
        }

        return numbers;
    }
}