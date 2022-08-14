package edu.neu.team28finalproject;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewHolder extends RecyclerView.ViewHolder {

        public TextView ticker;
        public TextView price;
        public TextView delta_price;
        public TextView delta_percent;
        public ImageButton like_button;
        public ImageButton add_button;
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
        this.add_button = itemView.findViewById(R.id.add_button);
        this.industry = itemView.findViewById(R.id.industry);
        this.dividends = itemView.findViewById(R.id.dividends);
        this.market_cap = itemView.findViewById(R.id.market_cap);
        this.volume = itemView.findViewById(R.id.volume);

    }

    public void bindThisData(RecViewObj recToBind) {
        ticker.setText(recToBind.getTicker());
        price.setText((int) recToBind.getCurrent());
        delta_price.setText((int) recToBind.getChange());
        delta_percent.setText((int) recToBind.getPctChange());

        //Figure out the like and add button drawables
        //like_button.setImageDrawable();
        //add_button.setImageDrawable();

        industry.setText(recToBind.getIndustry());
        dividends.setText((int) recToBind.getDividends());
        market_cap.setText((int) recToBind.getMarketCap());
        volume.setText((int) recToBind.getVolume());

    }
}
