package edu.neu.team28finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.preferences.UserPreferencesImpl;

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder>{

    private final List<StockViewObj> likes;
    private final Context context;
    private final UserPreferencesImpl up;
    public OnItemClickListener listener;
    public Button removeLikeButton;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public LikesAdapter(List<StockViewObj> likes, Context context) {
        this.likes = likes;
        this.context = context;
        up = new UserPreferencesImpl(context);
    }

    @NonNull
    @Override
    public LikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikesViewHolder(LayoutInflater.from(context).inflate(R.layout.likeslayout,
                parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LikesViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(likes.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent likesIntent = ((LikesActivity) context).getIntent();
                String jsonString = likesIntent.getStringExtra("stockList");
                Gson gson = new Gson();
                Type stockListType = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> stockList = gson.fromJson(jsonString, stockListType);
                String stockInput = likes.get(position).getTicker();
                if (!stockList.contains(stockInput.toUpperCase())) {
                    stockList.add(stockInput);
                }
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                String stockString = gson.toJson(stockList);
                intent.putExtra("Ticker", stockString);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }



}
