package edu.neu.team28finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.controller.Controller;
import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllStocksActivity extends AppCompatActivity {

    List<StockListObj> stocks;
    SearchView searchBar;
    StockListAdapter sa;
    ProgressBar mProgressBar;
    TextView loading;
    private final Controller controller = new ControllerImpl();
    private static final String TAG = "AllStocks";

    @SuppressLint({"SourceLockedOrientationActivity", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stocks);
        searchBar = findViewById(R.id.searchView);
        mProgressBar = findViewById(R.id.progress_bar);
        loading = findViewById(R.id.loading);
        stocks = new ArrayList<>();
        RecyclerView listRecyclerView = findViewById(R.id.stockListRecyclerView);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sa = new StockListAdapter(stocks,this);
        listRecyclerView.setAdapter(sa);
        controller.getSymbols().enqueue(new Callback<List<Symbol>>() {
            @Override
            public void onResponse(Call<List<Symbol>> call, Response<List<Symbol>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        StockListObj stock = new StockListObj(response.body().get(i).getDisplaySymbol(),
                                response.body().get(i).getDescription());
                        stocks.add(stock);
                    }
                    Collections.sort(stocks, new Comparator<StockListObj>() {
                        @Override
                        public int compare(final StockListObj object1, final StockListObj object2) {
                            return object1.getTicker().compareTo(object2.getTicker());
                        }
                    });
                    sa.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    Log.i(TAG, "getSymbolsOnResponse: " + response.body());
                } else {
                    try {
                        Log.i(TAG, "getSymbolsOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Symbol>> call, Throwable t) {
                Log.i(TAG, "getSymbolsOnFailure: " + t);
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(containsName(stocks, query)){
                    List<StockListObj> temp;
                    temp = stocks.stream().filter(stockListObj ->
                                    stockListObj.getName().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());
                    StockListAdapter sa = new StockListAdapter(temp,AllStocksActivity.this);
                    listRecyclerView.setAdapter(sa);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(containsName(stocks, newText)){
            //        List<StockListObj> temp;
            //        temp = stocks.stream().filter(stockListObj ->
            //                        stockListObj.getName().toLowerCase().contains(newText.toLowerCase()))
            //                .collect(Collectors.toList());
            //        StockListAdapter sa = new StockListAdapter(temp,AllStocksActivity.this);
            //        listRecyclerView.setAdapter(sa);
                } else {
                    StockListAdapter sa = new StockListAdapter(stocks,AllStocksActivity.this);
                    listRecyclerView.setAdapter(sa);
                }
                return false;
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Sending list of all stocks to the RecommendationsActivity
        Intent intent = new Intent(this, RecommendationsActivity.class);
        intent.putExtra("stocks", (Serializable) stocks);
    }

    public boolean containsName(final List<StockListObj> list, final String name){
        return list.stream().anyMatch(o -> o.getName().toLowerCase().contains(name.toLowerCase()));
    }
}