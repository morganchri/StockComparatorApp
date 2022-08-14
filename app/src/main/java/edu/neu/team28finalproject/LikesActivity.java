package edu.neu.team28finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikesActivity extends AppCompatActivity {

    List<StockViewObj> likes;
    RecyclerView likesRecycler;
    LikesAdapter la;
    TextView header;
    UserPreferencesImpl up;
    ControllerImpl controller;
    Button removeLikesButton;
    private static final String TAG = "Likes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        header = findViewById(R.id.likesHeader);
        header.setGravity(View.TEXT_ALIGNMENT_CENTER);
        likes = new ArrayList<>();
        removeLikesButton = findViewById(R.id.removeLikesButton);
        likesRecycler = findViewById(R.id.likesRecycler);
        likesRecycler.setHasFixedSize(true);
        LinearLayoutManager likesLinLayout = new LinearLayoutManager(this);
        likesRecycler.setLayoutManager(likesLinLayout);
        la = new LikesAdapter(likes, this);
        likesRecycler.setAdapter(la);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        up = new UserPreferencesImpl(this);
        controller = new ControllerImpl();
        if (up.getLikedStocks().size() > 0) {
            for (int i = 0; i < up.getLikedStocks().size(); i++) {
                String ticker = up.getLikedStocks().get(i);
                System.out.println(ticker);
                controller.getQuote(ticker)
                        .enqueue(new Callback<Quote>() {
                            @Override
                            public void onResponse(@NonNull Call<Quote> call,
                                                   @NonNull Response<Quote> response) {
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    if (response.body().getTimestamp() > 0) {
                                        double cPrice = response.body().getCurrentPrice();
                                        double oPrice = response.body().getOpenPrice();
                                        StockViewObj newStock = new StockViewObj(ticker, cPrice,
                                                oPrice);
                                        likes.add(newStock);
                                        la.notifyDataSetChanged();
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
                la.setOnItemClickListener(new LikesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        up.unlikeStock(ticker);
                        finish();
                        startActivity(getIntent());
                    }
                });
                 }
                } else {
                    la.notifyDataSetChanged();
                    likes.add(new StockViewObj("No Liked Stocks", 0.00, 0.00));
            }
        }
}