package com.example.journey;


import static java.lang.Integer.parseInt;

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
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TripFragmentActions extends Fragment {
    EditText editTextCity,editTextCountry,editTextDuration,editTextType,editTextCoordinates;
    Button btAdd,btClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_a, container, false);

        editTextCity = view.findViewById(R.id.editTextCity);
        editTextCountry = view.findViewById(R.id.editTextCountry);
        editTextDuration = view.findViewById(R.id.editTextDuration);
        editTextType = view.findViewById(R.id.editTextType);
        editTextCoordinates = view.findViewById(R.id.editTextCoordinates);

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
            String uText = editTextCity.getText().toString().trim();
            String uTextA = editTextCountry.getText().toString().trim();
            String uTextD = editTextDuration.getText().toString().trim();
            String uTextT = editTextType.getText().toString().trim();
            String uTextC = editTextCoordinates.getText().toString().trim();
            if (!uText.equals("")&&!uTextA.equals("")&&!uTextD.equals("")&&!uTextT.equals("")&&!uTextC.equals("")) {
                Trip trip = new Trip(uText, uTextA,uTextD,uTextT,uTextC);
                JourneyDatabase.getInstance(getContext()).journeyDao().insertTrip(trip);
                Toast toast = Toast.makeText(getContext(), "Insert success", Toast.LENGTH_SHORT);
                toast.show();
                editTextCity.setText("");
                editTextCountry.setText("");
                editTextDuration.setText("");
                editTextType.setText("");
                editTextCoordinates.setText("");
                //notifications
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"Notifications");
                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                builder.setContentTitle("New Trip inserted "+currentDateandTime);
                builder.setContentText("Trip to "+uText+" for "+uTextD+"days");
                builder.setSmallIcon(R.drawable.ic_trip);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(1,builder.build());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TripFragmentResults()).commit();


            }
            else {Toast toast = Toast.makeText(getContext(), "Don't Leave Empty Fields", Toast.LENGTH_SHORT);
                toast.show();}
        }
    });
        btClear.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(),"Fields Cleared",Toast.LENGTH_SHORT);
            toast.show();
            editTextCity.setText("");
            editTextCountry.setText("");
            editTextDuration.setText("");
            editTextType.setText("");
            editTextCoordinates.setText("");
        });
        return view;
    }

}
