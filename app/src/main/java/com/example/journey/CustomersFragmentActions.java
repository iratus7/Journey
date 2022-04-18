package com.example.journey;


import static java.lang.Integer.parseInt;

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


public class CustomersFragmentActions extends Fragment {
    EditText editTextName,editTextHotel,editTextPackageId;
    Button btAdd,btClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers_a, container, false);

        editTextName = view.findViewById(R.id.editTextCustomerName);
        editTextHotel = view.findViewById(R.id.editTextHotel);
        editTextPackageId = view.findViewById(R.id.editTextCustomerPackageId);


        btAdd = view.findViewById(R.id.buttonAdd);
        btClear = view.findViewById(R.id.buttonClear);

        btAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

            String uTextN = editTextName.getText().toString().trim();
            String uTextH = editTextHotel.getText().toString().trim();
            int uTextP = parseInt(editTextPackageId.getText().toString().trim());

            if (!uTextN.equals("")&&!uTextH.equals("")&&uTextP>0) {
                Customers customers = new Customers(uTextN, uTextH,uTextP);
                MainActivity.db.collection("Customers").document().set(customers).addOnCompleteListener((task) -> {Toast.makeText(getContext(),"Customer Added",Toast.LENGTH_LONG).show();})
                .addOnFailureListener((e)->{Toast.makeText(getContext(),"Fail to Add Customer",Toast.LENGTH_LONG).show();});


//                JourneyDatabase.getInstance(getContext()).journeyDao().insertTrip(customers);
//                Toast toast = Toast.makeText(getContext(), "Insert success", Toast.LENGTH_SHORT);
//                toast.show();
            editTextName.setText("");
            editTextHotel.setText("");
            editTextPackageId.setText("");

                ///////////////////
                requireActivity().recreate();

            }
            else {Toast toast = Toast.makeText(getContext(), "Don't Leave Empty Fields", Toast.LENGTH_SHORT);
                toast.show();}}
            catch(Exception e){
                String message = e.getMessage();
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        }
    });
        btClear.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(),"Fields Cleared",Toast.LENGTH_SHORT);
            toast.show();
            editTextName.setText("");
            editTextHotel.setText("");
            editTextPackageId.setText("");

        });
        return view;
    }

}
