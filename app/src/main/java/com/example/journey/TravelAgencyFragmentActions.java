package com.example.journey;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



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
