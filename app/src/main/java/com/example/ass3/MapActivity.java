package com.example.ass3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mapbox Access token
        Mapbox.getInstance(this, "pk.eyJ1Ijoiendhbjk4IiwiYSI6ImNsaGh0OHZ6NzAxN3gzZGxnMnF5NmM4OW0ifQ.sNz1xIUNpvE-ANrmcpWIyw");
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {

            // Map is set up and the style has loaded. Now you can add data or make other map adjustments.

        }));

        // Firebase reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String address = documentSnapshot.getString("address");

                    // Geocode the address to LatLng
                    MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                            .accessToken(Mapbox.getAccessToken())
                            .query(address)
                            .build();

                    mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
                        @Override
                        public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                            List<CarmenFeature> results = response.body().features();
                            if (results.size() > 0) {
                                // Get the first result
                                CarmenFeature feature = results.get(0);

                                Point point = feature.center();
                                LatLng latLng = new LatLng(point.latitude(), point.longitude());

                                mapView.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(@NonNull MapboxMap mapboxMap) {
                                        mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS),
                                                new Style.OnStyleLoaded() {
                                                    @Override
                                                    public void onStyleLoaded(@NonNull Style style) {
                                                        SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
                                                        symbolManager.create(new SymbolOptions()
                                                                .withLatLng(latLng)
                                                                .withIconImage("marker-15")
                                                                .withIconSize(3.0f));

                                                        // Set map camera position to user address
                                                        CameraPosition position = new CameraPosition.Builder()
                                                                .target(latLng)
                                                                .zoom(13)
                                                                .build();

                                                        mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
                                                    }
                                                });
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                            // Handle failure
                        }
                    });
                });

    }

    // Add the mapView's own lifecycle methods to the activity's lifecycle methods
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
