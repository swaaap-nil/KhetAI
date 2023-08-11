package com.example.khetai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.khetai.R;
import com.example.khetai.model.Crop;
import java.util.ArrayList;


public class InputsAtOnePlaceAdapter extends RecyclerView.Adapter<InputsAtOnePlaceAdapter.ViewHolder> {

    ArrayList<Crop> myCropList;

    public InputsAtOnePlaceAdapter(ArrayList<Crop> myCropList) {
        this.myCropList = myCropList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inputs_at_one_place_single_row_design, parent, false);
        return new InputsAtOnePlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String name = myCropList.get(position).getCropName();
        String amount = "";
        for (int i = 0; i < myCropList.get(position).getInputs().size()-1; i++) {
            Trio<String, Integer, Integer> trio = myCropList.get(position).getInputs().get(i);
            if(i!=myCropList.get(position).getInputs().size())
            amount = amount.concat(trio.getKey() + " " + trio.getValue() + "\n");
            else amount = amount.concat(trio.getKey() + " " + trio.getValue());
        }
        holder.name.setText("Input Details for " + name);
        holder.details.setText(amount);
    }

    @Override
    public int getItemCount() {
        return myCropList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.heading_111);
            details = itemView.findViewById(R.id.inputs_111);

        }
    }
}
