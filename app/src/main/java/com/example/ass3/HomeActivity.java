package com.example.ass3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

                    // print API response
                    Log.d("WeatherApp", "Weather response: " + weatherResponse);

                    float temperatureKelvin = weatherResponse.getMain().getTemp();
                    int temperatureCelsius = Math.round(temperatureKelvin - 273.15F); // Convert from Kelvin to Celsius and round to nearest integer
                    String weatherData = "The weather in Melbourne now: " + temperatureCelsius + "℃";

                    TextView textView = findViewById(R.id.weather_info);
                    textView.setText(weatherData);

                    // Check if the weather list is not empty
                    if (!weatherResponse.getWeather().isEmpty()) {
                        // Get the icon code from the first Weather object
                        String iconCode = weatherResponse.getWeather().get(0).getIcon();
                        ImageView imageView = findViewById(R.id.weather_icon);
                        String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
                        Log.d("WeatherApp", "Icon URL: " + iconUrl);
                        Glide.with(HomeActivity.this)
                                .load(iconUrl)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Log.e("Glide", "Load failed", e);
                                        return false; // return false so the error placeholder can be placed
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(imageView);

                    }
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

        ImageView foodIcon = findViewById(R.id.food_icon);
        foodIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        });

        ImageView exerciseIcon = findViewById(R.id.exercise_icon);
        exerciseIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            startActivity(intent);
        });

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


