package edu.neu.team28finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView title;
    private List<StockViewObj> stockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.Title);
        title.setGravity(View.TEXT_ALIGNMENT_CENTER);
        RecyclerView stockRecyclerView = findViewById(R.id.recyclerView);
        stockRecyclerView.setHasFixedSize(true);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stockRecyclerView.setAdapter(new StockViewAdapter(stockList, this));

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
                if (isValidTicker(stockInput.getText().toString())) {
                    StockViewObj newStock = new StockViewObj(stockInput.getText().toString(), 100.00,
                            98.00);
                    stockList.add(newStock);
                    Snackbar.make(view, "Adding Stock was successful", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Adding Stock was unsuccessful, not valid Ticker",
                                    Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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

    public static boolean isValidTicker(String ticker) {
        try {
            CSVReader reader = new CSVReader(new FileReader("res\\raw\\ticker.csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (IOException e) {
            System.out.println("Could not find file");
        }
        return true;
    }

}
