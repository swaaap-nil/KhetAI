package com.example.khetai.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.khetai.R;



import java.util.ArrayList;




public class InputsAdapter extends RecyclerView.Adapter<InputsAdapter.ViewHolder> {

    ArrayList<Trio<String, Integer, Integer>> inputsData;

    public InputsAdapter(ArrayList<Trio<String, Integer,Integer>> inputsData) {
        this.inputsData = inputsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inputs_rv_single_row_design, parent, false);
        return new InputsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trio<String, Integer, Integer> trio = inputsData.get(position);
        String name= trio.getKey();
        Integer amount = trio.getValue();

        holder.name.setText(name);
        holder.quantity.setText(String.valueOf(amount+ " kg/acre"));
    }

    @Override
    public int getItemCount() {
        return inputsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.input_name);
            quantity = itemView.findViewById(R.id.input_quantity);

        }
    }
}
