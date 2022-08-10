package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockViewHolder extends RecyclerView.ViewHolder {

    public TextView ticker;
    public TextView cPrice;
    public TextView change;
    public TextView pctChange;
    public ImageButton likeButton;
    public ImageButton deleteButton;


    public StockViewHolder(@NonNull View stockView) {
        super(stockView);
        this.ticker = stockView.findViewById(R.id.listTicker);
        this.cPrice = stockView.findViewById(R.id.price);
        this.change = stockView.findViewById(R.id.change);
        this.pctChange = stockView.findViewById(R.id.pctChange);
        this.likeButton = stockView.findViewById(R.id.likeButton);
        this.deleteButton = stockView.findViewById(R.id.deleteButton);
    }

    @SuppressLint("SetTextI18n")
    public void bindThisData(StockViewObj stockToBind) {
        ticker.setText(stockToBind.getTicker());
        cPrice.setText(Double.toString((stockToBind.getCurrent())));
        change.setText("(" + stockToBind.getChange() + ")");
        pctChange.setText(stockToBind.getPctChange() + "%");
        if (stockToBind.getChange() > 0) {
            change.setTextColor(Color.rgb(76,153,0));
            pctChange.setTextColor(Color.rgb(76,153,0));
        } else {
            change.setTextColor(Color.RED);
            pctChange.setTextColor(Color.RED);
        }
    }


}
