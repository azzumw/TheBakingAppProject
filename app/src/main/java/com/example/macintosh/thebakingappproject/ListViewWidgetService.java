package com.example.macintosh.thebakingappproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Network.GetDataService;
import com.example.macintosh.thebakingappproject.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewWidgetService extends RemoteViewsService {
    List<Recipe> recipeList;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new AppWidgetListView (RecipeDataBase.getRecipeFromNetwork(),this.getApplicationContext());
    }
}


class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory{

    private List<Recipe> recipeList;
    private Context context;

    public AppWidgetListView(List<Recipe> dataList, Context context) {
        this.recipeList = dataList;
        this.context = context;

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);

        views.setTextViewText(R.id.recipeNameTv, recipeList.get(position).getName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("RecipeName",recipeList.get(position).getName());
        views.setOnClickFillInIntent(R.id.parentView, fillInIntent);

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
}