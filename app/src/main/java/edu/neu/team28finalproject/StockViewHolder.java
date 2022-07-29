package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StockViewHolder extends RecyclerView.ViewHolder {

    GraphView graphView;
    TextView ticker;
    TextView cPrice;
    TextView change;
    TextView pctChange;
    ImageButton likeButton;

    private Button[] btn = new Button[4];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5};


    public StockViewHolder(@NonNull View stockView) {
        super(stockView);

        this.graphView = stockView.findViewById(R.id.idGraphView);
        this.ticker = stockView.findViewById(R.id.url);
        this.cPrice = stockView.findViewById(R.id.price);
        this.change = stockView.findViewById(R.id.change);
        this.pctChange = stockView.findViewById(R.id.pctChange);
        this.likeButton = stockView.findViewById(R.id.likeButton);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) stockView.findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btn[i].setOnClickListener((View.OnClickListener) stockView);
        }
        btn_unfocus = btn[0];
    }

    public void bindThisData(StockViewObj stockToBind) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        graphView.addSeries(series);
        ticker.setText(stockToBind.getTicker());
        cPrice.setText(Double.toString((stockToBind.getCurrent())));
        change.setText(Double.toString(stockToBind.getChange()));
        pctChange.setText(Double.toString(stockToBind.getPctChange()) + "%");



    }
}
