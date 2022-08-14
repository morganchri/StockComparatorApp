package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.team28finalproject.controller.Controller;
import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Error;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Object> stocks;
    private final Context context;
    private final Controller controller = new ControllerImpl();
    private final UserPreferencesImpl up;
    private static final String TAG = "ViewAdapter";

    public StockViewAdapter(List<Object> stocks,
                            Context context) {
        this.stocks = stocks;
        this.context = context;
        up = new UserPreferencesImpl(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new StockViewHolder(LayoutInflater.from(context).inflate(R.layout.stockviewlayout,
                    parent, false));
        } else {
            return new GraphViewHolder(LayoutInflater.from(context).inflate(R.layout.graphlayout,
                    parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        Object item = stocks.get(position);
        if (holder instanceof StockViewHolder) {
            ((StockViewHolder) holder).bindThisData((StockViewObj) item);
            ((StockViewHolder) holder).deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int newPosition = holder.getAdapterPosition();
                    stocks.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(newPosition, stocks.size());
                    stocks.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(newPosition, stocks.size());
                }
            });
            StockViewObj stock = (StockViewObj) stocks.get(position);
            if (up.getLikedStocks().size() > 0) {
                for (int i = 0; i < up.getLikedStocks().size(); i++) {
                    if (stock.getTicker().equalsIgnoreCase(up.getLikedStocks().get(i))) {
                        ((StockViewHolder) holder).likeButton.setChecked(true);
                        ((StockViewHolder) holder).likeButton.setClickable(false);
                    }
                }
            }
            if (!((StockViewHolder) holder).likeButton.isChecked()) {
                ((StockViewHolder) holder).likeButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    public void onClick(View v) {
                        StockViewObj stock = (StockViewObj) stocks.get(position);
                        if (up.getLikedStocks().size() > 0) {
                            for (int i = 0; i < up.getLikedStocks().size(); i++) {
                                if (stock.getTicker().equals(up.getLikedStocks().get(i))) {
                                    Snackbar.make(v, "Stock already liked",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    notifyDataSetChanged();
                                } else {
                                    up.likeStock(stock.getTicker());
                                    Snackbar.make(v, "Stock liked",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    notifyDataSetChanged();
                                }
                            }
                        } else {
                            up.likeStock(stock.getTicker());
                            Snackbar.make(v, "Stock liked",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            notifyDataSetChanged();
                        }
                    }
                });
            } else {
                ((StockViewHolder) holder).likeButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    public void onClick(View v) {
                        StockViewObj stock = (StockViewObj) stocks.get(position);
                        up.unlikeStock(stock.getTicker());
                        ((StockViewHolder) holder).likeButton.setChecked(false);
                        Snackbar.make(v, "Stock unliked",
                                        Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        notifyDataSetChanged();
                    }
                });
            }
        } else {
            ((GraphViewHolder) holder).bindThisData((GraphViewObj) item);
            ((GraphViewHolder) holder).btn[0].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < ((GraphViewHolder) holder).btn.length; i++) {
                        unFocus(((GraphViewHolder) holder).btn[i]);
                    }
                    setFocus(((GraphViewHolder) holder).btn[0]);
                    controller.getIndicators(((GraphViewObj) item).getTicker(),
                            IndicatorResolution.RES_60, dateToUnix(getPrevDay()),
                            dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<Indicator> call,
                                               @NonNull Response<Indicator> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                    ((GraphViewObj) item).setEntries(getData(response.body().getClosePrices()));
                                    StockViewObj stock = (StockViewObj) stocks.get(position - 1);
                                    stock.setOpen(response.body().getClosePrices().get(0));
                                    //notifyItemChanged(position - 1);
                                    notifyDataSetChanged();
                                    Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                                } else {
                                    Snackbar.make(v, "No data",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                            response.errorBody().string());
                                    ObjectMapper om = new ObjectMapper();
                                    Error e = om.readValue(response.errorBody().string(), Error.class);
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
            });
            ((GraphViewHolder) holder).btn[1].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < ((GraphViewHolder) holder).btn.length; i++) {
                        unFocus(((GraphViewHolder) holder).btn[i]);
                    }
                    setFocus(((GraphViewHolder) holder).btn[1]);

                    controller.getIndicators(((GraphViewObj) item).getTicker(),
                            IndicatorResolution.RES_60, dateToUnix(getPrevFiveDays()),
                            dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<Indicator> call,
                                               @NonNull Response<Indicator> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                    ((GraphViewObj) item).setEntries(getData(response.body().getClosePrices()));
                                    StockViewObj stock = (StockViewObj) stocks.get(position - 1);
                                    stock.setOpen(response.body().getClosePrices().get(0));
                                    notifyDataSetChanged();
                                    Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                                } else {
                                    Snackbar.make(v, "No data",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                            response.errorBody().string());
                                    ObjectMapper om = new ObjectMapper();
                                    Error e = om.readValue(response.errorBody().string(), Error.class);
                                    Log.i(TAG, "error: " + e.getError());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Indicator> call, @NonNull Throwable t) {
                            Log.i(TAG, "getIndicatorsOnFailure: " + t);
                        }
                    });
                }
            });
            ((GraphViewHolder) holder).btn[2].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < ((GraphViewHolder) holder).btn.length; i++) {
                        unFocus(((GraphViewHolder) holder).btn[i]);
                    }
                    setFocus(((GraphViewHolder) holder).btn[2]);

                    controller.getIndicators(((GraphViewObj) item).getTicker(),
                            IndicatorResolution.RES_D, dateToUnix(getPrevMonth()),
                            dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<Indicator> call,
                                               @NonNull Response<Indicator> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                    ((GraphViewObj) item).setEntries(getData(response.body().getClosePrices()));
                                    StockViewObj stock = (StockViewObj) stocks.get(position - 1);
                                    stock.setOpen(response.body().getClosePrices().get(0));
                                    notifyDataSetChanged();
                                    Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                                } else {
                                    Snackbar.make(v, "No data",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                            response.errorBody().string());
                                    ObjectMapper om = new ObjectMapper();
                                    Error e = om.readValue(response.errorBody().string(), Error.class);
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
            });
            ((GraphViewHolder) holder).btn[3].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < ((GraphViewHolder) holder).btn.length; i++) {
                        unFocus(((GraphViewHolder) holder).btn[i]);
                    }
                    setFocus(((GraphViewHolder) holder).btn[3]);

                    controller.getIndicators(((GraphViewObj) item).getTicker(),
                            IndicatorResolution.RES_D, dateToUnix(getPrevSixMonths()),
                            dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<Indicator> call,
                                               @NonNull Response<Indicator> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                    ((GraphViewObj) item).setEntries(getData(response.body().getClosePrices()));
                                    StockViewObj stock = (StockViewObj) stocks.get(position - 1);
                                    stock.setOpen(response.body().getClosePrices().get(0));
                                    notifyDataSetChanged();
                                    Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                                } else {
                                    Snackbar.make(v, "No data",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                            response.errorBody().string());
                                    ObjectMapper om = new ObjectMapper();
                                    Error e = om.readValue(response.errorBody().string(), Error.class);
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
            });
            ((GraphViewHolder) holder).btn[4].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < ((GraphViewHolder) holder).btn.length; i++) {
                        unFocus(((GraphViewHolder) holder).btn[i]);
                    }
                    setFocus(((GraphViewHolder) holder).btn[4]);
                    controller.getIndicators(((GraphViewObj) item).getTicker(),
                            IndicatorResolution.RES_D, dateToUnix(getPrevYear()),
                            dateToUnix(getCurrYear())).enqueue(new Callback<Indicator>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<Indicator> call,
                                               @NonNull Response<Indicator> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                    ((GraphViewObj) item).setEntries(getData(response.body().getClosePrices()));
                                    StockViewObj stock = (StockViewObj) stocks.get(position - 1);
                                    stock.setOpen(response.body().getClosePrices().get(0));
                                    notifyDataSetChanged();
                                    Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                                } else {
                                    Snackbar.make(v, "No data",
                                                    Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                            response.errorBody().string());
                                    ObjectMapper om = new ObjectMapper();
                                    Error e = om.readValue(response.errorBody().string(), Error.class);
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
            });
        }
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (stocks.get(position) instanceof StockViewObj) {
            return 0;
        }
        return 1;
    }

    private void setFocus(Button btn_focus){
        btn_focus.setTextColor(Color.MAGENTA);
    }

    private void unFocus(Button btn_unfocus){
        btn_unfocus.setTextColor(Color.BLACK);
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

    private LineData getData(List<Double> prices) {
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            float val = prices.get(i).floatValue();
            yVals.add(new Entry(i,val));
        }
        LineDataSet set1 = new LineDataSet(yVals, "Data Set");
        if (prices.get(prices.size() - 1) > prices.get(0)) {
            set1.setColor(Color.rgb(76,153,0));
        } else if (prices.get(prices.size() - 1) < prices.get(0)) {
            set1.setColor(Color.RED);
        } else {
            set1.setColor(Color.BLACK);
        }
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        return new LineData(set1);
    }

}
