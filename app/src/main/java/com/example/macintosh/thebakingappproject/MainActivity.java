package com.example.macintosh.thebakingappproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainRecipeCustomAdapter mainRecipeCustomAdapter;
    private LinearLayoutManager linearLayoutManager;

    private static final int NUM = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.mainRecipeRV);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mainRecipeCustomAdapter = new MainRecipeCustomAdapter(NUM);
        mRecyclerView.setAdapter(mainRecipeCustomAdapter);
    }
}
