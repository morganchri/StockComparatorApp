package edu.neu.team28finalproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LikesViewHolder extends RecyclerView.ViewHolder {

    private TextView ticker;
    private TextView price;
    private TextView change;
    private TextView pctChange;

    public LikesViewHolder(@NonNull View likesView) {
        super(likesView);
        this.ticker = likesView.findViewById(R.id.listTickerLikes);
        this.price = likesView.findViewById(R.id.priceLikes);
        this.change = likesView.findViewById(R.id.changeLikes);
        this.pctChange = likesView.findViewById(R.id.pctChangeLikes);

    }

    public void bindThisData(StockViewObj likeToBind) {
        ticker.setText(likeToBind.getTicker());
        price.setText(Double.toString(Math.round(likeToBind.getCurrent()/100.00)/100.00));
        change.setText(Double.toString(Math.round(likeToBind.getChange()/100.00)/100.00));
        pctChange.setText(Double.toString(Math.round(likeToBind.getPctChange()/100.00)/100.00));
    }
}
