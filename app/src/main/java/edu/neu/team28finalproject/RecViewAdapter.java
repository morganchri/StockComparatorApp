package edu.neu.team28finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewHolder> {

    private final ArrayList<RecViewObj> recList;
    private final Context context;

    public RecViewAdapter(ArrayList<RecViewObj> recList, Context context) {
        this.recList = recList;
        this.context = context;

    }


    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.recommendations_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.bindThisData(recList.get(position));
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }
}
