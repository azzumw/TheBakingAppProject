package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Network.GetDataService;
import com.example.macintosh.thebakingappproject.Network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements MainRecipeCustomAdapter.MainRecipeCustomOnClickHandler {

    private static final String LOG_TAG = "MAINACTIVITY";
    private RecyclerView mRecyclerView;
    private MainRecipeCustomAdapter mainRecipeCustomAdapter;
    private LinearLayoutManager layoutManager;
    private TextView emptyTv;
    private ImageView emptyImgView;

    private static final int NUM = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTv = findViewById(R.id.empty_view);
        emptyImgView = findViewById(R.id.empty_img_view);

        mRecyclerView = findViewById(R.id.mainRecipeRV);
        boolean isThisPhone = getResources().getBoolean(R.bool.isPhone);


        if(isThisPhone){
            //phone
            layoutManager = new LinearLayoutManager(this);

        }else{
            //tablet
            layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
            int spanCount = 60; // 3 columns
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount));
        }


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();


        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                generateDataList(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(LOG_TAG,"ERROR:"+ t.toString());
                mRecyclerView.setVisibility(View.GONE);
                emptyImgView.setVisibility(View.VISIBLE);
                emptyTv.setVisibility(View.VISIBLE);
            }
        });

        
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Recipe> recipes) {

        mainRecipeCustomAdapter = new MainRecipeCustomAdapter(recipes,this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mainRecipeCustomAdapter);

    }

    @Override
    public void onClickHandler(Recipe recipe) {

        Bundle bundle = new Bundle();
        //TODO 1: call TheMasterListActivity.class
        Intent intent = new Intent(this,TheMasterActivity.class);
        bundle.putParcelable("bundle", recipe);
        intent.putExtra("rBundle",bundle);
//        intent.putExtra("recipe",recipe);
        startActivity(intent);
    }
}
