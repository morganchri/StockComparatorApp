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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Error;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Serializable {

    TextView title;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Object> stockList;
    RecyclerView stockRecyclerView;
    StockViewAdapter sa;
    ControllerImpl cr;
    UserPreferencesImpl up;
    List<String> stockNames;
    List<String> timestamps;
    private static final String TAG = "Main";


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.Title);
        title.setGravity(View.TEXT_ALIGNMENT_CENTER);
        stockList = new ArrayList<>();
        up = new UserPreferencesImpl(this);
        cr = new ControllerImpl();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        stockRecyclerView = findViewById(R.id.recyclerView);
        stockRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linLayManager = new LinearLayoutManager(this);
        stockRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {

                } else if (dy > 0) {
                    // Recycle view scrolling down...
                }
            }
        });
        stockRecyclerView.setLayoutManager(linLayManager);
        sa = new StockViewAdapter(stockList,this);
        stockRecyclerView.setAdapter(sa);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button openList = findViewById(R.id.listButton);
        openList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openList = new Intent(MainActivity.this,
                        AllStocksActivity.class);
                MainActivity.this.startActivity(openList);
            }
        });

        Button openRecs = findViewById(R.id.recsButton);
        openRecs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openRecs = new Intent(MainActivity.this,
                        RecommendationsActivity.class);
                MainActivity.this.startActivity(openRecs);
            }
        });

        Button openMovers = findViewById(R.id.openMovers);
        openMovers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openMovers = new Intent(MainActivity.this,
                        Movers.class);
                MainActivity.this.startActivity(openMovers);
            }
        });

        Button histButton = findViewById(R.id.histButton);
        histButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openList = new Intent(MainActivity.this,
                        HistoryActivity.class);
                MainActivity.this.startActivity(openList);
            }
        });

        Button likedButton = findViewById(R.id.likedButton);
        likedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openList = new Intent(MainActivity.this,
                        LikesActivity.class);
                startActivity(openList);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
                                            double cPrice = response.body().getCurrentPrice();
                                            double oPrice = response.body().getOpenPrice();
                                            StockViewObj newStock = new StockViewObj(stockInput
                                                    .getText()
                                                    .toString().toUpperCase(),
                                                    cPrice,
                                                    oPrice);
                                            stockList.add(newStock);
                                            stockNames.add(stockInput.getText().toString().toUpperCase());
                                            timestamps.add(String.valueOf(System.currentTimeMillis()));
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
                                            up.viewStock(stockInput
                                                    .getText().toString().toUpperCase());
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
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Snackbar.make(view, "Adding Stock was cancelled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dialog.cancel();
            }
        });
        alert.show();
    }

    public ArrayList<String> getTickers() throws IOException {
        ArrayList<String> tickers = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.newtickers);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = bufferedReader.readLine();
        while (eachline != null) {
            eachline = bufferedReader.readLine();
            tickers.add(eachline);
        }
        return tickers;
    }

    public boolean isValidTicker(String ticker) throws IOException {
        ArrayList<String> tickers = this.getTickers();
        int l = 0;
        int r = tickers.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = ticker.toUpperCase().compareTo(tickers.get(m).toUpperCase());
            if (res == 0)
                return true;
            if (res > 0)
                l = m + 1;
            else
                r = m - 1;
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

    private LocalDate getPrevSixMonths() {
        return LocalDate.now().minusMonths(6);
    }

    private LocalDate getPrevMonth() {
        return LocalDate.now().minusMonths(1);
    }

    private LocalDate getPrevFiveDays() {
        return LocalDate.now().minusDays(5);
    }

    private LocalDate getPrevDay() {
        return LocalDate.now().minusDays(3);
    }


}
