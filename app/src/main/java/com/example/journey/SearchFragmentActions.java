package com.example.journey;

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

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentActions extends Fragment {
    ListView listViewSearchAction;
    List<String> searchSelectionList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_a, container, false);
        listViewSearchAction = view.findViewById(R.id.listViewSearchAction);
        searchSelectionList.add("Top 5 cheapest trips");
        searchSelectionList.add("Top 5 longest duration of trips");
        searchSelectionList.add("Top 5 cities having most offers from travel agencies");
        searchSelectionList.add("Top 5 cheapest customers");
        searchSelectionList.add("Top 5 hotels");
        searchSelectionList.add("Top 5 prices");
        ArrayAdapter<String> searchSelectionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchSelectionList);
        listViewSearchAction.setAdapter(searchSelectionAdapter);
        listViewSearchAction.setSelection(0);


        listViewSearchAction.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragmentResults searchFragmentResults = new SearchFragmentResults();
                searchFragmentResults.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainerResults, searchFragmentResults);
                fragmentTransaction.commit();

            }
        });
        return view;
    }
}
