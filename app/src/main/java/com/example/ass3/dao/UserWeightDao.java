package com.example.ass3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ass3.entity.UserWeight;

import java.util.List;

@Dao
public interface UserWeightDao {
    @Query("SELECT * FROM userWeight ORDER BY date ASC")
    LiveData<List<UserWeight>> getAll();
    @Query("SELECT * FROM userWeight WHERE uid = :userWeightId LIMIT 1")
    UserWeight findByID(int userWeightId);
    @Insert
    void insert(UserWeight userWeight);
    @Delete
    void delete(UserWeight userWeight);
    @Update
    void updateUserWeight(UserWeight userWeight);
    @Query("DELETE FROM userWeight")
    void deleteAll();
}

