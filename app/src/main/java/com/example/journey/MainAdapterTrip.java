package com.example.journey;

import static java.lang.Integer.parseInt;

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

public class MainAdapterTrip extends RecyclerView.Adapter<MainAdapterTrip.ViewHolder> {

    private List<Trip> dataList;
    private Activity context;
    private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapterTrip(Activity context, List<Trip> dataList){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_trip,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Trip data = dataList.get(position);
        database = JourneyDatabase.getInstance(context);
        holder.textViewTripCity.setText(data.city);
        holder.textViewTripCountry.setText(data.country);
        holder.textViewDuration.setText(data.duration);
        holder.textViewTripType.setText(data.type);
        holder.textViewCoordinates.setText(data.coordinates);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Trip t= dataList.get(holder.getAdapterPosition());

                int Tid = t.id;
                String tCity = t.city;
                String tCountry = t.country;
                String tDuration = t.duration;
                String tType = t.type;
                String tCoordinates = t.coordinates;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_trip);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                EditText editTextC = dialog.findViewById(R.id.edit_TextTCity);
                EditText editTextCo = dialog.findViewById(R.id.edit_TextTCountry);
                EditText editTextD = dialog.findViewById(R.id.edit_TextTDuration);
                EditText editTextT = dialog.findViewById(R.id.edit_TextTType);
                EditText editTextCoo = dialog.findViewById(R.id.edit_TextTCoordinates);
                Button btUpdate = dialog.findViewById(R.id.bt_update);
                Button btCancel = dialog.findViewById(R.id.bt_cancel);
                editTextC.setText(tCity);
                editTextCo.setText(tCountry);
                editTextD.setText(tDuration);
                editTextT.setText(tType);
                editTextCoo.setText(tCoordinates);

                btCancel.setOnClickListener(v1 -> {
                    dialog.dismiss();                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getTrip());

                });

                btUpdate.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    String uTextC = editTextC.getText().toString().trim();
                    String uTextCo = editTextCo.getText().toString().trim();
                    String uTextD = editTextD.getText().toString().trim();
                    String uTextT = editTextT.getText().toString().trim();
                    String uTextCoo = editTextCoo.getText().toString().trim();
                    Trip trip = new Trip(Tid,uTextC,uTextCo,uTextD,uTextT,uTextCoo);
                    JourneyDatabase.getInstance(context).journeyDao().updateTrip(trip);
                    dataList.clear();
                    dataList.addAll(JourneyDatabase.getInstance(context).journeyDao().getTrip());
                    notifyDataSetChanged();
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip data = dataList.get(holder.getAdapterPosition());
                JourneyDatabase.getInstance(context).journeyDao().deleteTrip(data);
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

        TextView textViewTripCity, textViewTripCountry,textViewTripType, textViewDuration,textViewCoordinates;
        ImageView btEdit,btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTripCity = itemView.findViewById(R.id.text_view_TripCity);
            textViewTripCountry = itemView.findViewById(R.id.text_view_TripCountry);
            textViewDuration = itemView.findViewById(R.id.text_view_TripDuration);
            textViewTripType = itemView.findViewById(R.id.text_view_TripType);
            textViewCoordinates = itemView.findViewById(R.id.text_view_TripCoordinates);

            btEdit = itemView.findViewById(R.id.bt_editTrip);
            btDelete = itemView.findViewById(R.id.bt_deleteTrip);
        }
    }
}
