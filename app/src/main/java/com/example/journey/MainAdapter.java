package com.example.journey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<TravelAgency> dataList;
    private Activity context;
    private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapter(Activity context, List<TravelAgency> dataList){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TravelAgency data = dataList.get(position);
        database = JourneyDatabase.getInstance(context);
        holder.textViewAgencyName.setText(data.agency_name);
        holder.textViewAgencyAddress.setText(data.address);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                TravelAgency t= dataList.get(holder.getAdapterPosition());

                int aID = t.id;
                String aName = t.agency_name;
                String aAddress = t.address;


                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_TextAname);
                EditText editTextA = dialog.findViewById(R.id.edit_TextAaddress);
                Button btUpdate = dialog.findViewById(R.id.bt_update);
                btUpdate.setText("Update");
                Button btCancel = dialog.findViewById(R.id.bt_cancel);
                btCancel.setText("Cancel");
                editText.setText(aName);
                editTextA.setText(aAddress);

                btCancel.setOnClickListener(v1 -> {
                    dialog.dismiss();                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getTravelAgency());

                });

                btUpdate.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    String uText = editText.getText().toString().trim();
                    String uTextA = editTextA.getText().toString().trim();
                    TravelAgency travelAgency = new TravelAgency(aID,uText,uTextA);
                    JourneyDatabase.getInstance(context).journeyDao().updateTravelAgency(travelAgency);
                    dataList.clear();
                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getTravelAgency());
                    notifyDataSetChanged();
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TravelAgency data = dataList.get(holder.getAdapterPosition());
                JourneyDatabase.getInstance(context).journeyDao().deleteTravelAgency(data);
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

        TextView textViewAgencyName, textViewAgencyAddress;
        ImageView btEdit,btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAgencyName = itemView.findViewById(R.id.text_view_AgencyName);
            textViewAgencyAddress = itemView.findViewById(R.id.text_view_AgencyAddress);

            btEdit = itemView.findViewById(R.id.bt_editAgency);
            btDelete = itemView.findViewById(R.id.bt_deleteAgency);
        }
    }
}
