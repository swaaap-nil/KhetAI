

package com.example.khetai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Range;
import com.example.khetai.model.Task;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View view;
    ArrayList<SlideModel> slideModels;
    ShapeableImageView dial_btn;
    AppCompatButton camera_btn;
    ConstraintLayout todaysActivityLayout;
    ImageView profile_btn;
    private static final int pic_id = 123;
    ImageSlider imageSlider;
    ArrayList<Task> completeTaskList ;
    LinkedHashMap activitiesDesc =  new LinkedHashMap<String, Trio<String, Integer, Integer>>();


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

        completeTaskList = MySingleton.getInstance().completeTaskList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        dial_btn =  view.findViewById(R.id.open_dialer_button);
        dial_btn.setOnClickListener(this);

        dial_btn =  view.findViewById(R.id.camera_button);
        dial_btn.setOnClickListener(this);

        profile_btn = (AppCompatImageButton) view.findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(this);

        todaysActivityLayout = view.findViewById(R.id.todayActvityLayout);
        todaysActivityLayout.setOnClickListener(this);

        TextView highlights = view.findViewById(R.id.todaysactivty_home);


        imageSlider=view.findViewById(R.id.imageSlider);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                Intent clickIntent = new Intent(getActivity(),NewsActivity.class);
                clickIntent.putExtra("position",position);
                startActivity(clickIntent);
            }
        });

       /*progressBar1=(ProgressBar)view.findViewById(R.id.progressBar1);
        progressBar1.setMax(100);
        progressBar1.setProgress(28);

        progressBar2=(ProgressBar)view.findViewById(R.id.progressBar2);
        progressBar1.setMax(100);
        progressBar2.setProgress(75); */


        pushImagesInImageSlider();
        displayImagesInImageSlide();

        return view;
    }

    public void onClick(View v) {

       switch(v.getId())
        {
            case R.id.open_dialer_button: openDialer(); break;
            case R.id.camera_button: openCamera();break;
            case R.id.profile_btn: openProfile(); break;
            case R.id.todayActvityLayout: openTodaysActvities(); break;
        }
    }

    private void openMap() {
        Intent intent = new Intent(getActivity(), MapsMarkerActivity3A.class);
        startActivity(intent);
    }

    private void openTodaysActvities()
    {
        Intent todaysActivitiesIntent=new Intent(getActivity(),TodaysActivity.class);
        startActivity(todaysActivitiesIntent);
    }

    private void openProfile() {

        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(profileIntent);
    }

    private void openCamera() {

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, pic_id);
    }

    public void openDialer() {

        Uri u = Uri.parse("tel:+918969971626");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try {
            startActivity(i);
        } catch (SecurityException s) {

            Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
        }

    }


    private void displayImagesInImageSlide() {

        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);


    }

    private void pushImagesInImageSlider() {
        slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.tractor, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ramleela, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.sabha, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.sample_image3, ScaleTypes.FIT));
    }



}
