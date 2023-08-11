package com.example.khetai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khetai.adapter.GroupAdapter;
import com.example.khetai.adapter.YourCropsAdapter;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Product;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment implements RecyclerViewInterface {

    private Context mContext;
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    String cropName;
    private List<Product> yourCropslist;
    ArrayList<Crop> userList, completeList;
    View view;
    SharedPreferences preferences;
    ArrayList<Crop> myCropList;
    Gson gson = new Gson();


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        preferences = getContext().getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("myCropList", null);

        myCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());


        recyclerView = view.findViewById(R.id.calendar_RV1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new YourCropsAdapter(myCropList, this::onItemClick));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), CropDetailsActivity.class);
        intent.putExtra("Name", myCropList.get(position).getCropName());
        getContext().startActivity(intent);

    }


}