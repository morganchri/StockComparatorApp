package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.view.View;
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


    public StockViewHolder(@NonNull View itemView) {
        super(itemView);



        RadioGroup rg = (RadioGroup) itemView.findViewById(R.id.RadioGroup);

        RadioButton oneDay = new RadioButton(rg.getContext());
        oneDay.setText("1D");
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg.addView(oneDay, params);

        RadioButton fiveDay = new RadioButton(rg.getContext());
        fiveDay.setText("5D");
        RadioGroup.LayoutParams params2 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg.addView(fiveDay, params2);

        RadioButton oneMonth = new RadioButton(rg.getContext());
        oneMonth.setText("1M");
        RadioGroup.LayoutParams params3 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg.addView(oneMonth, params3);

        RadioButton sixMonth = new RadioButton(rg.getContext());
        sixMonth.setText("6M");
        RadioGroup.LayoutParams params4 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg.addView(sixMonth, params4);

        RadioButton oneYear = new RadioButton(rg.getContext());
        oneYear.setText("1Y");
        RadioGroup.LayoutParams params5 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rg.addView(oneYear, params5);
    }

    public void bindThisData(StockViewObj stockToBind) {



    }
}
