package com.example.journey;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterCustomers extends RecyclerView.Adapter<MainAdapterCustomers.ViewHolder> {

    private List<Customers> dataList;
    private Activity context;
    private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapterCustomers(Activity context, List<Customers> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    public void reloadMainAdapter(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_customer,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Customers data = dataList.get(position);
        //database = JourneyDatabase.getInstance(context);
        //String city = database.journeyDao().getTripCity(data.TripId);
        //String CName = database.journeyDao().getAgencyName(data.TravelAgencyId);

        holder.textViewCName.setText(data.getName());
        holder.textViewCHotel.setText(data.getHotel());
        holder.textViewCPId.setText(data.getPackagetravelid());
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Customers c = dataList.get(holder.getAdapterPosition());

                String name = c.getName();
                String hotel = c.getHotel();
                int pid = c.getPackagetravelid();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_package_travel);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                Spinner spAId = dialog.findViewById(R.id.SpinnerAId);
                Spinner spTId = dialog.findViewById(R.id.SpinnerTId);
                EditText editTextD = dialog.findViewById(R.id.edit_TextPDate);
                EditText editTextP = dialog.findViewById(R.id.edit_TextPPrice);
                Button btUpdate = dialog.findViewById(R.id.bt_update);
                Button btCancel = dialog.findViewById(R.id.bt_cancel);

                List<String> spList ;
                List<String> spListT ;
                //JourneyDatabase database = JourneyDatabase.getInstance(context);
                spList = database.journeyDao().getANames();
                ArrayAdapter<String> spAdapterA = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spList);
                spAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spListT = database.journeyDao().getTCity();
                ArrayAdapter<String> spAdapterT = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spListT);
                spAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spAId.setAdapter(spAdapterA);
                spTId.setAdapter(spAdapterT);
                /*spAId.setSelection(AId-1);
                spTId.setSelection(TId-1);

                editTextD.setText(date);
                editTextP.setText(price);*/

                btCancel.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    //dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getPackageTravel());

                });

                btUpdate.setOnClickListener(v1 -> {
                    /*dialog.dismiss();
                    int Aid = spAId.getSelectedItemPosition()+1;
                    int Tid = spTId.getSelectedItemPosition()+1;
                    String uTextd = editTextD.getText().toString().trim();
                    String uTextp = editTextP.getText().toString().trim();
                    PackageTravel packageTravel = new PackageTravel(Pid,Aid,Tid,uTextd,uTextp);
                    JourneyDatabase.getInstance(context).journeyDao().updatePackageTravel(packageTravel);
                    dataList.clear();
                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getPackageTravel());
                    reloadMainAdapter();
                    Toast toast = Toast.makeText(context, "Update success", Toast.LENGTH_SHORT);
                    toast.show();*/
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*PackageTravel data = dataList.get(holder.getAdapterPosition());
                JourneyDatabase.getInstance(context).journeyDao().deletePackageTravel(data);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCName, textViewCHotel,textViewCPId;
        ImageView btEdit,btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCName = itemView.findViewById(R.id.text_view_customer_name);
            textViewCHotel = itemView.findViewById(R.id.text_view_hotel);
            textViewCPId = itemView.findViewById(R.id.text_view_customer_package);

            btEdit = itemView.findViewById(R.id.bt_edit_customer);
            btDelete = itemView.findViewById(R.id.bt_delete_customer);
        }
    }
}
