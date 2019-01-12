package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Network.GetDataService;
import com.example.macintosh.thebakingappproject.Network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements MainRecipeCustomAdapter.MainRecipeCustomOnClickHandler {

    private RecyclerView mRecyclerView;
    private MainRecipeCustomAdapter mainRecipeCustomAdapter;
    private LinearLayoutManager layoutManager;
    private TextView emptyTv;
    private ImageView emptyImgView;
    private Button retryBtn;

    private static final int NUM = 10;
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
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
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
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
////            return true;
////        }else{
////            return false;
////        }
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

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
