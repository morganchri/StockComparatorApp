package edu.neu.team28finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllStocksActivity extends AppCompatActivity {

    List<StockListObj> stocks;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stocks);
        stocks = new ArrayList<>();
        RecyclerView listRecyclerView = findViewById(R.id.stockListRecyclerView);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StockListAdapter sa = new StockListAdapter(stocks,this);
        listRecyclerView.setAdapter(sa);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.alltickers);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] split = strLine.split(" ");
                StockListObj stock = new StockListObj(split[0], split[1]);
                if (!Objects.equals(stock.getSector(), "nan")) {
                    stocks.add(stock);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}