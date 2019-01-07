package com.example.macintosh.thebakingappproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
}
