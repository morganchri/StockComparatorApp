package edu.neu.team28finalproject;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.preferences.UserPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "Company";
    ArrayList<RecViewObj> recList;
    RecViewAdapter adapter;
    UserPreferences viewedStocks;
    HashMap<String, Integer> industryCounts = new HashMap<>();
    List<String> viewedList;
    ControllerImpl controller;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommendations);

        //Getting list of all stocks from AllStocksActivity
        Bundle extras = getIntent().getExtras();
        StockListObj stocks = (StockListObj) extras.getSerializable("stocks");



        recList = new ArrayList<>();
        viewedList = viewedStocks.getViewedStocks();
        controller = new ControllerImpl();

        for (String ticker:viewedList) {
            controller.getCompany(ticker).enqueue(new Callback<CompanyProfile>() {
                @Override
                public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        CompanyProfile company = response.body();
                        String industry = company.getIndustry();
                        industryCounts.put(industry, industryCounts.getOrDefault(industry, 0) + 1);

                        Log.i(TAG, "getCompanyOnResponse: " + response.body());

                    } else {
                        try {
                            Log.i(TAG,"getCompanyNotSuccessful: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CompanyProfile> call, Throwable t) {
                    Log.i(TAG,"getCompanyOnFailure: " + t);
                }
            });
        }

        String favIndustry = Collections.max(industryCounts.entrySet(), Map.Entry.comparingByValue()).getKey();




        RecyclerView recyclerView = findViewById(R.id.recRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecViewAdapter(recList, this);
        recyclerView.setAdapter(adapter);
    }

}
