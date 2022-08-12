package edu.neu.team28finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder>{

    private final List<StockViewObj> likes;
    private final Context context;

    public LikesAdapter(List<StockViewObj> likes, Context context) {
        this.likes = likes;
        this.context = context;
    }

    @NonNull
    @Override
    public LikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikesViewHolder(LayoutInflater.from(context).inflate(R.layout.likeslayout,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LikesViewHolder holder, int position) {
        holder.bindThisData(likes.get(position));
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }
}
