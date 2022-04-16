package com.example.journey;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterPackage extends RecyclerView.Adapter<MainAdapterPackage.ViewHolder> {

    private List<PackageTravel> dataList;
    private Activity context;
    private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapterPackage(Activity context, List<PackageTravel> dataList){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_package,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PackageTravel data = dataList.get(position);
        database = JourneyDatabase.getInstance(context);
        String city = database.journeyDao().getTripCity(data.TravelAgencyId);
        String agency = database.journeyDao().getAgencyName(data.TripId);

        holder.textViewPAgencyId.setText(city);
        holder.textViewPTripId.setText(agency);
        holder.textViewPDate.setText(data.date);
        holder.textViewPPrice.setText(data.price);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                PackageTravel p = dataList.get(holder.getAdapterPosition());

                int Pid = p.id;
                int AId = p.TravelAgencyId;
                int TId = p.TripId;
                String date = p.date;
                String price = p.price;

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
                editTextD.setText(date);
                editTextP.setText(price);

                btCancel.setOnClickListener(v1 -> {
                    dialog.dismiss();                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getPackageTravel());

                });

                btUpdate.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    int Aid = spAId.getSelectedItemPosition();
                    int Tid = spTId.getSelectedItemPosition();
                    String uTextd = editTextD.getText().toString().trim();
                    String uTextp = editTextP.getText().toString().trim();
                    PackageTravel packageTravel = new PackageTravel(Pid,Aid,Tid,uTextd,uTextp);
                    JourneyDatabase.getInstance(context).journeyDao().updatePackageTravel(packageTravel);
                    dataList.clear();
                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getPackageTravel());
                    notifyDataSetChanged();
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageTravel data = dataList.get(holder.getAdapterPosition());
                JourneyDatabase.getInstance(context).journeyDao().deletePackageTravel(data);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPAgencyId, textViewPTripId,textViewPDate, textViewPPrice;
        ImageView btEdit,btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPAgencyId = itemView.findViewById(R.id.text_view_AgencyId);
            textViewPTripId = itemView.findViewById(R.id.text_view_TripId);
            textViewPDate = itemView.findViewById(R.id.text_view_PackageStartDate);
            textViewPPrice = itemView.findViewById(R.id.text_view_PackagePrice);

            btEdit = itemView.findViewById(R.id.bt_editPackage);
            btDelete = itemView.findViewById(R.id.bt_deletePackage);
        }
    }
}
