package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.preferences.UserPreferencesImpl;

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder>{

    private final List<StockViewObj> likes;
    private final Context context;
    private final UserPreferencesImpl up;

    public LikesAdapter(List<StockViewObj> likes, Context context) {
        this.likes = likes;
        this.context = context;
        up = new UserPreferencesImpl(context);
        Intent intent = new Intent(context, LikesActivity.class);
    }

    @NonNull
    @Override
    public LikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikesViewHolder(LayoutInflater.from(context).inflate(R.layout.likeslayout,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LikesViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(likes.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int REQUEST_CODE_CHOOSE_ITEM = 100;
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("Ticker", likes.get(position).getTicker());
                //startActivityForResult(intent, REQUEST_CODE_CHOOSE_ITEM);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return likes.size();
    }



}
