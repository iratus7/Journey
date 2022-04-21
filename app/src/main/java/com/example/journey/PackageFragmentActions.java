package com.example.journey;
import android.app.Activity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PackageFragmentActions extends Fragment {
    Spinner spAid,spTid;
    EditText editTextPDate,editTextPPrice;
    Button btAdd,btClear;
    List<String> spList = new ArrayList<>();
    List<String> spListT = new ArrayList<>();
    JourneyDatabase database;
    //private Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_a, container, false);

        spAid = view.findViewById(R.id.spinnerAId);
        spTid = view.findViewById(R.id.spinnerTId);
        editTextPDate = view.findViewById(R.id.editTextPDate);
        editTextPPrice = view.findViewById(R.id.editTextPrice);
        btAdd = view.findViewById(R.id.buttonAdd);
        btClear = view.findViewById(R.id.buttonClear);

        database = JourneyDatabase.getInstance(getContext());
        spList = database.journeyDao().getANames();
        ArrayAdapter<String> spAdapterA = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spList);
        spAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAid.setAdapter(spAdapterA);

        spListT = database.journeyDao().getTCity();
        ArrayAdapter<String> spAdapterT = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spListT);
        spAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTid.setAdapter(spAdapterT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("Notifications","Notifications",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        btAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String selectedItemA = spAid.getSelectedItem().toString();
            String selectedItemT = spTid.getSelectedItem().toString();

            int uTextAid = database.journeyDao().getAid(selectedItemA);
            int uTextTid = database.journeyDao().getTid(selectedItemT);
            String uTextD = editTextPDate.getText().toString().trim();
            String uTextT = editTextPPrice.getText().toString().trim();
            if (!uTextD.equals("")&&!uTextT.equals("")&& uTextAid>0&& uTextTid>0) {
                PackageTravel packageTravel = new PackageTravel(uTextAid, uTextTid,uTextD,uTextT);
                JourneyDatabase.getInstance(getContext()).journeyDao().insertPackageTravel(packageTravel);
                Toast toast = Toast.makeText(getContext(), "Insert success", Toast.LENGTH_SHORT);
                toast.show();
                editTextPDate.setText("");
                editTextPPrice.setText("");

                //notifications
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"Notifications");
                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                builder.setContentTitle("New Package inserted "+currentDateandTime);
                builder.setContentText("Package for "+uTextD+" with price "+uTextT+"€");
                builder.setSmallIcon(R.drawable.ic_packets);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(1,builder.build());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new PackageFragmentResults()).commit();

            }
            else {Toast toast = Toast.makeText(getContext(), "Don't Leave Empty Fields", Toast.LENGTH_SHORT);
                toast.show();}
        }
    });

        btClear.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(),"Fields Cleared",Toast.LENGTH_SHORT);
            toast.show();
            editTextPDate.setText("");
            editTextPPrice.setText("");
        });
        return view;
    }

}
