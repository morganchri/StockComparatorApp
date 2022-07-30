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

    View view;
    TextView ticker;
    TextView cPrice;
    TextView change;
    TextView pctChange;
    ImageButton likeButton;
    LinearLayout linearLayout;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;

    //private Button[] btn = new Button[4];
    //private Button btn_unfocus;
    //private int[] btn_id = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5};


    public StockViewHolder(@NonNull View stockView) {
        super(stockView);

        this.ticker = stockView.findViewById(R.id.url);
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
        view = stockView;

        //for(int i = 0; i < btn.length; i++){
            //btn[i] = (Button) stockView.findViewById(btn_id[i]);
            //btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            //btn[i].setOnClickListener((View.OnClickListener) stockView);
        //}
        //btn_unfocus = btn[0];
    }

    @SuppressLint("SetTextI18n")
    public void bindThisData(StockViewObj stockToBind) {

        //ticker.setText(stockToBind.getTicker());
        ticker.setText("AAPL");
        cPrice.setText(Double.toString((stockToBind.getCurrent())));
        change.setText(Double.toString(stockToBind.getChange()));
        pctChange.setText(Double.toString(stockToBind.getPctChange()) + "%");



    }
}
