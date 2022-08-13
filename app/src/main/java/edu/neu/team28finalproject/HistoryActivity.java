package edu.neu.team28finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    List<HistoryViewObj> histories;
    RecyclerView historyRecycler;
    HistoryAdapter ha;
    TextView header;
    UserPreferencesImpl up;
    ControllerImpl controller;
    List<String> stockNamesList = new ArrayList<>();
    List<String> timestampsList = new ArrayList<>();
    private static final String TAG = "History";

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
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            stockNamesList = bundle.getStringArrayList("stockNames");
//            timestampsList = bundle.getStringArrayList("timestamps");
//        int len = stockNamesList.size();
//        for(int i = len-1; i >= 0; i--) {
//            HistoryViewObj historyViewObj = new HistoryViewObj(stockNamesList.get(i), timestampsList.get(i));
//            histories.add(historyViewObj);
//            ha.notifyDataSetChanged();
//        }
        up = new UserPreferencesImpl(this);
        controller = new ControllerImpl();
        if (up.getViewedStocks().size() > 0) {
            for (int i = 0; i < up.getViewedStocks().size(); i++) {
                String ticker = up.getViewedStocks().get(i);
                System.out.println(ticker);
                HistoryViewObj historyViewObj = new HistoryViewObj(ticker, String.valueOf(System.currentTimeMillis()));
                histories.add(historyViewObj);
                ha.notifyDataSetChanged();
            }
        } else {
            histories.add(new HistoryViewObj("No History", ""));
        }
    }
}