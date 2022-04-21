package com.example.journey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class SearchFragmentResults extends Fragment {
    ListView listViewSearchResults;
    List<String> searchResultsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_r, container, false);
        listViewSearchResults = view.findViewById(R.id.listViewSearchResults);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("position")+1;
            switch (position){
                case 1:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getTop5CheapPackages();
                    break;
                case 2:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getTop5DurationTrips();

                    break;
                case 3:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getTop5CitiesOfferedFromPackets();
                    break;
                case 4:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getHomePackets();
                    break;
                case 5:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getHomePackets();
                    break;
                case 6:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getHomePackets();
                    break;
            }
        }
        else {
            searchResultsList = MainActivity.journeyDatabase.journeyDao().getTop5CheapPackages();

        }
        ArrayAdapter<String> searchResultsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchResultsList);
        listViewSearchResults.setAdapter(searchResultsAdapter);
            return view;
        }

}