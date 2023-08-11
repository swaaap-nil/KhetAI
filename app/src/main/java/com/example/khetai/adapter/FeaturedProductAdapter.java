package com.example.khetai.adapter;

import android.content.Context;
import android.content.Intent;
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

public class FeaturedProductAdapter extends RecyclerView.Adapter<FeaturedProductAdapter.ViewHolder> implements RecyclerViewInterface {

    private Context context;
    private List<Product> featuredList;
    private final RecyclerViewInterface recyclerViewInterface;


    public FeaturedProductAdapter(Context context, List<Product> featuredList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.featuredList = featuredList;
        this.recyclerViewInterface= recyclerViewInterface;
    }

    @NonNull
    @Override
    public FeaturedProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.featured_plant_item,parent,false);
        return new ViewHolder(view,recyclerViewInterface) ;

    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedProductAdapter.ViewHolder holder, int position) {

        holder.plantImage.setImageResource(featuredList.get(position).getProductImage());
        holder.plantName.setText(featuredList.get(position).getProductName());
        holder.plantPrice.setText("₹"+featuredList.get(position).getProductPrice());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("clickedProductName",featuredList.get(position).getProductName());
            context.startActivity(intent);

        });

        holder.plantImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("clickedProductName",featuredList.get(position).getProductName());
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {return featuredList.size();}
    @Override
    public void onItemClick(int position) { }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView plantImage;
        private TextView plantName,plantPrice;

        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            plantImage = itemView. findViewById(R.id.featured_item_plant_picture) ;
            plantName = itemView.findViewById(R.id.featured_item_plant_title);
            plantPrice = itemView. findViewById(R.id.featured_item_plant_price) ;

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
