package edu.neu.team28finalproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockListHolder extends RecyclerView.ViewHolder{

    TextView ticker;
    TextView sector;

    public StockListHolder(@NonNull View listView) {
        super(listView);
        this.ticker = listView.findViewById(R.id.listTicker);
        this.sector = listView.findViewById(R.id.tickerSector);
    }

    public void bindThisData(StockListObj stockToBind) {
        ticker.setText(stockToBind.getTicker());
        sector.setText(stockToBind.getSector());

    }
}
