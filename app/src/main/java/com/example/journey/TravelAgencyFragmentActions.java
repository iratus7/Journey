package com.example.journey;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TravelAgencyFragmentActions extends Fragment {
    EditText editTextAgency,editTextAddress;
    Button btAdd,btClear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_agency_a, container, false);

        editTextAgency = view.findViewById(R.id.editTextAgency);
        editTextAddress = view.findViewById(R.id.editTextAddress);
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
            String uText = editTextAgency.getText().toString().trim();
            String uTextA = editTextAddress.getText().toString().trim();
            if (!uText.equals("")&&!uTextA.equals("")) {
                TravelAgency travelAgency = new TravelAgency(uText, uTextA);
                JourneyDatabase.getInstance(getContext()).journeyDao().insertTravelAgency(travelAgency);
                Toast toast = Toast.makeText(getContext(), "Insert success", Toast.LENGTH_SHORT);
                toast.show();
                editTextAgency.setText("");
                editTextAddress.setText("");
                //notifications
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"Notifications");
                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                builder.setContentTitle("New Travel Agency inserted "+currentDateandTime);
                builder.setContentText("Travel Agency "+uText);
                builder.setSmallIcon(R.drawable.ic_travelagency);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(1,builder.build());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TravelAgencyFragmentResults()).commit();



            }
            else {Toast toast = Toast.makeText(getContext(), "Don't Leave Empty Fields", Toast.LENGTH_SHORT);
                toast.show();}
        }
    });
        btClear.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(),"Fields Cleared",Toast.LENGTH_SHORT);
            toast.show();
            editTextAgency.setText("");
            editTextAddress.setText("");
        });
        return view;
    }

}
