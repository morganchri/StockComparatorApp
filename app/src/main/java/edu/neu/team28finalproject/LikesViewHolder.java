package edu.neu.team28finalproject;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class LikesViewHolder extends RecyclerView.ViewHolder {

    private TextView ticker;
    private TextView price;
    private TextView change;
    private TextView pctChange;
    public MaterialButton removeLikeButton;

    public LikesViewHolder(@NonNull View likesView) {
        super(likesView);
        this.ticker = likesView.findViewById(R.id.listTicker);
        this.price = likesView.findViewById(R.id.price);
        this.change = likesView.findViewById(R.id.change);
        this.pctChange = likesView.findViewById(R.id.pctChange);
        this.removeLikeButton = likesView.findViewById(R.id.removeLikesButton);
    }

    public void bindThisData(StockViewObj likeToBind) {
        ticker.setText(likeToBind.getTicker());
        price.setText(Double.toString(likeToBind.getCurrent()));
        change.setText("(" + likeToBind.getChange() + ")");
        pctChange.setText(likeToBind.getPctChange() + "%");
        if (likeToBind.getChange() > 0) {
            ticker.setTextColor(Color.rgb(76,153,0));
            price.setTextColor(Color.rgb(76,153,0));
            change.setTextColor(Color.rgb(76,153,0));
            pctChange.setTextColor(Color.rgb(76,153,0));
        } else if (likeToBind.getChange() > 0) {
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
