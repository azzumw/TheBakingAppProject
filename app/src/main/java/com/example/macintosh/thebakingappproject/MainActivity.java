package com.example.macintosh.thebakingappproject;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Network.GetDataService;
import com.example.macintosh.thebakingappproject.Network.RetrofitClientInstance;
import java.util.List;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements MainRecipeCustomAdapter.MainRecipeCustomOnClickHandler{


    private RecyclerView mRecyclerView;
    private MainRecipeCustomAdapter mainRecipeCustomAdapter;
    private LinearLayoutManager layoutManager;
    private TextView emptyTv;
    private ImageView emptyImgView;
    private Button retryBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emptyTv = findViewById(R.id.empty_view);
        emptyImgView = findViewById(R.id.empty_img_view);
        retryBtn = findViewById(R.id.retryBtn);

        mRecyclerView = findViewById(R.id.mainRecipeRV);
        boolean isThisPhone = getResources().getBoolean(R.bool.isPhone);



        if(isThisPhone){
            //phone
            layoutManager = new LinearLayoutManager(this);

        }else{
            //tablet
//            int numberCol = calculateNoOfColumns(this);
            layoutManager = new GridLayoutManager(this,3);
            int spanCount = 60; // 3 columns
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        callRetroFit();

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
        Intent intent = new Intent(this,TheMasterActivity.class);
        bundle.putParcelable(getString(R.string.RECIPE_BUNDLE_KEY), recipe);
        intent.putExtra("rBundle",bundle);
        startActivity(intent);
    }

    private void callRetroFit(){
        OkHttpClient client = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            IdlingResource.registerOkHttp(client);
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(client).create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();


        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> recipeList = response.body();
                onConnectionSuccess();
                generateDataList(recipeList);


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                onConnectionFailure();
            }
        });

    }

    public void retyconnection(View view) {
        callRetroFit();
    }

    private void onConnectionSuccess(){
        mRecyclerView.setVisibility(View.VISIBLE);
        emptyImgView.setVisibility(View.GONE);
        emptyTv.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
    }

    private void onConnectionFailure(){
        mRecyclerView.setVisibility(View.GONE);
        emptyImgView.setVisibility(View.VISIBLE);
        emptyTv.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);
    }

}
