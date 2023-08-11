package com.example.khetai.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.ProductDetailsActivity;
import com.example.khetai.R;
import com.example.khetai.RecyclerViewInterface;
import com.example.khetai.model.Product;

import java.util.List;

public class RecommendPlantAdapter extends RecyclerView.Adapter<RecommendPlantAdapter.ViewHolder>implements RecyclerViewInterface {

    private Context context;
    private List<Product> recommendList;
    private final RecyclerViewInterface recyclerViewInterface;



    public RecommendPlantAdapter(Context context, List<Product> recommendList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recommendList = recommendList;
        this.recyclerViewInterface= recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecommendPlantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_item_plant,parent,false);
        return new ViewHolder(view,recyclerViewInterface) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendPlantAdapter.ViewHolder holder, int position) {
        holder.plantImage.setImageResource(recommendList.get(position).getProductImage());
        holder.plantName.setText(""+recommendList.get(position).getProductName());
        holder.plantPrice.setText("₹"+ recommendList.get(position).getProductPrice());


        holder.plantImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("clickedProductName",recommendList.get(position).getProductName());
            Log.d("clickedProductName",""+recommendList.get(position).getProductName());
            context.startActivity(intent);

        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("clickedProductName",recommendList.get(position).getProductName());
            Log.d("clickedProductName",""+recommendList.get(position).getProductName());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }

    @Override
    public void onItemClick(int position) { }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView plantImage;
        private TextView plantName,plantPrice;

        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            plantImage = itemView. findViewById(R.id.recommend_item_plant_picture) ;
            plantName = itemView.findViewById(R.id.recommend_item_plant_price);
            plantPrice = itemView. findViewById(R.id.recommend_item_plant_title) ;

            itemView.setOnClickListener(v -> {
                if(recyclerViewInterface!=null){
                    int position =getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(position);
                    }

                }

            });


        }

    }
}
