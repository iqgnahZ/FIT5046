package com.example.ass3;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass3.adapter.RecyclerViewAdapter;
import com.example.ass3.entity.UserWeight;
import com.example.ass3.viewmodel.UserWeightViewModel;
import com.example.ass3.databinding.ActivityWeightBinding;
import java.util.ArrayList;
import java.util.List;

public class WeightActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ActivityWeightBinding binding;
    private UserWeightViewModel userWeightViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // binding.idTextField.setPlaceholderText("This is only used for Edit");
        // we make sure that AndroidViewModelFactory creates the view model so it can accept the Application as the parameter
        userWeightViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserWeightViewModel.class);
        adapter = new RecyclerViewAdapter(new ArrayList<>());
        //this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new
                DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        userWeightViewModel.getAllUserWeights().observe(this, new Observer<List<UserWeight>>() {
                    @Override
                    public void onChanged(@Nullable final List<UserWeight> userWeights) {
//                        String allUserWeights = "";
                        adapter.addUnits(userWeights);
                    }
                });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = binding.etUnit.getText().toString().trim();
                String weight = binding.etMark.getText().toString().trim();
                if (!date.isEmpty() || !weight.isEmpty()) {
                    UserWeight userWeight = new UserWeight(date, weight);
                    userWeightViewModel.insert(userWeight);
                }
            }});
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    userWeightViewModel.deleteAll();
                }
        });
    }
}
