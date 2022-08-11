package edu.neu.team28finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.BiggestMovers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movers extends AppCompatActivity {

    ControllerImpl controller;
    TextView[] tickers = new TextView[12];
    TextView[] prices = new TextView[12];
    TextView[] changes = new TextView[12];
    TextView[] pctChanges = new TextView[12];
    int[] tickersIDs = {R.id.up1_ticker, R.id.up2_ticker, R.id.up3_ticker,
            R.id.up4_ticker, R.id.up5_ticker, R.id.up6_ticker,
            R.id.down1_ticker, R.id.down2_ticker, R.id.down3_ticker,
            R.id.down4_ticker, R.id.down5_ticker, R.id.down6_ticker};
    int[] priceIDs = {R.id.up1_price, R.id.up2_price, R.id.up3_price,
            R.id.up4_price, R.id.up5_price, R.id.up6_price,
            R.id.down1_price, R.id.down2_price, R.id.down3_price,
            R.id.down4_price, R.id.down5_price, R.id.down6_price};
    int[] changeIDs = {R.id.up1_delta_price, R.id.up2_delta_price, R.id.up3_delta_price,
            R.id.up4_delta_price, R.id.up5_delta_price, R.id.up6_delta_price,
            R.id.down1_delta_price, R.id.down2_delta_price, R.id.down3_delta_price,
            R.id.down4_delta_price, R.id.down5_delta_price, R.id.down6_delta_price};
    int[] pctChangeIDs = {R.id.up1_delta_percent, R.id.up2_delta_percent, R.id.up3_delta_percent,
            R.id.up4_delta_percent, R.id.up5_delta_percent, R.id.up6_delta_percent,
            R.id.down1_delta_percent, R.id.down2_delta_percent, R.id.down3_delta_percent,
            R.id.down4_delta_percent, R.id.down5_delta_percent, R.id.down6_delta_percent};
    private static final String TAG = "Movers";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movers_layout);
        for(int i = 0; i < tickersIDs.length; i++) {
            tickers[i] = findViewById(tickersIDs[i]);
        }
        for(int i = 0; i < priceIDs.length; i++) {
            prices[i] = findViewById(priceIDs[i]);
        }
        for(int i = 0; i < changeIDs.length; i++) {
            changes[i] = findViewById(changeIDs[i]);
        }
        for(int i = 0; i < pctChangeIDs.length; i++) {
            pctChanges[i] = findViewById(pctChangeIDs[i]);
        }
        controller = new ControllerImpl();
        controller.getMostGainers().enqueue(new Callback<List<BiggestMovers>>() {
            @Override
            public void onResponse(Call<List<BiggestMovers>> call, Response<List<BiggestMovers>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<BiggestMovers> gainers = new ArrayList<>(response.body());
                    for(int i = 0; i < 6; i++) {
                        tickers[i].setText(gainers.get(i).getSymbol());
                        tickers[i].setTextColor(Color.rgb(76,153,0));
                    }
                    for(int i = 0; i < 6; i++) {
                        prices[i].setText(Double.toString(Math.round(gainers.get(i).getPrice()*100.00)/100.00));
                        prices[i].setTextColor(Color.rgb(76,153,0));
                    }
                    for(int i = 0; i < 6; i++) {
                        changes[i].setText("+" + Math.round(gainers.get(i).getChange()*100.00)/100.00);
                        changes[i].setTextColor(Color.rgb(76,153,0));
                    }
                    for(int i = 0; i < 6; i++) {
                        pctChanges[i].setText("+" + Math.round(gainers.get(i).getPercentageChange()*100.00)/100.00 + "%");
                        pctChanges[i].setTextColor(Color.rgb(76,153,0));
                    }
                    Log.i(TAG, "getBiggestMoversOnResponse: " + response.body());
                } else {
                    try {
                        Log.i(TAG, "getBiggestMoversNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BiggestMovers>> call, Throwable t) {
                Log.i(TAG, "getBiggestMoversOnFailure: " + t);
            }
        });
        controller.getMostLosers().enqueue(new Callback<List<BiggestMovers>>() {
            @Override
            public void onResponse(Call<List<BiggestMovers>> call, Response<List<BiggestMovers>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<BiggestMovers> losers = new ArrayList<>(response.body());
                    for(int i = 0; i < 6; i++) {
                        tickers[i + 6].setText(losers.get(i).getSymbol());
                        tickers[i + 6].setTextColor(Color.RED);
                    }
                    for(int i = 0; i < 6; i++) {
                        prices[i + 6].setText(Double.toString(Math.round(losers.get(i).getPrice()*100.00)/100.00));
                        prices[i + 6].setTextColor(Color.RED);
                    }
                    for(int i = 0; i < 6; i++) {
                        changes[i + 6].setText(Double.toString(Math.round(losers.get(i).getChange()*100.00)/100.00));
                        changes[i + 6].setTextColor(Color.RED);
                    }
                    for(int i = 0; i < 6; i++) {
                        pctChanges[i + 6].setText(Math.round(losers.get(i).getPercentageChange()*100.00)/100.00 + "%");
                        pctChanges[i + 6].setTextColor(Color.RED);
                    }
                    Log.i(TAG, "getBiggestMoversOnResponse: " + response.body());
                } else {
                    try {
                        Log.i(TAG, "getBiggestMoversNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BiggestMovers>> call, Throwable t) {
                Log.i(TAG, "getBiggestMoversOnFailure: " + t);
            }
        });
    }
}
