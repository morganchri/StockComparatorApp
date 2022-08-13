package edu.neu.team28finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.StockScreener;
import edu.neu.team28finalproject.preferences.UserPreferences;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "Company";
    ArrayList<RecViewObj> recList;
    RecViewAdapter adapter;
    UserPreferencesImpl up;
    HashMap<String, Integer> industryCounts = new HashMap<>();
    List<String> viewedList = new ArrayList<>();
    ControllerImpl controller;
    static List<StockScreener> allStocks;
    List<StockScreener> industryStocks;
    double current;
    double open;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommendations);

        up = new UserPreferencesImpl(this);
        recList = new ArrayList<>();
        if (up.getViewedStocks().size() > 0) {
            viewedList = up.getViewedStocks();
        }


        RecyclerView recyclerView = findViewById(R.id.recRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecViewAdapter(recList, this);
        recyclerView.setAdapter(adapter);

        controller = new ControllerImpl();

        //Setting allStocks list using stockscreener
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RecommendationsActivity.getAllStocks();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.i(TAG,"allStocks: " + allStocks);

        //Going through all stocks viewed and getting counts for each industry
        for (String ticker:viewedList) {
           //Using getAllStocks list to get counts for each industry, may be slow
           for (StockScreener stock:allStocks) {
               if (ticker.equalsIgnoreCase(stock.getSymbol())) {
                   String industry = stock.getIndustry();
                   industryCounts.put(industry, industryCounts.getOrDefault(industry,0) + 1);
               }
           }
        }

        String favIndustry = Collections.max(industryCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

        //Getting a list of stocks that fall under favIndustry
        controller.getStocksByIndustry(favIndustry).enqueue(new Callback<List<StockScreener>>() {
            @Override
            public void onResponse(Call<List<StockScreener>> call, Response<List<StockScreener>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    industryStocks = response.body();
                    industryStocks.sort(Comparator.comparing(StockScreener::getVolume).reversed());
                    Log.i(TAG,"getStocksByIndustrySuccessful: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StockScreener>> call, Throwable t) {
                Log.i(TAG, "getStocksByIndustryFailure: " + t);
            }
        });

        //Get top 10 stocks with highest volume from industryStocks list
        List<StockScreener> topTen = industryStocks.stream().limit(10).collect(Collectors.toList());

        if (topTen.size() > 0) {
            for (int i = 0; i < topTen.size(); i++) {
                String ticker = topTen.get(i).getSymbol();
                //Get price info using getQuote
                controller.getQuote(ticker).enqueue(new Callback<Quote>() {
                    @Override
                    public void onResponse(Call<Quote> call, Response<Quote> response) {
                        assert response.body() != null;
                        if (response.body().getTimestamp() > 0) {
                            current = response.body().getCurrentPrice();
                             open = response.body().getOpenPrice();
                             Log.i(TAG,"getQuoteSuccessful: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Quote> call, Throwable t) {
                        Log.i(TAG,"getQuoteFailure: " + t);
                    }
                });
                String industry = topTen.get(i).getIndustry();
                double marketCap = topTen.get(i).getMarketCap();
                double volume = topTen.get(i).getVolume();
                double dividends = topTen.get(i).getLastAnnualDividend();
                 RecViewObj rec = new RecViewObj(ticker, current, open, industry, marketCap, volume, dividends);
                 recList.add(rec);
                 adapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * Helper function to get list of all stocks
     */
    public static void getAllStocks() {
        ControllerImpl controller = new ControllerImpl();
        controller.getAllStockInfo().enqueue(new Callback<List<StockScreener>>() {
            @Override
            public void onResponse(Call<List<StockScreener>> call, Response<List<StockScreener>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    allStocks = new ArrayList<>(response.body());
                    Log.i(TAG, "getAllStockInfoSuccessful: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StockScreener>> call, Throwable t) {
                Log.i(TAG, "getAllStockInfoFailure: " + t);
            }
        });
    }

}
