package com.example.ass3.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserWeight {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "date")
    @NonNull
    public String date;
    @ColumnInfo(name = "weight")
    @NonNull
    public String weight;
//    public double salary;

    public UserWeight(@NonNull String date, @NonNull String weight) {
        this.date=date;
        this.weight=weight;
//        this.salary = salary;
    }
}
