package com.example.macintosh.thebakingappproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Network.GetDataService;
import com.example.macintosh.thebakingappproject.Network.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new AppWidgetListView (this.getApplicationContext());
    }
}


class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory{

    private List<Ingredient> ingredientsList;
    private Context context;

    public AppWidgetListView(Context context) {

        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(context.getString(R.string.JSON_KEY),"no preference");
        if(preferences.contains(context.getString(R.string.JSON_KEY))){
            ingredientsList = getIngredientFromJson(json);
        }else {
            ingredientsList = new ArrayList<>();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);

        views.setTextViewText(R.id.ingredientNameTv, ingredientsList.get(position).getIngredient());
        views.setTextViewText(R.id.ingredientQtyTv,String.valueOf(ingredientsList.get(position).getQuantity()));
        views.setTextViewText(R.id.ingredientMeasureTv,ingredientsList.get(position).getMeasure());

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position; // Treat all items in the GridView the same
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private List<Ingredient> getIngredientFromJson(String json){
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(json,Recipe.class);
        return recipe.getIngredients();

    }
}