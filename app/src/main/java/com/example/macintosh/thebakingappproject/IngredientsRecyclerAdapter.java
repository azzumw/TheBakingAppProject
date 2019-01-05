package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Ingredient;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.IngredientRecyclerViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientsRecyclerAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem,viewGroup,false);
        IngredientRecyclerViewHolder ingredientRecyclerViewHolder = new IngredientRecyclerViewHolder(view);
        return ingredientRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientRecyclerViewHolder ingredientRecyclerViewHolder, int i) {
        ingredientRecyclerViewHolder.ingredientNameTV.setText(ingredients.get(i).getIngredient());
        ingredientRecyclerViewHolder.ingredientUnitTV.setText(ingredients.get(i).getMeasure());
        ingredientRecyclerViewHolder.ingredientQtyTV.setText(String.valueOf(ingredients.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView ingredientNameTV;
        TextView ingredientUnitTV;
        TextView ingredientQtyTV;

        public IngredientRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ingredientNameTV = itemView.findViewById(R.id.ingred_name_tv);
            this.ingredientQtyTV = itemView.findViewById(R.id.ingred_quantity_tv);
            this.ingredientUnitTV = itemView.findViewById(R.id.ingred_measure_tv);
        }
    }
}
