package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.view.Gravity;
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

    @SuppressLint("SetTextI18n")
    public void bindThisData(HistoryViewObj historyToBind) {
        String tickerName = historyToBind.getTickerName();
        String timestampTime = historyToBind.getTimestamp();
        if (!tickerName.equals("") && !timestampTime.equals("")) {
            ticker.setText(tickerName);
            long millis = Long.parseLong(timestampTime);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date date = new Date(millis);
            timestamp.setText(simpleDateFormat.format(date));
        } else {
            ticker.setText("              No History Found");
            ticker.setGravity(Gravity.CENTER);
            timestamp.setText("");
        }
    }
}
