package com.example.ass3.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ass3.dao.UserWeightDao;
import com.example.ass3.database.UserWeightDatabase;
import com.example.ass3.entity.UserWeight;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class UserWeightRepository {

    private UserWeightDao userWeightDao;
    private LiveData<List<UserWeight>> allUserWeights;
    public UserWeightRepository(Application application){
        UserWeightDatabase db = UserWeightDatabase.getInstance(application);
        userWeightDao =db.userWeightDao();
        allUserWeights= userWeightDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<UserWeight>> getAllUserWeights() {
        return allUserWeights;
    }
    public void insert(final UserWeight userWeight){
        UserWeightDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userWeightDao.insert(userWeight);
            }
        });
    }
    public void deleteAll(){
        UserWeightDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userWeightDao.deleteAll();
            }
        });
    }
    public void delete(final UserWeight userWeight){
        UserWeightDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userWeightDao.delete(userWeight);
            }
        });
    }
    public void updateCustomer(final UserWeight userWeight){
        UserWeightDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userWeightDao.updateUserWeight(userWeight);
            }
        });
    }
    public CompletableFuture<UserWeight> findByIDFuture(final int userWeightId) {
        return CompletableFuture.supplyAsync(new Supplier<UserWeight>() {
            @Override
            public UserWeight get() {
                return userWeightDao.findByID(userWeightId);
            }
        }, UserWeightDatabase.databaseWriteExecutor);
    }
}
