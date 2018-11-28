package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDetailMasterListActivity extends AppCompatActivity implements RecipeDetailMasterListFragment.OnImageClickListener{

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


    @Override
    public void onItemClicked(int pos,Recipe recipe) {
        Toast.makeText(this, "Position clicked" + pos, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, StepIngredDetailActivity.class);
        Bundle bundle = new Bundle();

        if (pos == 0) {
            //pass ingredient list/data
            //recipe.getIngredients()
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
//            intent.putParcelableArrayListExtra();
            bundle.putParcelableArrayList("ingredients",ingredients);
        }
        else{
            //pass steps data
            //recipe.getSteps().get(pos-1);
            Steps step = recipe.getSteps().get(pos-1);
            bundle.putParcelable("step", step);
        }

        intent.putExtra("bundle",bundle);

        startActivity(intent);
    }
}
