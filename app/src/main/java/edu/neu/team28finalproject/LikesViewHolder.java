package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LikesViewHolder extends RecyclerView.ViewHolder {

    private final TextView ticker;
    private final TextView price;
    private final TextView change;
    private final TextView pctChange;
    public Button removeLikeButton;

    public LikesViewHolder(@NonNull View likesView, LikesAdapter.OnItemClickListener listener) {
        super(likesView);
        this.ticker = likesView.findViewById(R.id.listTicker);
        this.price = likesView.findViewById(R.id.price);
        this.change = likesView.findViewById(R.id.change);
        this.pctChange = likesView.findViewById(R.id.pctChange);
        this.removeLikeButton = likesView.findViewById(R.id.removeLikesButton);
        removeLikeButton.setOnClickListener(view
                -> listener.onItemClick(getAbsoluteAdapterPosition()));
    }

    @SuppressLint("SetTextI18n")
    public void bindThisData(StockViewObj likeToBind) {
        if (likeToBind.getTicker().equals("No Liked Stocks")) {
            ticker.setText("                " + likeToBind.getTicker());
            price.setText("");
            change.setText("");
            pctChange.setText("");
            removeLikeButton.setTextColor(Integer.parseInt("F3F2EF", 16)+0xFF000000);
        } else {
            ticker.setText(likeToBind.getTicker());
            price.setText(Double.toString(likeToBind.getCurrent()));
            change.setText("(" + likeToBind.getChange() + ")");
            pctChange.setText(likeToBind.getPctChange() + "%");
            if (likeToBind.getChange() > 0) {
                ticker.setTextColor(Color.rgb(76, 153, 0));
                price.setTextColor(Color.rgb(76, 153, 0));
                change.setTextColor(Color.rgb(76, 153, 0));
                pctChange.setTextColor(Color.rgb(76, 153, 0));
            } else if (likeToBind.getChange() < 0) {
                ticker.setTextColor(Color.RED);
                price.setTextColor(Color.RED);
                change.setTextColor(Color.RED);
                pctChange.setTextColor(Color.RED);
            } else {
                ticker.setTextColor(Color.BLACK);
                price.setTextColor(Color.BLACK);
                change.setTextColor(Color.BLACK);
                pctChange.setTextColor(Color.BLACK);
            }
        }
    }
}
