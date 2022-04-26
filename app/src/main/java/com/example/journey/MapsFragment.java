package com.example.journey;

import static java.lang.Double.parseDouble;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    GoogleMap eMap;
    List<String> coordinatesList = new ArrayList<>();
    List<String> cityList = new ArrayList<>();
    List<String> tripDuration = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            eMap = googleMap;
            coordinatesList = MainActivity.journeyDatabase.journeyDao().getTCoordinates();
            cityList = MainActivity.journeyDatabase.journeyDao().getTCity();
            tripDuration = MainActivity.journeyDatabase.journeyDao().getTDuration();
            if (!cityList.isEmpty()) {
                for (int i = 0; i < cityList.size(); i++) {
                    String[] latlong = coordinatesList.get(i).split(",");
                    double latitude = parseDouble(latlong[0]);
                    double longitude = parseDouble(latlong[1]);
                    LatLng city = new LatLng(latitude, longitude);
                    eMap.addMarker(new MarkerOptions().position(city).title("Trip to " + cityList.get(i) + " for " + tripDuration.get(i) + " days!"));
                }
            }
            LatLng thess = new LatLng(40.640064, 22.944420);

            Bundle bundle = getArguments();
            if (bundle != null) {
                String selectedCity = bundle.getString("SelectedCity");
                String coordinates = MainActivity.journeyDatabase.journeyDao().getCoordinatesByCity(selectedCity);
                String[] latlong = coordinates.split(",");
                double latitude = parseDouble(latlong[0]);
                double longitude = parseDouble(latlong[1]);
                LatLng selectedCityMoveCamera = new LatLng(latitude, longitude);
                eMap.moveCamera(CameraUpdateFactory.newLatLng(selectedCityMoveCamera));
                Toast.makeText(getContext(), "Trip to  " + selectedCity, Toast.LENGTH_LONG).show();
            } else {
                eMap.moveCamera(CameraUpdateFactory.newLatLng(thess));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}