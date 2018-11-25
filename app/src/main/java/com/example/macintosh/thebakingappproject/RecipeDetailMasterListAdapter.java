package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailMasterListAdapter extends RecyclerView.Adapter<RecipeDetailMasterListAdapter.RecipeDetailMasterListViewHolder>{


    private List<String> stringList;

    public RecipeDetailMasterListAdapter(int size){
        stringList = new ArrayList<>(size);
        size++;
        stringList.add("Ingredients");

        for(int i=1; i< size; i++){

            stringList.add("Step " + i);
        }
    }

    @NonNull
    @Override
    public RecipeDetailMasterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.main_recipe_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem,viewGroup,false);
        RecipeDetailMasterListViewHolder viewHolder = new RecipeDetailMasterListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailMasterListViewHolder recipeDetailMasterListViewHolder, int i) {
        String textFieldName = stringList.get(i);
        recipeDetailMasterListViewHolder.textView.setText(textFieldName);
    }


    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class RecipeDetailMasterListViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public RecipeDetailMasterListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipeTitleTv);
        }
    }
}
