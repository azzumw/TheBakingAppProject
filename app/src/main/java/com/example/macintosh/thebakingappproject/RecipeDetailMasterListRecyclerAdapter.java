package com.example.macintosh.thebakingappproject;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailMasterListRecyclerAdapter extends RecyclerView.Adapter<RecipeDetailMasterListRecyclerAdapter.RecipeDetailMasterListViewHolder> {
    private OnImageClickListener onImageClickListener;

    private List<String> stringList;
    private Context context;
    public RecipeDetailMasterListRecyclerAdapter(Context context,int size, OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
        this.context = context;
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
        stringList.add(context.getString(R.string.INGREDIENTS_TEXT));
        for(int i=0; i < size-1; i++){
            if(i == 0){
                stringList.add(context.getString(R.string.RECIPE_INTRODUCTION_TEXT));
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
