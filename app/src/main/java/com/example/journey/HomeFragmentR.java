package com.example.journey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class HomeFragmentR extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_r,container,false);

        //TravelAgency travelAgency1 = new TravelAgency(2,"hermes","dragoymi");
        //MainActivity.journeyDatabase.journeyDao().insertTravelAgency(travelAgency1);
//        List<TravelAgency> travelAgency = new TravelAgency(MainActivity.journeyDatabase.journeyDao().getTravelAgency());
//        String result = "";
//    for (TravelAgency element : travelAgency){
//        int id = element.id;
//        String name = element.agency_name;
//        String address = element.address;
//    }

    }
}
