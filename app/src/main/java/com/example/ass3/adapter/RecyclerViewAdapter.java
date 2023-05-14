package com.example.ass3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass3.databinding.RvLayoutBinding;
import com.example.ass3.entity.UserWeight;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder> {
    private static List<UserWeight> userWeight;

    public RecyclerViewAdapter(List<UserWeight> userWeight) {
        this.userWeight = userWeight;
    }
    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        RvLayoutBinding binding=
                RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }
    //this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final UserWeight unit = userWeight.get(position);
        viewHolder.binding.tvRvunit.setText(unit.date);
        viewHolder.binding.tvRvmark.setText(unit.weight);
        viewHolder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeight.remove(unit);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return userWeight.size();
    }
    public void addUnits(List<UserWeight> results) {
        userWeight = results;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

