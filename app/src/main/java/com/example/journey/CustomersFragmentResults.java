package com.example.journey;
import static com.example.journey.MainActivity.db;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomersFragmentResults extends Fragment {
    MainAdapterCustomers adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    List<Customers> dataList = new ArrayList<>();
    JourneyDatabase database;
    //FirebaseFirestore firebaseFirestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trip_r,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);

        //firebaseFirestore.getInstance();
        //Query query = firebaseFirestore.collection("Customers");

        db.collection("Customers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        dataList.add(document.toObject(Customers.class));
                    }

                    Toast toast = Toast.makeText(getContext(), "list is : "+dataList.toString(), Toast.LENGTH_SHORT);
                    toast.show();

                    linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new MainAdapterCustomers((Activity) getContext(),dataList);
                    recyclerView.setAdapter(adapter);


                } else {
                    Toast toast = Toast.makeText(getContext(),"Error getting documents: "+ task.getException(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });





        
//        database = JourneyDatabase.getInstance(getContext());
//        dataList = database.journeyDao().getCustomers();

        return view ;
    }


}
