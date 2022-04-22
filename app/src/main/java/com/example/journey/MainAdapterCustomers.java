package com.example.journey;


import static com.example.journey.MainActivity.db;
import static java.lang.Integer.parseInt;
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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterCustomers extends RecyclerView.Adapter<MainAdapterCustomers.ViewHolder> {

    private List<Customers> dataList;
    private Activity context;
    private List<String> idList;
    List<Integer> packetIdList=new ArrayList<>();

    //private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapterCustomers(Activity context, List<Customers> dataList, List<String> idList) {
        this.context = context;
        this.dataList = dataList;
        this.idList = idList;
        notifyDataSetChanged();
    }

    public void reloadMainAdapter() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_customer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Customers data = dataList.get(position);
        String hiddenid = idList.get(position);
        holder.textViewCName.setText(data.getName());
        holder.textViewCHotel.setText(data.getHotel());
        holder.textViewCPId.setText(String.valueOf(data.getPackagetravelid()));
        holder.textViewHiddenId.setText(hiddenid);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Customers c = dataList.get(holder.getAdapterPosition());
                String Hid = idList.get(holder.getAdapterPosition());

                String name = c.getName();
                String hotel = c.getHotel();
                int pid = c.getPackagetravelid();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_customers);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                Spinner spPId = dialog.findViewById(R.id.SpinnerPid);
                EditText editTextN = dialog.findViewById(R.id.edit_TextName);
                EditText editTextH = dialog.findViewById(R.id.edit_TextHotel);
                Button btUpdate = dialog.findViewById(R.id.bt_update);
                Button btCancel = dialog.findViewById(R.id.bt_cancel);

                List<String> spList;
                spList = MainActivity.journeyDatabase.journeyDao().getPackageDetails();
                packetIdList = MainActivity.journeyDatabase.journeyDao().getSelectedPackageId();
                if (!spList.isEmpty()) {
                    ArrayAdapter<String> spAdapterA = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spList);
                    spAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPId.setAdapter(spAdapterA);
                    spPId.setSelection(packetIdList.indexOf(pid));
                }
                editTextN.setText(name);
                editTextH.setText(hotel);
                btCancel.setOnClickListener(v1 -> {
                    dialog.dismiss();

                });

                btUpdate.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    int Pid = packetIdList.get(spPId.getSelectedItemPosition());
                    String uTextn = editTextN.getText().toString().trim();
                    String uTexth = editTextH.getText().toString().trim();
                    Customers customers = new Customers(uTextn, uTexth, Pid);

                    try {
                        MainActivity.db.
                                collection("Customers").
                                document(Hid).
                                set(customers).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Update Success.", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "add operation failed.", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } catch (Exception e) {
                        String message = e.getMessage();
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                    dataList.clear();
                    db.collection("Customers")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String pid = document.getLong("packagetravelid").toString();
                                            Customers customer = new Customers(document.getString("name"), document.getString("hotel"), parseInt(pid));
                                            dataList.add(customer);
                                            reloadMainAdapter();
                                        }

                                    }
                                }
                            });

                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                db.collection("Customers").
                        document(idList.get(holder.getAdapterPosition())).
                        delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Customer has been deleted from Database.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Fail to delete the Customer. ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCName, textViewCHotel, textViewCPId, textViewHiddenId;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCName = itemView.findViewById(R.id.text_view_customer_name);
            textViewCHotel = itemView.findViewById(R.id.text_view_hotel);
            textViewCPId = itemView.findViewById(R.id.text_view_customer_package);
            textViewHiddenId = itemView.findViewById(R.id.text_view_hidden_id);

            btEdit = itemView.findViewById(R.id.bt_edit_customer);
            btDelete = itemView.findViewById(R.id.bt_delete_customer);
        }
    }
}
