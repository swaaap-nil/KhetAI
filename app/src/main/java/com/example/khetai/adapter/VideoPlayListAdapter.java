package com.example.khetai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.R;
import com.example.khetai.model.Scheme;

import java.util.ArrayList;

public class VideoPlayListAdapter extends RecyclerView.Adapter<VideoPlayListAdapter.ViewHolder> {

    ArrayList<Scheme> dataholder;

    public VideoPlayListAdapter(ArrayList<Scheme> dataholder) {
        this.dataholder = dataholder;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.govt_scheme_single_row, parent, false);
        return new VideoPlayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(dataholder.get(position).getImgName());
       // holder.T1.setText(dataholder.get(position).getHeader());
        holder.T2.setText(dataholder.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView T1, T2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.scheme_img);
            //T1= itemView.findViewById(R.id.scheme_heading);
            T2= itemView.findViewById(R.id.scheme_details);

        }
    }
}
