package com.example.khetai.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.MySingleton;
import com.example.khetai.R;
import com.example.khetai.RecyclerViewInterface;
import com.example.khetai.model.Land;

import java.util.ArrayList;

public class LandCountAdapter extends RecyclerView.Adapter<LandCountAdapter.holder> {


    ArrayList<Land> data;

    private final RecyclerViewInterface recyclerViewInterface;


    public LandCountAdapter(@NonNull ArrayList<Land> data, RecyclerViewInterface recyclerViewInterface) {
        this.data = data;
        this.recyclerViewInterface = recyclerViewInterface;

    }



    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.location_set_single_row_design, parent, false);
        return new holder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {



        holder.editText_Area.setHint("Area in Acre");
        holder.textView.setText("Farm " + data.get(position).getLandNumber());
        if (data.get(position).getLatitude() != 0) {
            holder.editText_Location.setText("Done");
            holder.tickIcon.setVisibility(View.VISIBLE);
            holder.locationIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Land getItem(int position) {
        return data.get(position);
    }

    class holder extends RecyclerView.ViewHolder {
        EditText editText_Area;
        AppCompatButton editText_Location;
        TextView textView;
        ImageView locationIcon, tickIcon;

        public holder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            editText_Area = itemView.findViewById(R.id.editText_landArea);
            editText_Location = itemView.findViewById(R.id.textView_landLocation);
            textView = itemView.findViewById(R.id.textView_landNoText);
            locationIcon = itemView.findViewById(R.id.location);
            tickIcon = itemView.findViewById(R.id.done_tick);

            editText_Location.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });

            editText_Area.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(editText_Area.getText().toString().length()!=0)
                   MySingleton.getInstance().getLandAt(getBindingAdapterPosition()).setArea(Double.parseDouble( editText_Area.getText().toString()));
                }
            });


        }
    }
}

