package com.example.journey;

import static com.example.journey.MainActivity.db;
import static java.lang.Integer.parseInt;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CustomersFragmentResults extends Fragment {
    MainAdapterCustomers adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    List<Customers> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customers_r, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        db.collection("Customers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pid = document.getLong("packagetravelid").toString();
                                String hiddenId= document.getId();
                                Customers customer = new Customers(document.getString("name"), document.getString("hotel"), parseInt(pid),hiddenId);
                                dataList.add(customer);
                                linearLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                adapter = new MainAdapterCustomers((Activity) getContext(), dataList);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }
}
