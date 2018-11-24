package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macintosh.thebakingappproject.Models.Recipe;

public class RecipeDetailMasterListActivity extends AppCompatActivity {


    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_master_list);

        Intent intent = getIntent();

        recipe = intent.getParcelableExtra("recipe");

        String recipeName = recipe.getName();

        getSupportActionBar().setTitle(recipeName);
    }

}
