package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.List;

public class RecipeDetailMasterListActivity extends AppCompatActivity{

    private Recipe recipe;
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
