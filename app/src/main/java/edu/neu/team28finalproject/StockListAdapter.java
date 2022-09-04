package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListHolder>{

    private final List<StockListObj> stocks;
    private final Context context;

    public StockListAdapter(List<StockListObj> stocks, Context context) {
        this.stocks = stocks;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public StockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StockListHolder(LayoutInflater.from(context).inflate(R.layout.stock_list_layout,
                null));
    }

    @Override
    public void onBindViewHolder(@NonNull StockListHolder holder, int position) {
        holder.bindThisData(stocks.get(position));
        holder.ticker.setOnClickListener(v -> {
            Intent allStocksIntent = ((AllStocksActivity) context).getIntent();
            String jsonString = allStocksIntent.getStringExtra("stockList");
            Gson gson = new Gson();
            Type stockListType = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> stockList = gson.fromJson(jsonString, stockListType);
            String stockInput = stocks.get(position).getTicker();
            if (!stockList.contains(stockInput.toUpperCase())) {
                stockList.add(stockInput);
            }
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            String stockString = gson.toJson(stockList);
            intent.putExtra("Ticker", stockString);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
