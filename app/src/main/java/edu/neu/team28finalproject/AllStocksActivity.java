package edu.neu.team28finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AllStocksActivity extends AppCompatActivity {

    List<StockListObj> stocks;
    SearchView searchBar;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stocks);
        searchBar = findViewById(R.id.searchView);
        stocks = new ArrayList<>();
        RecyclerView listRecyclerView = findViewById(R.id.stockListRecyclerView);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StockListAdapter sa = new StockListAdapter(stocks,this);
        listRecyclerView.setAdapter(sa);
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
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.alltickers);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] split = strLine.split(" ");
                if (split[2].equals("Telecommunications")) {
                    split[2] = "Telecoms";
                }
                StockListObj stock = new StockListObj(split[0],
                        split[1].replaceAll("\\s+",""), split[2]);
                if (!Objects.equals(stock.getSector(), "nan")
                        && !Objects.equals(stock.getSector(), " ")
                        && !Objects.equals(stock.getName(), " ")
                        && !Objects.equals(stock.getSector(), "NaN")
                        && !Objects.equals(stock.getSector(), "")
                        && !Objects.equals(stock.getName(), "")) {
                    stocks.add(stock);
                    //Sending list of all stocks to the RecommendationsActivity
                    Intent intent = new Intent(this, RecommendationsActivity.class);
                    intent.putExtra("stocks", (Serializable) stocks);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean containsName(final List<StockListObj> list, final String name){
        return list.stream().anyMatch(o -> o.getName().toLowerCase().equals(name.toLowerCase()));
    }
}