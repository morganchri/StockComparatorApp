package edu.neu.team28finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    TextView title;
    List<Object> stockList;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.Title);
        title.setGravity(View.TEXT_ALIGNMENT_CENTER);
        stockList = new ArrayList<>();
        RecyclerView stockRecyclerView = findViewById(R.id.recyclerView);
        stockRecyclerView.setHasFixedSize(true);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StockViewAdapter sa = new StockViewAdapter(stockList,this);
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
                if (stockInput.getText().toString().equals(" ")) {
                    Snackbar.make(view, "Stock ticker must not be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    try {
                        if (isValidTicker(stockInput.getText().toString().toUpperCase())) {
                            //Just test data for now
                            StockViewObj newStock = new StockViewObj(stockInput.getText()
                                    .toString().toUpperCase(),
                                    100.00,
                                    140.00);
                            stockList.add(newStock);
                            GraphViewObj newGraph = new GraphViewObj(stockInput.getText()
                                    .toString().toUpperCase(),
                                    getData(36,100));
                            stockList.add(newGraph);
                            Snackbar.make(view, "Adding Stock was successful",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
        int i = 0;
        while (i < tickers.size()) {
            if (Objects.equals(ticker, tickers.get(i))) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    private LineData getData(int count, int range) {
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random()*range) + 3;
            yVals.add(new Entry(i,val));
        }
        LineDataSet set1 = new LineDataSet(yVals, "Data Set");
        set1.setLineWidth(3f);
        //set1.setCircleRadius(5f);
        //set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.BLACK);
        //set1.setCircleColor(Color.BLACK);
        //set1.setHighLightColor(Color.BLACK);
        set1.setDrawValues(false);
        return new LineData(set1);
    }



}
