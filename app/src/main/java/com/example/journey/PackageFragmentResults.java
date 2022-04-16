package com.example.journey;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PackageFragmentResults extends Fragment {
    MainAdapterPackage adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    List<PackageTravel> dataList = new ArrayList<>();
    JourneyDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_package_r,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        database = JourneyDatabase.getInstance(getContext());
        dataList = database.journeyDao().getPackageTravel();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapterPackage((Activity) getContext(),dataList);
        recyclerView.setAdapter(adapter);
        return view ;
    }
}
