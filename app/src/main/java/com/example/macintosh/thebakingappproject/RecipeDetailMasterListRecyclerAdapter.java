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

public class RecipeDetailMasterListRecyclerAdapter extends RecyclerView.Adapter<RecipeDetailMasterListRecyclerAdapter.RecipeDetailMasterListViewHolder> {
    private OnImageClickListener onImageClickListener;

    private List<String> stringList;

    public RecipeDetailMasterListRecyclerAdapter(int size, OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;

        stringList = new ArrayList<>();
        size++;
        fillArrayList(size);
    }

    @NonNull
    @Override
    public RecipeDetailMasterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_detail_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem,viewGroup,false);
        RecipeDetailMasterListViewHolder recipeDetailMasterListViewHolder = new RecipeDetailMasterListViewHolder(view);
        return recipeDetailMasterListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailMasterListViewHolder recipeDetailMasterListViewHolder, int i) {

            recipeDetailMasterListViewHolder.recipeDetailItemTitle.setText(stringList.get(i));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    private void fillArrayList(int size){
        stringList.add("Ingredients");
        for(int i=0; i < size-1; i++){
            if(i == 0){
                stringList.add("Recipe Introduction");
                continue;
            }
            stringList.add("Step " + i);
        }
    }

    class RecipeDetailMasterListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView recipeDetailItemTitle;
        public RecipeDetailMasterListViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeDetailItemTitle = itemView.findViewById(R.id.recipeTitleTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onImageClickListener.onItemClicked(adapterPosition);
        }
    }
}
