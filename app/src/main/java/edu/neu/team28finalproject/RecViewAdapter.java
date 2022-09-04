package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.neu.team28finalproject.preferences.UserPreferencesImpl;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewHolder> {

    private final ArrayList<RecViewObj> recList;
    private final Context context;
    private final UserPreferencesImpl up;

    public RecViewAdapter(ArrayList<RecViewObj> recList, Context context) {
        this.recList = recList;
        this.context = context;
        up = new UserPreferencesImpl(context);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recommendations_layout,null));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(recList.get(position));

        RecViewObj recViewObj = recList.get(position);

        if (up.getLikedStocks().size() > 0) {
            for (int i = 0; i < up.getLikedStocks().size(); i++) {
                if (recViewObj.getTicker().equalsIgnoreCase(up.getLikedStocks().get(i))) {
                    (holder).like_button.setChecked(true);
                    (holder).like_button.setClickable(false);
                }
            }
        }
        if (!(holder).like_button.isChecked()) {
            (holder).like_button.setOnClickListener(v -> {
                RecViewObj stock = recList.get(position);
                if (up.getLikedStocks().size() > 0) {
                    for (int i = 0; i < up.getLikedStocks().size(); i++) {
                        if (!stock.getTicker().equals(up.getLikedStocks().get(i))) {
                            up.likeStock(stock.getTicker());
                            Snackbar.make(v, "Stock liked",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(v, "Stock already liked",
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        notifyDataSetChanged();
                    }
                } else {
                    up.likeStock(stock.getTicker());
                    Snackbar.make(v, "Stock liked",
                                    Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    notifyDataSetChanged();
                }
            });
        } else {
            (holder).like_button.setOnClickListener(v -> {
                RecViewObj stock = recList.get(position);
                up.unlikeStock(stock.getTicker());
                (holder).like_button.setChecked(false);
                Snackbar.make(v, "Stock unliked",
                                Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }
}
