package com.example.macintosh.thebakingappproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macintosh.thebakingappproject.Models.Ingredient;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    public IngredientsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ingredients_list,container,false);

        RecyclerView recyclerView = rootview.findViewById(R.id.ingredientsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle = getArguments();


        if(bundle!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Ingredients");

            ArrayList<Ingredient> ingredients = bundle.getParcelableArrayList(getString(R.string.INGREDIENT_ARRAY_BUNDLE_KEY));

            IngredientsRecyclerAdapter ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(ingredients);

            recyclerView.setAdapter(ingredientsRecyclerAdapter);
        }

        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);
    }
}
