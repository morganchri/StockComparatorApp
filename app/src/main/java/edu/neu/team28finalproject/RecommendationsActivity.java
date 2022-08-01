package edu.neu.team28finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendationsActivity extends AppCompatActivity {

    ArrayList<RecViewObj> recList;
    LinearLayoutManager layoutManager;
    RecViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommendations);

        recList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecViewAdapter(recList, this);


    }

}
