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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapterSearch  {

    private List<String> dataList;
    private Activity context;
    private JourneyDatabase database;

    @SuppressLint("NotifyDataSetChanged")
    public MainAdapterSearch(Activity context, List<String> dataList){
        this.context = context;
        this.dataList = dataList;
        //notifyDataSetChanged();
    }
    //public void reloadMainAdapter(){
    //    notifyDataSetChanged();
    //}

}
