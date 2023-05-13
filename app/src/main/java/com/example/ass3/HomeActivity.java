package com.example.ass3;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import api.WeatherResponse;
import api.WeatherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static final String BASE_URL = "https://api.openweathermap.org/";
    public static final String APP_ID = "fc597d1e27d837bed4d372e2f050577b";


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        // Set the drawer toggle as the DrawerListener
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//
//        actionBarDrawerToggle.syncState();
//
//        // Set the icon for the drawer toggle
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Setup navigation view
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(menuItem -> {
//            switch (menuItem.getItemId()) {
//                case R.id.nav_map:
//                    // Replace content_frame with MapFragment
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.content_frame, new MapFragment())
//                            .commit();
//                    break;
//                // Handle other screen navigation here
//            }
//            // Close the navigation drawer
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return true;
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        // Set the icon for the drawer toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_map:
                    // Replace content_frame with MapFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new MapFragment())
                            .commit();
                    break;
                // Handle other screen navigation here
            }
            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Begin the Retrofit code
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = service.getCurrentWeatherData("-37.8136", "144.9631", APP_ID);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    float temperature = weatherResponse.getMain().getTemp() - 273.15F; // Convert from Kelvin to Celsius
                    String weatherData = "Current temperature: " + temperature + "℃";

                    TextView textView = findViewById(R.id.weather_info);
                    textView.setText(weatherData);
                } else {
                    Log.d("WeatherApp", "Failed to get weather data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("WeatherApp", "Failed to call weather API", t);
            }
        });
        // End of Retrofit code

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


