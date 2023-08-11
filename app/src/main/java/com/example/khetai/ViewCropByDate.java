package com.example.khetai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.adapter.TodaysActivityAdapter;
import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Land;
import com.example.khetai.model.Range;
import com.example.khetai.model.Task;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;


public class ViewCropByDate extends AppCompatActivity {

    String clickedCropName, month;

    private TodaysActivityAdapter mAdapter;
    RecyclerView mRecyclerView;
    String clickedDate;
    ArrayList<Crop> myCropList;
    SharedPreferences preferences;
    Gson gson = new Gson();
    Crop identifiedCrop;
    ArrayList<Task> completeTaskList =MySingleton.getInstance().completeTaskList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbydate);
        Bundle bundle = getIntent().getExtras();

        clickedCropName = bundle.getString("cropName");
        clickedDate = bundle.getString("clickedDate");
        month = bundle.getString("Month");
        preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json_cropList = preferences.getString("myCropList", null);
        myCropList = gson.fromJson(json_cropList,
                new TypeToken<List<Crop>>() {
                }.getType());

        for(int i=0;i<myCropList.size();i++) {
            if(myCropList.get(i).equals(new Crop(clickedCropName,0))){
                identifiedCrop= myCropList.get(i);
            }
        }

        mRecyclerView= findViewById(R.id.todays_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));






        LinkedHashMap  <String,Trio<String,Integer,Integer>> activitiesDesc= new LinkedHashMap<>();

        //TODO You can add activities here
        //TODO In below code,activity for beetroot with gaps 1-5 and 6-10 are added.
        //TODO Note : the range is inclusive in nature ie includes upper and lower bounds as well


//        completeTaskList.add(new Task(Trio.createTrio("Beetroot",new Range(0,2), new LinkedHashMap<String,Trio<String,Integer,Integer>>(){{
//            put("GO THROUGH THE PLAYLIST",Trio.createTrio("\tgo through the playlist and callender for tomato farming \tconsult our sanchalak for any potential hurdels and difficulties \n",R.drawable.image1,R.drawable.image1));
//            put("ARRANGE INPUTS AND MACHINIRIES",Trio.createTrio("\tscan the input list for this crop \n\t find the best and most economical soource of those inputs \t stock the inputs required in the first 10 days, you can also check out khetai bazar and shanchalaks for inputs",0,0));
//            put("GET READY FOR LAND PREPRATION",Trio.createTrio("\tmark your area in your land for the crop\n\tget ready wiht equipments and techniques",R.drawable.image1,R.drawable.image1));
//        }})));
//
//        completeTaskList.add(new Task (Trio.createTrio("Beetroot", new Range(3,9),new LinkedHashMap<String,Trio<String,Integer,Integer>>(){{
//
//                    put("Clear the land",Trio.createTrio( "Remove any debris, rocks, or other obstacles from the land that could interfere with planting or the growth of crops. This is usually done using hand tools like machetes or hoes.",0,0));
//                    put("Remove weeds", Trio.createTrio("Remove any weeds or unwanted plants from the land using hand tools like hoes or cultivators. This helps to create a clear and even surface for planting.",0,0));
//                    put("Break up the soil", Trio.createTrio("Use a hoe or a shovel to break up the soil and create a loose surface for planting. This can be done in small sections or across the entire area.",0,0));
//                    put("Level the soil",Trio.createTrio( "Use a rake or a hoe to level the soil and create an even surface for planting. This helps to ensure that water and nutrients are distributed evenly across the land.",0,0));
//                    put("Add amendments", Trio.createTrio("add organic matter 3ton/acre farm yard manure to improve soil fertility and structure. This can be done by spreading the amendments evenly across the surface of the land and working them into the soil using a hoe or a rake.",0,0));
//                    put("Create planting beds", Trio.createTrio("If planting rows of crops, create planting beds by using a hoe to create furrows or shallow trenches where seeds can be planted. This helps to ensure that seeds are planted at the correct depth and that plants have enough space to grow.",0,0));
//        }})));
//
//        completeTaskList.add(new Task (Trio.createTrio("Beetroot", new Range(9,12),new LinkedHashMap<String,Trio<String,Integer,Integer>>(){{
//            put("Prepare the seedlings",Trio.createTrio( "Purchase or grow healthy seedlings in a greenhouse or seedbed. Ensure that the seedlings are strong and healthy, with well-developed roots, stems, and leaves. ",0,0));
//            put("Select the planting location",Trio.createTrio( "Choose a suitable planting location based on the crop requirements, soil quality, and availability of sunlight and water. Ensure that the soil is well-drained and fertile, with a pH level that is suitable for the crop.",0,0));
//            put("Prepare the planting area",Trio.createTrio( "Seeds of beetroot can be sown about 1.5 cm deep leaving a distance of about 7cm in between. The space between the rows must be 30 to 40 cm. Watering should be constant but not over the top, and gentle hoeing will keep the weeds away. In dry conditions thorough watering is necessary. The seedlings will appear in about 15 days, depending on the weather. When the seedlings are about 3 to 5 cm high, thin them to a spacing of 7 to 10 cm to out so that there is only one seedling in one place.",0,0));
//            put("Dig planting holes",Trio.createTrio( "Use a dibble or planting tool to dig holes in the prepared soil. The holes should be deep enough to accommodate the seedlings without damaging the roots.",0,0));
//            put("Add fertilizer", Trio.createTrio("Add a small amount of fertilizer to each planting hole to provide additional nutrients to the seedlings.",0,0));
//            put("Plant the seedlings",Trio.createTrio( "Carefully remove the seedlings from their containers, and place them in the prepared planting holes. Be sure to keep the roots intact and avoid damaging them. Cover the roots with soil, and gently press the soil around the base of the seedlings to ensure good soil-to-root contact. ",0,0));
//            put("Water the seedlings",Trio.createTrio( "Water the seedlings immediately after planting to help settle the soil around the roots and promote initial growth. Use a watering can or hose with a gentle spray nozzle to avoid damaging the seedlings.",0,0));
//            put("Add support",Trio.createTrio("If necessary, add support stakes or trellises to the seedlings to help them grow upright.",0,0));
//            put("Monitor the seedlings",Trio.createTrio("Monitor the seedlings regularly for signs of stress or disease, and adjust watering and fertilizer as needed. ",0,0));
//        }})));
//
//        completeTaskList.add(new Task (Trio.createTrio("Beetroot", new Range(13,19),new LinkedHashMap<String,Trio<String,Integer,Integer>>(){{
//            put("Water daily until the leaves begin to sprout",Trio.createTrio("At the beginning, your seeds need plenty of water to start the germination process. The roots will take moisture from the soil once they're established.\n" +
//                    "•\tThat being said, avoid over-watering. This causes beetroot to produce more leaves and less root, risking them \"bolting\" (flowering and not producing a vegetable). What's more, under-watering creates woody roots.\n" +
//                    "•\tOnce you have sprouts, only water them every 10-14 days in dry spells. Other than when the weather is unnaturally dry, normal rainfall should be fine.\n",0,0));
//        }})));
//
//        completeTaskList.add(new Task (Trio.createTrio("Beetroot", new Range(20,32),new LinkedHashMap<String,Trio<String,Integer,Integer>>() {
//            {
//                put("Determine the fertilizer needs", Trio.createTrio("Test the soil to determine the nutrient content and pH level, and identify any nutrient deficiencies or imbalances. This will help you determine the appropriate type and amount of fertilizer to use. ", 0, 0));
//                put("Calculate the application rate", Trio.createTrio("Calculate the amount of fertilizer needed based on the crop requirements and the area to be treated. This can be done using fertilizer calculators or by consulting with agricultural experts.", 0, 0));
//                put("Apply the fertilizer", Trio.createTrio("Apply the  npk fertilizer to the beetroot using a fertilizer spreader or by hand.At vegetative stage,duration in days 30,fertilizer grade npk 17kg/acre(19 19 19),%requirement of nitrogen 30%,phosphorous 12.5%,potassium 10% ,urea 74kg, and neemcake 1.5ton. Spread the fertilizer evenly over the soil surface and avoid applying it directly to the plants. ", 0, 0));
//                put("Incorporate the fertilizer", Trio.createTrio("Incorporate the fertilizer into the soil using a cultivator or other equipment. This will help distribute the nutrients more evenly and ensure that they are available to the plants.", 0, 0));
//                put("Water the plants", Trio.createTrio("Water the plants after applying the fertilizer to help dissolve the nutrients and move them into the soil.", 0, 0));
//                put("Monitor the plants", Trio.createTrio("Monitor the plants regularly to ensure that they are responding well to the fertilizer and adjust the application rate as needed.", 0, 0));
//                put("Store the fertilizer", Trio.createTrio("Store any unused fertilizer in a cool, dry place away from children and pets, following all safety instructions on the packaging.", 0, 0));
//            }})));
//                //TODO   BITTER GOURD
//
//                completeTaskList.add(new Task(Trio.createTrio("Bitter Gourd", new Range(0, 2), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("GO THROUGH THE PLAYLIST", Trio.createTrio("\tgo through the playlist and callender for tomato farming \tconsult our sanchalak for any potential hurdels and difficulties \n", 0, 0));
//                    put("ARRANGE INPUTS AND MACHINIRIES", Trio.createTrio("\tscan the input list for this crop \n\t find the best and most economical soource of those inputs \t stock the inputs required in the first 10 days, you can also check out khetai bazar and shanchalaks for inputs", 0, 0));
//                    put("GET READY FOR LAND PREPRATION", Trio.createTrio("\tmark your area in your land for the crop\n\tget ready wiht equipments and techniques", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Bitter Gourd", new Range(3, 8), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Clear the land", Trio.createTrio("Remove any debris, rocks, or other obstacles from the land that could interfere with planting or the growth of crops. This is usually done using hand tools like machetes or hoes.", 0, 0));
//                    put("Remove weeds", Trio.createTrio("Remove any weeds or unwanted plants from the land using hand tools like hoes or cultivators. This helps to create a clear and even surface for planting.", 0, 0));
//                    put("Break up the soil", Trio.createTrio("Use a hoe or a shovel to break up the soil and create a loose surface for planting. This can be done in small sections or across the entire area.", 0, 0));
//                    put("Level the soil", Trio.createTrio("Use a rake or a hoe to level the soil and create an even surface for planting. This helps to ensure that water and nutrients are distributed evenly across the land", 0, 0));
//                    put("Add amendments", Trio.createTrio("add organic matter 1.5ton vermicompost,10ton poultry  manure to improve soil fertility and structure. This can be done by spreading the amendments evenly across the surface of the land and working them into the soil using a hoe or a rake.", 0, 0));
//                    put("Create planting beds", Trio.createTrio("If planting rows of crops, create planting beds by using a hoe to create furrows or shallow trenches where seeds can be planted. This helps to ensure that seeds are planted at the correct depth and that plants have enough space to grow.", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Bitter Gourd", new Range(9, 12), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Prepare the seedlings", Trio.createTrio("Purchase or grow healthy seedlings in a greenhouse or seedbed. Ensure that the seedlings are strong and healthy, with well-developed roots, stems, and leaves. ", 0, 0));
//                    put("Select the planting location", Trio.createTrio("Choose a suitable planting location based on the crop requirements, soil quality, and availability of sunlight and water. Ensure that the soil is well-drained and fertile, with a pH level that is suitable for the crop.", 0, 0));
//                    put("Prepare the planting area", Trio.createTrio("Soaking the bitter gourd seeds before sowing helps them to germinate faster.Make about 1/2 inch deep holes in the land to sow bitter gourdd seeds.Then,space them 10 cm apart to ensure the creepers won't eat into each other.", 0, 0));
//                    put("Dig planting holes", Trio.createTrio("Use a dibble or planting tool to dig holes in the prepared soil. The holes should be deep enough to accommodate the seedlings without damaging the roots.", 0, 0));
//                    put("Add fertilizer", Trio.createTrio("Add a small amount of fertilizer to each planting hole to provide additional nutrients to the seedlings.", 0, 0));
//                    put("Plant the seedlings", Trio.createTrio("Carefully remove the seedlings from their containers, and place them in the prepared planting holes. Be sure to keep the roots intact and avoid damaging them. Cover the roots with soil, and gently press the soil around the base of the seedlings to ensure good soil-to-root contact. ", 0, 0));
//                    put("Water the seedlings", Trio.createTrio("Water the seedlings immediately after planting to help settle the soil around the roots and promote initial growth. Use a watering can or hose with a gentle spray nozzle to avoid damaging the seedlings.", 0, 0));
//                    put("Add support", Trio.createTrio("If necessary, add support stakes or trellises to the seedlings to help them grow upright.", 0, 0));
//                    put("Monitor the seedlings", Trio.createTrio("Monitor the seedlings regularly for signs of stress or disease, and adjust watering and fertilizer as needed. ", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Bitter Gourd", new Range(13, 19), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("IRRIGATION METHOD FOR BITTER GOURD ", Trio.createTrio("Irrigate the basins before dibbling the seeds and thereafter once a week. Install a drip system with main and sub-main pipes and place the inline lateral tubes at an interval of 1.5m. Place the drippers in lateral tubes at an interval of 60 cm and 50 cm spacing with 4LPH and 3.5 LPH capacities respectively.", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Bitter Gourd", new Range(20, 32), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Determine the fertilizer needs", Trio.createTrio("Test the soil to determine the nutrient content and pH level, and identify any nutrient deficiencies or imbalances. This will help you determine the appropriate type and amount of fertilizer to use.", 0, 0));
//                    put("Calculate the application rate", Trio.createTrio("Calculate the amount of fertilizer needed based on the crop requirements and the area to be treated. This can be done using fertilizer calculators or by consulting with agricultural experts.", 0, 0));
//                    put("Apply the fertilizer", Trio.createTrio("Apply the npk fertilizer to the beetroot using a fertilizer spreader or by hand.At vegetative stage npk ratio 19-19-19 to be use of 21kg,urea content 74 kg should be apply and 1 ton neem cake.Spread the fertilizer evenly over the soil surface and avoid applying it directly to the plants.", 0, 0));
//                    put("Incorporate the fertilizer: ", Trio.createTrio("Incorporate the fertilizer into the soil using a cultivator or other equipment. This will help distribute the nutrients more evenly and ensure that they are available to the plants.", 0, 0));
//                    put("Water the plants", Trio.createTrio("Water the plants after applying the fertilizer to help dissolve the nutrients and move them into the soil.", 0, 0));
//                    put("Monitor the plants", Trio.createTrio("Monitor the plants regularly to ensure that they are responding well to the fertilizer and adjust the application rate as needed.", 0, 0));
//                    put("Store the fertilizer", Trio.createTrio("Store any unused fertilizer in a cool, dry place away from children and pets, following all safety instructions on the packaging", 0, 0));
//                }})));
//
//
//                //TODO  Broad Beans
//
//                completeTaskList.add(new Task(Trio.createTrio("Broad Beans", new Range(0, 2), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("GO THROUGH THE PLAYLIST", Trio.createTrio("\tgo through the playlist and callender for tomato farming \tconsult our sanchalak for any potential hurdels and difficulties \n", 0, 0));
//                    put("ARRANGE INPUTS AND MACHINIRIES", Trio.createTrio("\tscan the input list for this crop \n\t find the best and most economical soource of those inputs \t stock the inputs required in the first 10 days, you can also check out khetai bazar and shanchalaks for inputs", 0, 0));
//                    put("GET READY FOR LAND PREPRATION", Trio.createTrio("\tmark your area in your land for the crop\n\tget ready wiht equipments and techniques", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Broad Beans", new Range(3, 8), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Clear the land", Trio.createTrio("Remove any debris, rocks, or other obstacles from the land that could interfere with planting or the growth of crops. This is usually done using hand tools like machetes or hoes.", 0, 0));
//                    put("Remove weeds", Trio.createTrio("Remove any weeds or unwanted plants from the land using hand tools like hoes or cultivators. This helps to create a clear and even surface for planting.", 0, 0));
//                    put("Break up the soil", Trio.createTrio("Use a hoe or a shovel to break up the soil and create a loose surface for planting. This can be done in small sections or across the entire area.", 0, 0));
//                    put("Level the soil", Trio.createTrio("Use a rake or a hoe to level the soil and create an even surface for planting. This helps to ensure that water and nutrients are distributed evenly across the land.", 0, 0));
//                    put("Add amendments", Trio.createTrio("add organic matter 6 ton vermicompost,15ton cow dung to improve soil fertility and structure. This can be done by spreading the amendments evenly across the surface of the land and working them into the soil using a hoe or a rake.", 0, 0));
//                    put("Create planting beds", Trio.createTrio("If planting rows of crops, create planting beds by using a hoe to create furrows or shallow trenches where seeds can be planted. This helps to ensure that seeds are planted at the correct depth and that plants have enough space to grow.", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Broad Beans", new Range(9, 12), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Prepare the seedlings", Trio.createTrio("Purchase or grow healthy seedlings in a greenhouse or seedbed. Ensure that the seedlings are strong and healthy, with well-developed roots, stems, and leaves.", 0, 0));
//                    put("Select the planting location", Trio.createTrio("Choose a suitable planting location based on the crop requirements, soil quality, and availability of sunlight and water. Ensure that the soil is well-drained and fertile, with a pH level that is suitable for the crop.", 0, 0));
//                    put("Prepare the planting area", Trio.createTrio("Break up the earth to a depth of around 1 foot (30 cm).\n" +
//                            "•\tDig grooves that are around 4 to 6 inches (10 to 15 cm) deep.\n" +
//                            "•\tPlace a seed every 4 inches (10 cm) along the groove, and press it down with your thumb. An alternate way of sowing is to place 2 seeds per seed hole and space the seed holes 8 inches (20 cm) apart.\n" +
//                            "•\tCover with about an inch (a couple centimeters) of soil, without pressing it down.", 0, 0));
//
//                    put("Dig planting holes", Trio.createTrio("Use a dibble or planting tool to dig holes in the prepared soil. The holes should be deep enough to accommodate the seedlings without damaging the roots.", 0, 0));
//                    put("Add fertilizer", Trio.createTrio("Add a small amount of fertilizer to each planting hole to provide additional nutrients to the seedlings.", 0, 0));
//                    put("Plant the seedlings", Trio.createTrio("Carefully remove the seedlings from their containers, and place them in the prepared planting holes. Be sure to keep the roots intact and avoid damaging them. Cover the roots with soil, and gently press the soil around the base of the seedlings to ensure good soil-to-root contact. ", 0, 0));
//                    put("Water the seedlings", Trio.createTrio("Water the seedlings immediately after planting to help settle the soil around the roots and promote initial growth. Use a watering can or hose with a gentle spray nozzle to avoid damaging the seedlings.", 0, 0));
//                    put("Add support", Trio.createTrio("If necessary, add support stakes or trellises to the seedlings to help them grow upright.", 0, 0));
//                    put("Monitor the seedlings", Trio.createTrio("Monitor the seedlings regularly for signs of stress or disease, and adjust watering and fertilizer as needed.", 0, 0));
//
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Broad Beans", new Range(13, 19), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Determine the fertilizer needs", Trio.createTrio("Test the soil to determine the nutrient content and pH level, and identify any nutrient deficiencies or imbalances. This will help you determine the appropriate type and amount of fertilizer to use.", 0, 0));
//                    put("Calculate the application rate", Trio.createTrio("Calculate the amount of fertilizer needed based on the crop requirements and the area to be treated. This can be done using fertilizer calculators or by consulting with agricultural experts. ", 0, 0));
//                    put("Apply the fertilizer", Trio.createTrio("Apply the npk fertilizer to the broad beans using a fertilizer spreader or by hand.At vegetative stage npk ratio 20-10-10 use of 30 kg,urea 40kg and 8ton neemcake. Spread the fertilizer evenly over the soil surface and avoid applying it directly to the plants. ", 0, 0));
//                    put("Incorporate the fertilizer", Trio.createTrio("Incorporate the fertilizer into the soil using a cultivator or other equipment. This will help distribute the nutrients more evenly and ensure that they are available to the plants.", 0, 0));
//                    put("Water the plants", Trio.createTrio("Water the plants after applying the fertilizer to help dissolve the nutrients and move them into the soil.", 0, 0));
//                    put("Monitor the plants", Trio.createTrio("Monitor the plants regularly to ensure that they are responding well to the fertilizer and adjust the application rate as needed.", 0, 0));
//                    put("Store the fertilizer", Trio.createTrio("Store any unused fertilizer in a cool, dry place away from children and pets, following all safety instructions on the packaging. ", 0, 0));
//
//                }})));
//
//                // TODO Tomato
//
//                completeTaskList.add(new Task(Trio.createTrio("Tomato", new Range(0, 2), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("GO THROUGH THE PLAYLIST", Trio.createTrio("\tgo through the playlist and callender for tomato farming \tconsult our sanchalak for any potential hurdels and difficulties \n", 0, 0));
//                    put("ARRANGE INPUTS AND MACHINIRIES", Trio.createTrio("\tscan the input list for this crop \n\t find the best and most economical soource of those inputs \t stock the inputs required in the first 10 days, you can also check out khetai bazar and shanchalaks for inputs", 0, 0));
//                    put("GET READY FOR LAND PREPRATION", Trio.createTrio("\tmark your area in your land for the crop\n\tget ready wiht equipments and techniques", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Tomato", new Range(3, 8), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Clear the land", Trio.createTrio("Remove any debris, rocks, or other obstacles from the land that could interfere with planting or the growth of crops. This is usually done using hand tools like machetes or hoes.", 0, 0));
//                    put("Remove weeds", Trio.createTrio("Remove any weeds or unwanted plants from the land using hand tools like hoes or cultivators. This helps to create a clear and even surface for planting.", 0, 0));
//                    put("Break up the soil", Trio.createTrio("Use a hoe or a shovel to break up the soil and create a loose surface for planting. This can be done in small sections or across the entire area.", 0, 0));
//                    put("Level the soil", Trio.createTrio("Use a rake or a hoe to level the soil and create an even surface for planting. This helps to ensure that water and nutrients are distributed evenly across the land.", 0, 0));
//                    put("Add amendments", Trio.createTrio("add organic matter 10 ton vermicompost,500kg poultry manure to improve soil fertility and structure. This can be done by spreading the amendments evenly across the surface of the land and working them into the soil using a hoe or a rake.", 0, 0));
//                    put("Create planting beds", Trio.createTrio("If planting rows of crops, create planting beds by using a hoe to create furrows or shallow trenches where seeds can be planted. This helps to ensure that seeds are planted at the correct depth and that plants have enough space to grow.", 0, 0));
//                }})));
//
//
//                completeTaskList.add(new Task(Trio.createTrio("Tomato", new Range(9, 12), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Prepare the seedlings", Trio.createTrio("Purchase or grow healthy seedlings in a greenhouse or seedbed. Ensure that the seedlings are strong and healthy, with well-developed roots, stems, and leaves.", 0, 0));
//                    put("Select the planting location", Trio.createTrio("Choose a suitable planting location based on the crop requirements, soil quality, and availability of sunlight and water. Ensure that the soil is well-drained and fertile, with a pH level that is suitable for the crop.", 0, 0));
//                    put("Prepare the planting area", Trio.createTrio("Planting to be done at a spacing of 90 x 60 x 60 cm in the paired row system, using ropes marked at 60 cm spacing", 0, 0));
//                    put("Dig planting holes", Trio.createTrio("Use a dibble or planting tool to dig holes in the prepared soil. The holes should be deep enough to accommodate the seedlings without damaging the roots.", 0, 0));
//                    put("Add fertilizer", Trio.createTrio("Add a small amount of fertilizer to each planting hole to provide additional nutrients to the seedlings.", 0, 0));
//                    put("Plant the seedlings", Trio.createTrio("Carefully remove the seedlings from their containers, and place them in the prepared planting holes. Be sure to keep the roots intact and avoid damaging them. Cover the roots with soil, and gently press the soil around the base of the seedlings to ensure good soil-to-root contact. ", 0, 0));
//                    put("Water the seedlings", Trio.createTrio("Water the seedlings immediately after planting to help settle the soil around the roots and promote initial growth. Use a watering can or hose with a gentle spray nozzle to avoid damaging the seedlings.", 0, 0));
//                    put("Add support", Trio.createTrio("If necessary, add support stakes or trellises to the seedlings to help them grow upright.", 0, 0));
//                    put("Monitor the seedlings", Trio.createTrio("Monitor the seedlings regularly for signs of stress or disease, and adjust watering and fertilizer as needed. ", 0, 0));
//                }})));
//
//
//                completeTaskList.add(new Task(Trio.createTrio("Tomato", new Range(13, 19), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("IRRIGATION METHOD FOR TOMATO", Trio.createTrio("Water every 7 to 10 days. Do this after the first week. Give them about 16 ounces (about 500 mL) of warm water per plant every day. Drip or soaker hose watering aimed at the roots is better than overhead watering, which can encourage diseases.\n" +
//                            "·\tTo prevent mold or fungal diseases, water plants in the morning.\n" +
//                            "·\tWater less frequently after 10 days. Ensure plants are receiving 1 to 3 inches (2.5 cm to 7.6 cm) of rain weekly. If not, give each plant about 2 gallons (about 7.5 L) per plant per week, beginning by about the end of the second week after transplanting.\n" +
//                            "Increase water as the plants get larger and when weather is hotter. Water deeply 2 to 3 times weekly, about ..75 to 1 gallon (2.84 to 3.79 L) (about 3 to 4 L) each time. Make sure that the soil is moist, but not drenched.\n", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("Tomato", new Range(20, 32), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Determine the fertilizer needs", Trio.createTrio("Test the soil to determine the nutrient content and pH level, and identify any nutrient deficiencies or imbalances. This will help you determine the appropriate type and amount of fertilizer to use.", 0, 0));
//                    put("Calculate the application rate", Trio.createTrio("Calculate the amount of fertilizer needed based on the crop requirements and the area to be treated. This can be done using fertilizer calculators or by consulting with agricultural experts.", 0, 0));
//                    put("Apply the fertilizer", Trio.createTrio("Apply the npk fertilizer to the tomato using a fertilizer spreader or by hand. At vegetative stage npk ratio 13-0-45 of 84 kg will use,urea 84kg and neemcake 1 tonn can be use.Spread the fertilizer evenly over the soil surface and avoid applying it directly to the plants. ", 0, 0));
//                    put("Incorporate the fertilizer", Trio.createTrio("Incorporate the fertilizer into the soil using a cultivator or other equipment. This will help distribute the nutrients more evenly and ensure that they are available to the plants.", 0, 0));
//                    put("Water the plants", Trio.createTrio("Water the plants after applying the fertilizer to help dissolve the nutrients and move them into the soil.", 0, 0));
//                    put("Monitor the plants", Trio.createTrio("Monitor the plants regularly to ensure that they are responding well to the fertilizer and adjust the application rate as needed.", 0, 0));
//                    put("Store the fertilizer", Trio.createTrio("Store any unused fertilizer in a cool, dry place away from children and pets, following all safety instructions on the packaging. ", 0, 0));
//                }})));
//
//                //TODO FRENCH BEANS
//
//                completeTaskList.add(new Task(Trio.createTrio("French Beans", new Range(0, 2), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("GO THROUGH THE PLAYLIST", Trio.createTrio("\tgo through the playlist and callender for tomato farming \tconsult our sanchalak for any potential hurdels and difficulties \n", 0, 0));
//                    put("ARRANGE INPUTS AND MACHINIRIES", Trio.createTrio("\tscan the input list for this crop \n\t find the best and most economical soource of those inputs \t stock the inputs required in the first 10 days, you can also check out khetai bazar and shanchalaks for inputs", 0, 0));
//                    put("GET READY FOR LAND PREPRATION", Trio.createTrio("\tmark your area in your land for the crop\n\tget ready wiht equipments and techniques", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("French Beans", new Range(3, 8), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//
//                    put("Clear the land", Trio.createTrio("Remove any debris, rocks, or other obstacles from the land that could interfere with planting or the growth of crops. This is usually done using hand tools like machetes or hoes.", 0, 0));
//                    put("Remove weeds", Trio.createTrio("Remove any weeds or unwanted plants from the land using hand tools like hoes or cultivators. This helps to create a clear and even surface for planting.", 0, 0));
//                    put("Break up the soil", Trio.createTrio("Use a hoe or a shovel to break up the soil and create a loose surface for planting. This can be done in small sections or across the entire area.", 0, 0));
//                    put("Level the soil", Trio.createTrio("Use a rake or a hoe to level the soil and create an even surface for planting. This helps to ensure that water and nutrients are distributed evenly across the land.", 0, 0));
//                    put("Add amendments", Trio.createTrio("add organic matter 8 ton farm yard manure,6ton vermicompost and rhizobium 1 kg to improve soil fertility and structure. This can be done by spreading the amendments evenly across the surface of the land and working them into the soil using a hoe or a rake.", 0, 0));
//                    put("Create planting beds", Trio.createTrio("If planting rows of crops, create planting beds by using a hoe to create furrows or shallow trenches where seeds can be planted. This helps to ensure that seeds are planted at the correct depth and that plants have enough space to grow.", 0, 0));
//
//
//                }})));
//
//
//                completeTaskList.add(new Task(Trio.createTrio("French Beans", new Range(9, 12), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Prepare the seedlings", Trio.createTrio("Purchase or grow healthy seedlings in a greenhouse or seedbed. Ensure that the seedlings are strong and healthy, with well-developed roots, stems, and leaves.", 0, 0));
//                    put("Select the planting location", Trio.createTrio("Choose a suitable planting location based on the crop requirements, soil quality, and availability of sunlight and water. Ensure that the soil is well-drained and fertile, with a pH level that is suitable for the crop.", 0, 0));
//                    put("Prepare the planting area", Trio.createTrio("Seeds should be sown at a row spacing of 30-40 cm keeping 15-20 cm distance from plant to plant when followed the flat bed sowing method. In case of raised bed sowing method, seeds should be sown on raised beds having 60-80 cm width and 20-30 cm height at a row spacing of 30-40 cm keeping 15-20 cm distance from plant to plant.The seeds may be sown by dibbling, drilling and broad casting method at a depth of 2 to 3 cm.", 0, 0));
//                    put("Dig planting holes", Trio.createTrio("Use a dibble or planting tool to dig holes in the prepared soil. The holes should be deep enough to accommodate the seedlings without damaging the roots.", 0, 0));
//                    put("Add fertilizer", Trio.createTrio("Add a small amount of fertilizer to each planting hole to provide additional nutrients to the seedlings. ", 0, 0));
//                    put("Plant the seedlings", Trio.createTrio("Carefully remove the seedlings from their containers, and place them in the prepared planting holes. Be sure to keep the roots intact and avoid damaging them. Cover the roots with soil, and gently press the soil around the base of the seedlings to ensure good soil-to-root contact. ", 0, 0));
//                    put("Water the seedlings", Trio.createTrio("Water the seedlings immediately after planting to help settle the soil around the roots and promote initial growth. Use a watering can or hose with a gentle spray nozzle to avoid damaging the seedlings.", 0, 0));
//                    put("Add support", Trio.createTrio("If necessary, add support stakes or trellises to the seedlings to help them grow upright.", 0, 0));
//                    put("Monitor the seedlings", Trio.createTrio("Monitor the seedlings regularly for signs of stress or disease, and adjust watering and fertilizer as needed.", 0, 0));
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("French Beans", new Range(13, 19), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("IRRIGATION METHOD FOR FRENCH BEANS", Trio.createTrio("In about 8-10 days, the seeds will have sprout so remove the mulch and continue watering in the morning especially in strong sunny days to ensure that the ground does not dry up.\n" +
//                            "\n" +
//                            "Be careful enough not to over water as this may lead to the crop rotting or dying from drowning. If the weather is rainy or cloudy, do not water the crop.\n", 0, 0));
//
//                }})));
//
//                completeTaskList.add(new Task(Trio.createTrio("French Beans", new Range(20, 32), new LinkedHashMap<String, Trio<String, Integer, Integer>>() {{
//                    put("Determine the fertilizer needs", Trio.createTrio("Test the soil to determine the nutrient content and pH level, and identify any nutrient deficiencies or imbalances. This will help you determine the appropriate type and amount of fertilizer to use.", 0, 0));
//                    put("Calculate the application rate", Trio.createTrio("Calculate the amount of fertilizer needed based on the crop requirements and the area to be treated. This can be done using fertilizer calculators or by consulting with agricultural experts.", 0, 0));
//                    put("Apply the fertilizer", Trio.createTrio("Apply the npk fertilizer to the french beans using a fertilizer spreader or by hand.At vegetative stage npk ratio 20-10-10 use of 40kg,urea content 74 kg and rhizobium 1kg/acre will use. Spread the fertilizer evenly over the soil surface and avoid applying it directly to the plants. ", 0, 0));
//                    put("Incorporate the fertilizer", Trio.createTrio("Incorporate the fertilizer into the soil using a cultivator or other equipment. This will help distribute the nutrients more evenly and ensure that they are available to the plants.", 0, 0));
//                    put("Water the plants ", Trio.createTrio("Water the plants after applying the fertilizer to help dissolve the nutrients and move them into the soil.", 0, 0));
//                    put("Monitor the plants", Trio.createTrio("Monitor the plants regularly to ensure that they are responding well to the fertilizer and adjust the application rate as needed.", 0, 0));
//                    put("Store the fertilizer", Trio.createTrio("Store any unused fertilizer in a cool, dry place away from children and pets, following all safety instructions on the packaging. ", 0, 0));
//
//                }})));

        saveCompleteTaskListToStorage();

                long daysGap = figureOutDistanceFrom(identifiedCrop.getSowingDate());

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
                Log.d("gap", daysGap + "");
                mRecyclerView.setAdapter(new TodaysActivityAdapter(1,activitiesDesc));
            }




    private void saveCompleteTaskListToStorage() {

//        SharedPreferences preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        Gson gson = new Gson();
//        ArrayList<Land> myLandList = MySingleton.getInstance().multipleLandHolder;
//        String json = gson.toJson(myLandList);
//        editor.putString("myLandList", json);

        Gson gson = new Gson();
        String json = gson.toJson(completeTaskList);
        preferences.edit().putString("completeTaskList", json).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private long figureOutDistanceFrom(String todaysDate){

        SharedPreferences preferences = getSharedPreferences("FormFilled",MODE_PRIVATE);
        //String loginDate =preferences.getString("loginDate",null);
        long days_difference=0;
        DateFormat obj = new SimpleDateFormat("yyyy-MM-dd");
        // In the try block, we will try to find the difference
        try {
            // Use parse method to get date object of both dates
            Date date1 = obj.parse(identifiedCrop.getSowingDate());
//            Log.d("date1",date1.toString());
            Date date2 = obj.parse(clickedDate);
//            Log.d("date2",date2.toString());
            long time_difference = date2.getTime()- date1.getTime();
            return time_difference / 24 / 60 / 60 / 1000 ;

        }
        // Catch parse exception
        catch (ParseException excep) {
            Log.d("excep","catch from figureOutRange");
            excep.printStackTrace();
        }
        return days_difference;


    }
}
