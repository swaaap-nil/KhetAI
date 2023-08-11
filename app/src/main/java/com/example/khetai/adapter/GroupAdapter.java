package com.example.khetai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.MarketFragment;
import com.example.khetai.ProductDetailsActivity;
import com.example.khetai.R;
import com.example.khetai.RecyclerViewInterface;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Group;
import com.example.khetai.model.Product;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private Context context;
    private List<Group> groupList;
    private List<Product> featuredList;
    private List<Product> recommendList;
    private List<Crop> yourCropList;
    private List<Crop> recommendCropList;
    private final RecyclerViewInterface recyclerViewInterface;
    
    public GroupAdapter(Context context, List<Group> groupList,
                        List<Product> featuredList,
                        List<Product> recommendList,
                        RecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.groupList = groupList;
        this.featuredList = featuredList;
        this.recommendList = recommendList;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        holder.groupTitle.setText(groupList.get(position).getGroupTitle());
        //holder.groupButton.setText(groupList.get(position).getGroupButtonTitle());
        holder.setList(holder.recyclerView, position);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView groupTitle;
        private AppCompatButton groupButton;
        private RecyclerView recyclerView;;
        int pos;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            groupTitle = itemView.findViewById(R.id.group_title);
           // groupButton = itemView.findViewById(R.id.group_button);
            recyclerView = itemView.findViewById(R.id.group_RV);


        }

        public void setList(RecyclerView recyclerView, int position) {
            switch (position) {
                case 0:
                    setRecommendList(recyclerView);
                    break;
                case 1:
                    setFeaturedList(recyclerView);
                    break;
            }
        }
    }

    private void setRecommendList(RecyclerView recyclerView) {
        RecommendPlantAdapter adapter = new RecommendPlantAdapter(context, recommendList, recyclerViewInterface);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);


    }


    private void setFeaturedList(RecyclerView recyclerView) {
        FeaturedProductAdapter adapter = new FeaturedProductAdapter(context, featuredList,recyclerViewInterface);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);

    }

}
