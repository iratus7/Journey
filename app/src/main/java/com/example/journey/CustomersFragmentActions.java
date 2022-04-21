package com.example.journey;


import static com.example.journey.MainActivity.db;
import static java.lang.Integer.parseInt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class CustomersFragmentActions extends Fragment {
    EditText editTextName, editTextHotel;
    Spinner spPackageId;
    Button btAdd, btClear;
    List<String> spPList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers_a, container, false);

        editTextName = view.findViewById(R.id.editTextCustomerName);
        editTextHotel = view.findViewById(R.id.editTextHotel);
        //editTextPackageId = view.findViewById(R.id.editTextCustomerPackageId);
        spPackageId = view.findViewById(R.id.spinnerPackageId);


        spPList = MainActivity.journeyDatabase.journeyDao().getPackageDetails();
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spPList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPackageId.setAdapter(spAdapter);

        btAdd = view.findViewById(R.id.buttonAdd);
        btClear = view.findViewById(R.id.buttonClear);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("Notifications","Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uTextN = editTextName.getText().toString().trim();
                String uTextH = editTextHotel.getText().toString().trim();
                //String selectedItemPid = spPackageId.getSelectedItem().toString();
                int position = spPackageId.getSelectedItemPosition()+1;
                int uTextP = MainActivity.journeyDatabase.journeyDao().getSelectedPackageId(position);

                //int uTextP = MainActivity.journeyDatabase.journeyDao().getTid(selectedItemPid);

                try {
                    if (!uTextN.equals("") && !uTextH.equals("") && uTextP > 0) {
                        //Customers customers = new Customers();
                        //customers.setName(uTextN);
                        //customers.setHotel(uTextH);
                        //customers.setPackagetravelid(uTextP);
                        Customers customers = new Customers(uTextN, uTextH, uTextP);
                        //db.setLoggingEnabled(true);
                        db.collection("Customers").document().set(customers).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Customer added", Toast.LENGTH_LONG).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "add operation failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                        editTextName.setText("");
                        editTextHotel.setText("");
                        //notifications
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"Notifications");
                        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        builder.setContentTitle("New Customer inserted "+currentDateandTime);
                        builder.setContentText("Customer "+uTextN+" in hotel "+uTextH);
                        builder.setSmallIcon(R.drawable.ic_customers);
                        builder.setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                        managerCompat.notify(1,builder.build());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new CustomersFragmentResults()).commit();

                    } else {
                        Toast toast = Toast.makeText(getContext(), "Don't Leave Empty Fields", Toast.LENGTH_SHORT);
                        toast.show();
                    }


                } catch (Exception e) {
                    String message = e.getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            }

        });
        btClear.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(), "Fields Cleared", Toast.LENGTH_SHORT);
            toast.show();
            editTextName.setText("");
            editTextHotel.setText("");

        });

        return view;
    }

}
