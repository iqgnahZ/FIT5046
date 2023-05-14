package com.example.ass3.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ass3.entity.UserWeight;
import com.example.ass3.repository.UserWeightRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserWeightViewModel extends AndroidViewModel {
    private UserWeightRepository uRepository;
    private LiveData<List<UserWeight>> allUserWeights;
    public UserWeightViewModel(Application application) {
        super(application);
        uRepository = new UserWeightRepository(application);
        allUserWeights = uRepository.getAllUserWeights(); //
    }

    public CompletableFuture<UserWeight> findByIDFuture(final int userWeightId){
        return uRepository.findByIDFuture(userWeightId);
    }
    public LiveData<List<UserWeight>> getAllUserWeights() {
        return allUserWeights;
    }
    public void insert(UserWeight userWeight) {
        uRepository.insert(userWeight);
    }
    public void delete(UserWeight userWeight) {uRepository.delete(userWeight);}
    public void deleteAll() {
        uRepository.deleteAll();
    }
    public void update(UserWeight userWeight) {
        uRepository.updateCustomer(userWeight);
    }
}