package edu.neu.team28finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Error;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Serializable {

    TextView title;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Object> stockList;
    List<String> stockStrings;
    RecyclerView stockRecyclerView;
    StockViewAdapter sa;
    ControllerImpl cr;
    UserPreferencesImpl up;
    List<String> tickers;
    private static final String TAG = "Main";


    @SuppressLint({"SourceLockedOrientationActivity", "ResourceType", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        up = new UserPreferencesImpl(this);
        cr = new ControllerImpl();
        tickers = new ArrayList<>();
        getTickers();
        title = findViewById(R.id.Title);
        title.setGravity(View.TEXT_ALIGNMENT_CENTER);
        stockList = new ArrayList<>();
        stockStrings = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        stockRecyclerView = findViewById(R.id.recyclerView);
        stockRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linLayManager = new LinearLayoutManager(this);
        stockRecyclerView.setLayoutManager(linLayManager);
        sa = new StockViewAdapter(stockList,this);
        stockRecyclerView.setAdapter(sa);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String jsonString = bundle.getString("Ticker");
            Type listOfStockType = new TypeToken<ArrayList<String>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<String> Stocklist = gson.fromJson(jsonString, listOfStockType);
            for (int i = 0; i < Stocklist.size(); i++) {
                addStockFromLikes(Stocklist.get(i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("NULL");
        }

        Button openList = findViewById(R.id.listButton);
        openList.setOnClickListener(v -> {
            Intent openList1 = new Intent(MainActivity.this,
                    AllStocksActivity.class);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(stockStrings);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            openList1.putExtra("stockList", jsonString);
            MainActivity.this.startActivity(openList1);
        });

        Button openRecs = findViewById(R.id.recsButton);
        openRecs.setOnClickListener(v -> {
            Intent openRecs1 = new Intent(MainActivity.this,
                    RecommendationsActivity.class);
            MainActivity.this.startActivity(openRecs1);
        });

        Button openMovers = findViewById(R.id.openMovers);
        openMovers.setOnClickListener(v -> {
            Intent openMovers1 = new Intent(MainActivity.this,
                    Movers.class);
            MainActivity.this.startActivity(openMovers1);
        });

        Button histButton = findViewById(R.id.histButton);
        histButton.setOnClickListener(v -> {
            Intent openList12 = new Intent(MainActivity.this,
                    HistoryActivity.class);
            MainActivity.this.startActivity(openList12);
        });

        Button likedButton = findViewById(R.id.likedButton);
        likedButton.setOnClickListener(v -> {
            Intent openList13 = new Intent(MainActivity.this,
                    LikesActivity.class);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(stockStrings);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            openList13.putExtra("stockList", jsonString);
            startActivity(openList13);
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            updateOperation();
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
        });

    }

    public void updateOperation() {
        int i;
        for (i = 0; i < stockList.size(); i++) {
            int finalI = i;
            if (finalI % 2 == 0) {
                StockViewObj stock = (StockViewObj) stockList.get(finalI);
                cr.getQuote(stock.getTicker().toUpperCase())
                        .enqueue(new Callback<Quote>() {
                            @Override
                            public void onResponse(@NonNull Call<Quote> call,
                                                   @NonNull Response<Quote> response) {
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    if (response.body().getTimestamp() > 0) {;
                                        double cPrice = response.body().getCurrentPrice();
                                        double oPrice = response.body().getOpenPrice();
                                        StockViewObj newStock = new StockViewObj(stock.getTicker()
                                                .toUpperCase(),
                                                cPrice,
                                                oPrice);
                                        stockList.set(finalI, newStock);
                                        Log.i(TAG, "getQuoteOnResponse: "
                                                + response.body());
                                    }
                                } else {
                                    try {
                                        assert response.errorBody() != null;
                                        Log.i(TAG, "getQuoteOnResponseNotSuccessful: " +
                                                response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<Quote> call,
                                                  @NonNull Throwable t) {
                                Log.i(TAG, "getQuoteOnFailure: " + t);
                            }
                        });
            } else {
                GraphViewObj graph = (GraphViewObj) stockList.get(finalI);
                cr.getIndicators(graph.getTicker().toUpperCase(),
                        IndicatorResolution.RES_D, dateToUnix(getPrevYear()),
                        dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(@NonNull Call<Indicator> call,
                                           @NonNull Response<Indicator> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getStatus()
                                    .equalsIgnoreCase("ok")) {
                                GraphViewObj newGraph =
                                        new GraphViewObj(graph.getTicker().toUpperCase(),
                                        getData(response.body().getClosePrices()));
                                stockList.set(finalI, newGraph);
                                stockRecyclerView.setAdapter(sa);
                                //sa.notifyDataSetChanged();
                                Log.i(TAG, "getIndicatorsOnResponse: "
                                        + response.body());
                            } else {
                                //no data
                            }
                        } else {
                            try {
                                assert response.errorBody() != null;
                                Log.i(TAG,
                                        "getIndicatorsOnResponseNotSuccessful: " +
                                                response.errorBody().
                                                        string());
                                ObjectMapper om = new ObjectMapper();
                                Error e = om.readValue(response.errorBody().string(),
                                        Error.class);
                                Log.i(TAG, "error: " + e.getError());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Indicator> call,
                                          @NonNull Throwable t) {
                        Log.i(TAG, "getIndicatorsOnFailure: " + t);
                    }
                });
            }
        }
    }

    public void addStock(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View dialog_layout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alert.setTitle("Stock input");
        alert.setMessage("Please input a stock ticker (i.e. AAPL)");

        EditText stockInput = dialog_layout.findViewById(R.id.text1);
        alert.setView(dialog_layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                for (int i = 0; i < stockList.size(); i+=2) {
                    StockViewObj check = (StockViewObj)stockList.get(i);
                    if (check.getTicker().equalsIgnoreCase(stockInput.getText().toString())) {
                        Snackbar.make(view, "Stock already in list", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                }
                if (stockInput.getText().toString().equals(" ")) {
                    Snackbar.make(view, "Stock ticker must not be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    try {
                        if (isValidTicker(stockInput.getText().toString().toUpperCase())) {
                            cr.getQuote(stockInput.getText().toString().toUpperCase())
                                    .enqueue(new Callback<Quote>() {
                                @Override
                                public void onResponse(@NonNull Call<Quote> call,
                                                       @NonNull Response<Quote> response) {
                                    if (response.isSuccessful()) {
                                        assert response.body() != null;
                                        if (response.body().getTimestamp() > 0) {
                                            up.viewStock(stockInput
                                                    .getText().toString().toUpperCase(),
                                                    String.valueOf(System.currentTimeMillis()));
                                            double cPrice = response.body().getCurrentPrice();
                                            double oPrice = response.body().getOpenPrice();
                                            StockViewObj newStock = new StockViewObj(stockInput
                                                    .getText()
                                                    .toString().toUpperCase(),
                                                    cPrice,
                                                    oPrice);
                                            stockList.add(newStock);
                                            stockStrings.add(
                                                    stockInput.getText().toString().toUpperCase());
                                            Snackbar.make(view, "Adding Stock was successful",
                                                            Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Log.i(TAG, "getQuoteOnResponse: "
                                                    + response.body());
                                        }
                                        else {
                                            Snackbar.make(view, "No match",
                                                            Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    } else {
                                        try {
                                            assert response.errorBody() != null;
                                            Log.i(TAG, "getQuoteOnResponseNotSuccessful: " +
                                                    response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(@NonNull Call<Quote> call,
                                                      @NonNull Throwable t) {
                                    Log.i(TAG, "getQuoteOnFailure: " + t);
                                }
                            });
                            cr.getIndicators(stockInput.getText().toString().toUpperCase(),
                                    IndicatorResolution.RES_D, dateToUnix(getPrevYear()),
                                    dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onResponse(@NonNull Call<Indicator> call,
                                                       @NonNull Response<Indicator> response) {
                                    if (response.isSuccessful()) {
                                        assert response.body() != null;
                                        if (response.body().getStatus()
                                                .equalsIgnoreCase("ok")) {
                                            GraphViewObj newGraph = new GraphViewObj(stockInput
                                                    .getText().toString().toUpperCase(),
                                                    getData(response.body().getClosePrices()));
                                            stockList.add(newGraph);
                                            sa.notifyDataSetChanged();
                                            Log.i(TAG, "getIndicatorsOnResponse: "
                                                    + response.body());
                                        } else {
                                            Snackbar.make(view, "No data",
                                                            Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    } else {
                                        try {
                                            assert response.errorBody() != null;
                                            Log.i(TAG,
                                                    "getIndicatorsOnResponseNotSuccessful: " +
                                                    response.errorBody().
                                                            string());
                                            ObjectMapper om = new ObjectMapper();
                                            Error e = om.readValue(response.errorBody().string(),
                                                    Error.class);
                                            Log.i(TAG, "error: " + e.getError());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(@NonNull Call<Indicator> call,
                                                      @NonNull Throwable t) {
                                    Log.i(TAG, "getIndicatorsOnFailure: " + t);
                                }
                            });
                        } else {
                            Snackbar.make(view, "Adding Stock was unsuccessful, " +
                                                    "not valid Ticker",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            Snackbar.make(view, "Adding Stock was cancelled", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            dialog.cancel();
        });
        alert.show();
    }

    public void addStockFromLikes(String stockInput) {
        cr.getQuote(stockInput.toUpperCase())
                .enqueue(new Callback<Quote>() {
                    @Override
                    public void onResponse(@NonNull Call<Quote> call,
                                           @NonNull Response<Quote> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getTimestamp() > 0) {
                                up.viewStock(stockInput.toUpperCase(),
                                        String.valueOf(System.currentTimeMillis()));
                                double cPrice = response.body().getCurrentPrice();
                                double oPrice = response.body().getOpenPrice();
                                StockViewObj newStock = new StockViewObj(stockInput
                                        .toUpperCase(),
                                        cPrice,
                                        oPrice);
                                stockList.add(newStock);
                                Log.i(TAG, "getQuoteOnResponse: "
                                        + response.body());
                            }
                        } else {
                            try {
                                assert response.errorBody() != null;
                                Log.i(TAG, "getQuoteOnResponseNotSuccessful: " +
                                        response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Quote> call,
                                          @NonNull Throwable t) {
                        Log.i(TAG, "getQuoteOnFailure: " + t);
                    }
                });
        cr.getIndicators(stockInput.toUpperCase(),
                IndicatorResolution.RES_D, dateToUnix(getPrevYear()),
                dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<Indicator> call,
                                   @NonNull Response<Indicator> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus()
                            .equalsIgnoreCase("ok")) {
                        GraphViewObj newGraph = new GraphViewObj(stockInput
                                .toUpperCase(),
                                getData(response.body().getClosePrices()));
                        stockList.add(newGraph);
                        stockStrings.add(stockInput.toUpperCase());
                        sa.notifyDataSetChanged();
                        Log.i(TAG, "getIndicatorsOnResponse: "
                                + response.body());
                    } else {
                        //no data
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.i(TAG,
                                "getIndicatorsOnResponseNotSuccessful: " +
                                        response.errorBody().
                                                string());
                        ObjectMapper om = new ObjectMapper();
                        Error e = om.readValue(response.errorBody().string(),
                                Error.class);
                        Log.i(TAG, "error: " + e.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Indicator> call,
                                  @NonNull Throwable t) {
                Log.i(TAG, "getIndicatorsOnFailure: " + t);
            }
        });
    }

    public void getTickers() {
        cr.getSymbols().enqueue(new Callback<List<Symbol>>() {
            @Override
            public void onResponse(@NonNull Call<List<Symbol>> call,
                                   @NonNull Response<List<Symbol>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            tickers.add(response.body().get(i).getDisplaySymbol());
                        }
                    }
                    Log.i(TAG, "getSymbolsOnResponse: " + response.body());
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.i(TAG, "getSymbolsOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Symbol>> call,
                                  @NonNull Throwable t) {
                Log.i(TAG, "getSymbolsOnFailure: " + t);
            }
        });
    }

    public boolean isValidTicker(String ticker) throws IOException {
        for (int i = 0; i < tickers.size(); i++) {
            if (ticker.equalsIgnoreCase(tickers.get(i))) {
                return true;
            }
        }
        return false;
    }

    private LineData getData(List<Double> prices) {
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            float val = prices.get(i).floatValue();
            yVals.add(new Entry(i,val));
        }
        LineDataSet set1 = new LineDataSet(yVals, "Data Set");
        if (prices.get(prices.size() - 1) > prices.get(prices.size() - 2)) {
            set1.setColor(Color.rgb(76,153,0));
        } else if (prices.get(prices.size() - 1) < prices.get(prices.size() - 2)) {
            set1.setColor(Color.RED);
        } else {
            set1.setColor(Color.BLACK);
        }
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        return new LineData(set1);
    }
    private long dateToUnix(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.atStartOfDay(zoneId).toEpochSecond();
    }

    private LocalDate getCurrYear() {
        return LocalDate.now();
    }

    private LocalDate getPrevYear() {
        return LocalDate.now().minusYears(1);
    }
}