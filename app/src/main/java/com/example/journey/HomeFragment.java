package com.example.journey;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        ArrayAdapter<String> tripsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tripsList);
        listViewHome.setAdapter(tripsAdapter);
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                for(int a = 0; a < arg0.getChildCount(); a++)
                {
                    arg0.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                }

                v.setBackgroundColor(Color.WHITE);

                Bundle bundle = new Bundle();
                String[] selectedCity = tripsList.get(position).split(",");
                bundle.putString("SelectedCity", selectedCity[0]);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainerResults, mapsFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
