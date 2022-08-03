package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockViewAdapter extends RecyclerView.Adapter<StockViewHolder>{

    private final List<StockViewObj> stocks;
    private final Context context;

    public StockViewAdapter(List<StockViewObj> stocks, Context context) {
        this.stocks = stocks;
        this.context = context;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StockViewHolder(LayoutInflater.from(context).inflate(R.layout.stockviewlayout,
                null));
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(stocks.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stocks.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }


}
