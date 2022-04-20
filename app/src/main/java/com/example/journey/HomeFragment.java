package com.example.journey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView listViewHome;
    List<String> tripsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listViewHome = view.findViewById(R.id.listViewHome);
        tripsList = MainActivity.journeyDatabase.journeyDao().getHomePackets();
        ArrayAdapter<String> tripsAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,tripsList);
        listViewHome.setAdapter(tripsAdapter);

        return view;
    }
}
