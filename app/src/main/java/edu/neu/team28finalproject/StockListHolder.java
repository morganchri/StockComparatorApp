package edu.neu.team28finalproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockListHolder extends RecyclerView.ViewHolder{

    TextView ticker;
    TextView name;
    TextView sector;

    public StockListHolder(@NonNull View listView) {
        super(listView);
        this.ticker = listView.findViewById(R.id.listTickerLikes);
        this.name = listView.findViewById(R.id.name);
        this.sector = listView.findViewById(R.id.tickerSector);
    }

    public void bindThisData(StockListObj stockToBind) {
        ticker.setText(stockToBind.getTicker());
        name.setText(stockToBind.getName());
        sector.setText(stockToBind.getSector());

    }
}
