package com.example.khetai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.FragmentToday;
import com.example.khetai.NotAvPage;
import com.example.khetai.R;
import com.example.khetai.ViewCropByDate;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class TodaysActivityAdapter extends RecyclerView.Adapter<TodaysActivityAdapter.ViewHolder> {

    private HashMap<String,Trio<String,Integer,Integer>> mData;
    ArrayList<String> headingsList;
    ArrayList <Trio<String,Integer,Integer>> descListWithImages;
    ArrayList <Integer> image1List;
    ArrayList <Integer> image2List;
    private Integer mContext;
//    ArrayList<Trio<String,Integer,Integer>> descListWithImages;


    public TodaysActivityAdapter(Integer context, LinkedHashMap<String,Trio<String,Integer,Integer>> data) {
        mData = data;
        this.mContext = context;
        Set<String> headings = mData.keySet();
         headingsList= new ArrayList<>(headings);


        Collection<Trio<String,Integer,Integer>> collection = mData.values();
        descListWithImages = new ArrayList<>(collection);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todays_activity_single_row_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mContext==1){
            holder.checkBox.setVisibility(View.INVISIBLE);

        };

        Trio <String,Integer,Integer > currentDesc = descListWithImages.get(position);
        holder.ActivityNumber.setText(headingsList.get(position));
        holder.activityDescription.setText(currentDesc.getKey());

        if(currentDesc.getValue()!=0 )
        holder.img1.setImageResource(currentDesc.getValue());
        else
            holder.card1.setVisibility(View.GONE);

        if(currentDesc.getElement2()!=0)
        holder.img2.setImageResource(currentDesc.getElement2());
        else
            holder.card2.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ActivityNumber;
        TextView activityDescription;
        ShapeableImageView img1,img2;
        MaterialCardView card1,card2;
        MaterialCheckBox checkBox;


        public ViewHolder(View view) {
            super(view);
             activityDescription = view.findViewById(R.id.activitydescribed);
            ActivityNumber = view.findViewById(R.id.activity_number);
            card1 = view.findViewById(R.id.leftCard);
            card2 = view.findViewById(R.id.rightCard);
            img1=view.findViewById(R.id.image1);
            img2=view.findViewById(R.id.image2);
            checkBox = view.findViewById(R.id.checkBox);

            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NotAvPage.class);

                    view.getContext().startActivity (intent);
                }
            });

        }
    }
}
