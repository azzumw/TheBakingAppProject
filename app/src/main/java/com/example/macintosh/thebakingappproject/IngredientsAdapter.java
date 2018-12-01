package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Ingredient;

import java.util.ArrayList;

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {
    public IngredientsAdapter(@NonNull Context context, @NonNull ArrayList<Ingredient> ingredientArrayList) {
        super(context, 0, ingredientArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.main_recipe_list_item,parent,false);
        }

        Ingredient ingredient = getItem(position);

        TextView textView =  listItemView.findViewById(R.id.recipeTitleTv);

        textView.setText(ingredient.getIngredient());

        return listItemView;
    }
}
