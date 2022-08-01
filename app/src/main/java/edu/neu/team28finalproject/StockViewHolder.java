package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockViewHolder extends RecyclerView.ViewHolder {

    public TextView ticker;
    public TextView cPrice;
    public TextView change;
    public TextView pctChange;
    public ImageButton likeButton;
    public LinearLayout linearLayout;
    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;


    public StockViewHolder(@NonNull View stockView) {
        super(stockView);

        this.ticker = stockView.findViewById(R.id.listTicker);
        this.cPrice = stockView.findViewById(R.id.price);
        this.change = stockView.findViewById(R.id.change);
        this.pctChange = stockView.findViewById(R.id.pctChange);
        this.likeButton = stockView.findViewById(R.id.likeButton);
        linearLayout = stockView.findViewById(R.id.linLayout);
        btn1 = stockView.findViewById(R.id.btn0);
        btn2 = stockView.findViewById(R.id.btn1);
        btn3 = stockView.findViewById(R.id.btn2);
        btn4 = stockView.findViewById(R.id.btn3);
        btn5 = stockView.findViewById(R.id.btn4);
        btn6 = stockView.findViewById(R.id.btn5);

        //for(int i = 0; i < btn.length; i++){
            //btn[i] = (Button) stockView.findViewById(btn_id[i]);
            //btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            //btn[i].setOnClickListener((View.OnClickListener) stockView);
        //}
        //btn_unfocus = btn[0];
    }

    @SuppressLint("SetTextI18n")
    public void bindThisData(StockViewObj stockToBind) {
        ticker.setText(stockToBind.getTicker());
        cPrice.setText(Double.toString((stockToBind.getCurrent())));
        change.setText(Double.toString(stockToBind.getChange()));
        pctChange.setText(Double.toString(stockToBind.getPctChange()) + "%");



    }
}
