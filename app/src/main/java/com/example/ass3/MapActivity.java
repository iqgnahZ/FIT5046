package com.example.ass3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.mapbox.mapboxsdk.Mapbox;

public class MapActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoiendhbjk4IiwiYSI6ImNsaGh0OHZ6NzAxN3gzZGxnMnF5NmM4OW0ifQ.sNz1xIUNpvE-ANrmcpWIyw");
        setContentView(R.layout.fragment_map);

        // Replace the frame layout with MapFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MapFragment())
                .commit();
    }


}
