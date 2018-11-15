package com.example.macintosh.thebakingappproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
        boolean isThisPhone = getResources().getBoolean(R.bool.isPhone);


        if(isThisPhone){
            //phone
            linearLayoutManager = new LinearLayoutManager(this);

        }else{
            //tablet
            linearLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
            int spanCount = 60; // 3 columns
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount));
        }

        generateDataList(NUM);

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(int x) {

        mainRecipeCustomAdapter = new MainRecipeCustomAdapter(x);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mainRecipeCustomAdapter);
    }
}
