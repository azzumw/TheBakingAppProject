package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MainRecipeCustomAdapter extends RecyclerView.Adapter<MainRecipeCustomAdapter.MainRecipeViewHolder> {

    private int number;

    public MainRecipeCustomAdapter(int num){
        number = num;
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
        String sometext = "Recipe ";
        int recipeNumber = i++;
        mainRecipeViewHolder.recipeTitle.setText(sometext + recipeNumber);
    }

    @Override
    public int getItemCount() {
        return number;
    }

    class MainRecipeViewHolder extends RecyclerView.ViewHolder{

        private TextView recipeTitle;

        public MainRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitleTv);
        }
    }
}
