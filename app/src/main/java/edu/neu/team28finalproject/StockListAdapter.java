package edu.neu.team28finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListHolder>{

    private final List<StockListObj> stocks;
    private final Context context;

    public StockListAdapter(List<StockListObj> stocks, Context context) {
        this.stocks = stocks;
        this.context = context;
    }

    @NonNull
    @Override
    public StockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StockListHolder(LayoutInflater.from(context).inflate(R.layout.stock_list_layout,
                null));
    }

    @Override
    public void onBindViewHolder(@NonNull StockListHolder holder, int position) {
        holder.bindThisData(stocks.get(position));
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
