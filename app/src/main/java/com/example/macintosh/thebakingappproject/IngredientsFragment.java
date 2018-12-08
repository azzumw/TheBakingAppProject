package com.example.macintosh.thebakingappproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {

    public IngredientsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ingredients_list,container,false);


        ListView listView = rootview.findViewById(R.id.ingredientsListView);


        Bundle bundle = getArguments();

        if(bundle!=null){


            ArrayList<Ingredient> ingredients = bundle.getParcelableArrayList("ingredients");

            IngredientsAdapter arrayAdapter = new IngredientsAdapter(getContext(),ingredients);

            listView.setAdapter(arrayAdapter);
        }

        return rootview;

    }
}
