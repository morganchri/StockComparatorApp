package edu.neu.team28finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder>{

    private final List<HistoryViewObj> historyViewObjs;
    private final Context context;

    public HistoryAdapter(List<HistoryViewObj> historyViewObjList, Context context) {
        this.historyViewObjs = historyViewObjList;

        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_layout,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bindThisData(historyViewObjs.get(position));
    }

    @Override
    public int getItemCount() {
        return historyViewObjs.size();
    }
}
