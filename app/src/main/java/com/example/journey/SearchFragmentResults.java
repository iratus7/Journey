package com.example.journey;

import static com.example.journey.MainActivity.db;
import static java.lang.Integer.parseInt;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            int position = bundle.getInt("position") + 1;
            switch (position) {
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
                    List<String> hotels = new ArrayList<>();
                    db.collection("Customers")//.whereNotEqualTo("hotel",null)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            hotels.add(document.getString("hotel"));
                                        }
                                        Map<String, Long> couterMap = hotels.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                                        Map<String, Long> sortedMap = new LinkedHashMap<>();
                                        couterMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                                                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

                                        int n = sortedMap.size();
                                        for (int i = n - 1; i >= n - 5; i--) {
                                            String[] out = sortedMap.entrySet().toArray()[i].toString().split("=");
                                            searchResultsList.add("Hotel : " + out[0] + " used " + out[1] + " times");
                                        }
                                        ArrayAdapter<String> searchResultsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchResultsList);
                                        listViewSearchResults.setAdapter(searchResultsAdapter);
                                    } else {
                                        Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                case 5:
                    List<String> names = new ArrayList<>();
                    db.collection("Customers")//.whereNotEqualTo("hotel",null)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            names.add(document.getString("name"));
                                        }
                                        Map<String, Long> couterMap = names.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                                        Map<String, Long> sortedMap = new LinkedHashMap<>();
                                        couterMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                                                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

                                        int n = sortedMap.size();
                                        for (int i = n - 1; i >= n - 5; i--) {
                                            String[] out = sortedMap.entrySet().toArray()[i].toString().split("=");
                                            searchResultsList.add("Customer : " + out[0] + " travel with us " + out[1] + " times");
                                        }
                                        ArrayAdapter<String> searchResultsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchResultsList);
                                        listViewSearchResults.setAdapter(searchResultsAdapter);
                                    } else {
                                        Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                case 6:
                    searchResultsList = MainActivity.journeyDatabase.journeyDao().getHomePackets();
                    break;
            }
        } else {
            searchResultsList = MainActivity.journeyDatabase.journeyDao().getTop5CheapPackages();

        }
        ArrayAdapter<String> searchResultsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchResultsList);
        listViewSearchResults.setAdapter(searchResultsAdapter);
        return view;
    }

}