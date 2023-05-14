package com.example.ass3.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ass3.dao.UserWeightDao;
import com.example.ass3.entity.UserWeight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserWeight.class}, version = 1, exportSchema = false)
public abstract class UserWeightDatabase extends RoomDatabase {
    public abstract UserWeightDao userWeightDao();

    private static UserWeightDatabase INSTANCE;
    // we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //A synchronized method in a multi threaded environment means that two thread are not allowed to access data at the same time
    public static synchronized UserWeightDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserWeightDatabase.class, "UserWeightDatabase")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}