package edu.neu.team28finalproject;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView ticker;
    public TextView timestamp;

    public HistoryViewHolder(@NonNull View historyView) {
        super(historyView);
        this.ticker = historyView.findViewById(R.id.listTickerInHistory);
        this.timestamp = historyView.findViewById(R.id.timestamp);
    }

    public void bindThisData(HistoryViewObj historyToBind) {
        ticker.setText(historyToBind.getTickerName());
        long millis = Long.valueOf(historyToBind.getTimestamp());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(millis);
        timestamp.setText(simpleDateFormat.format(date));
    }
}
