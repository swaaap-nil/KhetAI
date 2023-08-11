package com.example.khetai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.R;
import com.example.khetai.RecyclerViewInterface;
import com.example.khetai.model.Crop;

import java.util.ArrayList;

public class YourCropsAdapter extends RecyclerView.Adapter<YourCropsAdapter.ViewHolder> {


    private ArrayList<Crop> myCropList;
    private final RecyclerViewInterface recyclerViewInterface;
    public YourCropsAdapter(ArrayList<Crop> myCropList,RecyclerViewInterface recyclerViewInterface){
        this.myCropList = myCropList;
        this.recyclerViewInterface=recyclerViewInterface;

    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_single_row_design, parent, false);
        return new ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cropImage.setImageResource(myCropList.get(position).getCropImage());
        holder.cropName.setText(myCropList.get(position).getCropName());
    }
    
    @Override
    public int getItemCount() {
        return myCropList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cropImage;
        private TextView cropName;

        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            cropImage = itemView.findViewById(R.id.your_crop_picture);
            cropName = itemView.findViewById(R.id.your_crop_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            
        }

    }


}
