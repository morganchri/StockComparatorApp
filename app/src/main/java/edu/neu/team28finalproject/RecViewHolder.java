package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewHolder extends RecyclerView.ViewHolder {

        public TextView ticker;
        public TextView price;
        public TextView delta_price;
        public TextView delta_percent;
        public RadioButton like_button;
        public TextView industry;
        public TextView dividends;
        public TextView market_cap;
        public TextView volume;


    public RecViewHolder(@NonNull View itemView) {
        super(itemView);

        //May be confused with other ticker reference from other views. tbd
        this.ticker = itemView.findViewById(R.id.ticker);
        this.price = itemView.findViewById(R.id.dollar_price);
        this.delta_percent = itemView.findViewById(R.id.delta_percent);
        this.delta_price = itemView.findViewById(R.id.delta_price);
        this.like_button = itemView.findViewById(R.id.like_button);
        this.industry = itemView.findViewById(R.id.industryValue);
        this.dividends = itemView.findViewById(R.id.dividendsValue);
        this.market_cap = itemView.findViewById(R.id.market_capValue);
        this.volume = itemView.findViewById(R.id.volumeValue);

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void bindThisData(RecViewObj recToBind) {
        ticker.setText(recToBind.getTicker());
        price.setText("$" + String.valueOf(recToBind.getCurrent()));
        delta_price.setText("(" + String.format("%.2f", recToBind.getChange()) + ")");
        delta_percent.setText(String.format("%.2f", recToBind.getPctChange()) + "%");
        if (recToBind.getChange() > 0) {
            delta_price.setTextColor(Color.rgb(76,153,0));
            delta_percent.setTextColor(Color.rgb(76,153,0));
        } else {
            delta_price.setTextColor(Color.RED);
            delta_percent.setTextColor(Color.RED);
        }

        //Figure out the like and add button drawables
        //like_button.setImageDrawable();

        industry.setText(String.valueOf(recToBind.getIndustry()));
        dividends.setText(String.valueOf(recToBind.getDividends()));
        market_cap.setText(String.format("%.0f", recToBind.getMarketCap()));
        volume.setText(String.format("%.0f", recToBind.getVolume()));

    }
}
