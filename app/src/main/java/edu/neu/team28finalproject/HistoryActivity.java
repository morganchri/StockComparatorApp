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
//        up = new UserPreferencesImpl(this);
//        controller = new ControllerImpl();
//        if (up.getLikedStocks().size() > 0) {
//            for (int i = 0; i < up.getLikedStocks().size(); i++) {
//                String ticker = up.getLikedStocks().get(i);
//                System.out.println(ticker);
//                controller.getQuote(ticker)
//                        .enqueue(new Callback<Quote>() {
//                            @Override
//                            public void onResponse(@NonNull Call<Quote> call,
//                                                   @NonNull Response<Quote> response) {
//                                if (response.isSuccessful()) {
//                                    assert response.body() != null;
//                                    if (response.body().getTimestamp() > 0) {
//                                        double cPrice = response.body().getCurrentPrice();
//                                        double oPrice = response.body().getOpenPrice();
//                                        StockViewObj newStock = new StockViewObj(ticker, cPrice,
//                                                oPrice);
//                                        likes.add(newStock);
//                                        la.notifyDataSetChanged();
//                                        Log.i(TAG, "getQuoteOnResponse: "
//                                                + response.body());
//                                    }
//                                } else {
//                                    try {
//                                        assert response.errorBody() != null;
//                                        Log.i(TAG, "getQuoteOnResponseNotSuccessful: " +
//                                                response.errorBody().string());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            @Override
//                            public void onFailure(@NonNull Call<Quote> call,
//                                                  @NonNull Throwable t) {
//                                Log.i(TAG, "getQuoteOnFailure: " + t);
//                            }
//                        });
//            }
//        } else {
//            likes.add(new StockViewObj("No Likes", 0.00, 0.00));
//        }
    }
}