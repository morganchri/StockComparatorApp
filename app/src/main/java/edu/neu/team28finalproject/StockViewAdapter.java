package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Object> stocks;
    private final Context context;

    public StockViewAdapter(List<Object> stocks,
                            Context context) {
        this.stocks = stocks;
        this.context = context;
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
                    stocks.remove(position);
                    notifyItemRemoved(position);
                }
            });
            ((StockViewHolder) holder).likeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to add to the firebase DB
                }
            });
        } else {
            ((GraphViewHolder) holder).bindThisData((GraphViewObj) item);
            ((GraphViewHolder) holder).day1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to filter on one day
                }
            });
            ((GraphViewHolder) holder).day5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to filter on 5 days
                }
            });
            ((GraphViewHolder) holder).month.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to filter on one month
                }
            });
            ((GraphViewHolder) holder).month6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to filter on 6 months
                }
            });
            ((GraphViewHolder) holder).year.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //code to filter on one year
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
}
