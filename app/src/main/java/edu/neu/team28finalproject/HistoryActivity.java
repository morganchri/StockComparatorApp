package edu.neu.team28finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;

public class HistoryActivity extends AppCompatActivity {

    List<HistoryViewObj> histories;
    RecyclerView historyRecycler;
    HistoryAdapter ha;
    TextView header;
    UserPreferencesImpl up;
    ControllerImpl controller;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        header = findViewById(R.id.historyHeader);
        header.setGravity(View.TEXT_ALIGNMENT_CENTER);
        histories = new ArrayList<>();
        historyRecycler = findViewById(R.id.historyRecycler);
        historyRecycler.setHasFixedSize(true);
        LinearLayoutManager historyLinLayout = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(historyLinLayout);
        ha = new HistoryAdapter(histories, this);
        historyRecycler.setAdapter(ha);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        up = new UserPreferencesImpl(this);
        controller = new ControllerImpl();
        if (up.getViewedStocks().size() > 0) {
            int viewedStocksSize = up.getViewedStocks().size();
            for (int i = 0; i < viewedStocksSize; i++) {
                String[] tickerPlusTimestamp = up.getViewedStocks().get(i).split("-");
                String ticker = tickerPlusTimestamp[0];
                String timestamp = tickerPlusTimestamp[1];
                HistoryViewObj historyViewObj = new HistoryViewObj(ticker, timestamp);
                histories.add(historyViewObj);
            }
            histories.sort(Comparator.comparing(HistoryViewObj::getTimestamp).reversed());
            ha.notifyDataSetChanged();
        } else {
            histories.add(new HistoryViewObj("            No History Found",
                    ""));
        }
    }
}