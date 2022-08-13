package edu.neu.team28finalproject;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        timestamp.setText(historyToBind.getTimestamp());
    }
}
