package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.StockScreener;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "Company";
    ProgressBar loadingCircle;
    TextView loadingText;
    static List<StockScreener> allStocks;
    private final ControllerImpl controller = new ControllerImpl();
    ArrayList<RecViewObj> recList;
    RecViewAdapter adapter;
    UserPreferencesImpl up;
    HashMap<String, Integer> industryCounts = new HashMap<>();
    List<String> viewedList = new ArrayList<>();
    List<StockScreener> industryStocks;
    double current;
    double open;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommendations);

        loadingCircle = findViewById(R.id.loadingCircle);
        loadingText = findViewById(R.id.loadingText);

        up = new UserPreferencesImpl(this);
        recList = new ArrayList<>();
        if (up.getViewedStocks().size() > 0) {
            viewedList = up.getViewedStocks();
            Log.i(TAG, "viewedList: " + viewedList);
        }


        RecyclerView recyclerView = findViewById(R.id.recRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecViewAdapter(recList, this);
        recyclerView.setAdapter(adapter);

        //Setting allStocks list using stockscreener
        controller.getAllStockInfo().enqueue(new Callback<List<StockScreener>>() {
            @Override
            public void onResponse(@NonNull Call<List<StockScreener>> call,
                                   @NonNull Response<List<StockScreener>> response)
                    throws NullPointerException{
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    allStocks = new ArrayList<>(response.body());
                    Log.i(TAG, "getAllStockInfoSuccessful: " + response.body());

                    Log.i(TAG,"allStocks: " + allStocks);

                    //Going through all stocks viewed and getting counts for each industry
                    for (String ticker:viewedList) {
                        //Using getAllStocks list to get counts for each industry, may be slow
                        for (StockScreener stock:allStocks) {
                            String t = ticker.split("-")[0];
                            if (t.equalsIgnoreCase(stock.getSymbol())) {
                                String industry = stock.getIndustry();
                                try {
                                    industryCounts.put(industry,
                                            industryCounts.getOrDefault(industry,0) + 1);

                                } catch (NullPointerException e) {
                                    Toast.makeText(RecommendationsActivity.this,
                                            "Failure",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    Log.w(TAG, "onCreate: " + industryCounts);
                    String favIndustry = "";
                    if (industryCounts.size() > 0)
                        favIndustry = Collections.max(industryCounts.entrySet(),
                                Map.Entry.comparingByValue()).getKey();

                    if (!favIndustry.equalsIgnoreCase("")) {
                        //Getting a list of stocks that fall under favIndustry
                        controller.getStocksByIndustry(favIndustry)
                                .enqueue(new Callback<List<StockScreener>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<StockScreener>> call,
                                                   @NonNull Response<List<StockScreener>>
                                                           response) {
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    industryStocks = response.body();
                                    industryStocks.sort(Comparator.comparing
                                            (StockScreener::getVolume).reversed());
                                    Log.i(TAG,"getStocksByIndustrySuccessful: "
                                            + response.body());

                                    //Get top 10 stocks with highest volume from industryStocks list
                                    List<StockScreener> topTen = industryStocks.stream().limit(10)
                                            .collect(Collectors.toList());

                                    if (topTen.size() > 0) {
                                        for (int i = 0; i < topTen.size(); i++) {
                                            String ticker = topTen.get(i).getSymbol();
                                            String industry = topTen.get(i).getIndustry();
                                            double marketCap = topTen.get(i).getMarketCap();
                                            double volume = topTen.get(i).getVolume();
                                            double dividends =
                                                    topTen.get(i).getLastAnnualDividend();
                                            //Get price info using getQuote
                                            controller.getQuote(ticker).enqueue(
                                                    new Callback<Quote>() {
                                                @SuppressLint("NotifyDataSetChanged")
                                                @Override
                                                public void onResponse(@NonNull Call<Quote> call,
                                                                       @NonNull Response<Quote>
                                                                               response) {
                                                    if (response.isSuccessful()) {
                                                        assert response.body() != null;
                                                        if (response.body().getTimestamp() > 0) {
                                                            current = response.body()
                                                                    .getCurrentPrice();
                                                            open = response.body().getOpenPrice();
                                                            Log.i(TAG, "getQuoteSuccessful: "
                                                                    + response.body());
                                                            Log.i(TAG, "" + current);
                                                            Log.i(TAG, "" + open);
                                                            RecViewObj rec = new RecViewObj(ticker,
                                                                    current, open, industry,
                                                                    marketCap, volume, dividends);
                                                            Log.i(TAG, "recViewObj "
                                                                    + rec);
                                                            recList.add(rec);
                                                            Log.i(TAG, "onResponse: "
                                                                    + recList);
                                                            adapter.notifyDataSetChanged();
                                                            loadingCircle.setVisibility(View.GONE);
                                                            loadingText.setVisibility(View.GONE);
                                                        }
                                                    }  else {
                                                        try {
                                                            assert response.errorBody() != null;
                                                            Log.i(TAG,
                                                                    "getQuoteNotSuccessful: " +
                                                                    response.errorBody().string());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Quote> call,
                                                                      @NonNull Throwable t) {
                                                    Log.i(TAG,"getQuoteFailure: " + t);
                                                }
                                            });


                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<StockScreener>> call,
                                                  @NonNull Throwable t) {
                                Log.i(TAG, "getStocksByIndustryFailure: " + t);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StockScreener>> call,
                                  @NonNull Throwable t) {
                Log.i(TAG, "getAllStockInfoFailure: " + t);
            }
        });
    }
}
