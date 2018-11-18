package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Recipe;

import java.util.List;


public class MainRecipeCustomAdapter extends RecyclerView.Adapter<MainRecipeCustomAdapter.MainRecipeViewHolder> {

    private int number;
    private List<Recipe> recipeList;

    public MainRecipeCustomAdapter(List<Recipe> recipes){
        recipeList = recipes;
    }
    @NonNull
    @Override
    public MainRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.main_recipe_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem,viewGroup,false);
        MainRecipeViewHolder mainRecipeViewHolder = new MainRecipeViewHolder(view);
        return mainRecipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecipeViewHolder mainRecipeViewHolder, int i) {

        mainRecipeViewHolder.recipeTitle.setText(recipeList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class MainRecipeViewHolder extends RecyclerView.ViewHolder{

        private TextView recipeTitle;

        public MainRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitleTv);
        }
    }
}
