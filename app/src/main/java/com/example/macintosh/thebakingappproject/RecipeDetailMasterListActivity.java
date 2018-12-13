package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macintosh.thebakingappproject.Models.Recipe;

public class RecipeDetailMasterListActivity extends AppCompatActivity {

    private Recipe mRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_master_list);

        Intent intent = getIntent();

        mRecipe = intent.getParcelableExtra("recipe");

        String recipeName = mRecipe.getName();

        getSupportActionBar().setTitle(recipeName);

    }



//    public void onItemClicked(int pos,Recipe recipe) {

//        Intent intent = new Intent(this, StepIngredDetailActivity.class);
//        Bundle bundle = new Bundle();

//        if (pos == 0) {
            //pass ingredient list/data
//            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
//            bundle.putParcelableArrayList("ingredients",ingredients);
//        }
//        else{
            //pass steps data
//            Step step = recipe.getSteps().get(pos-1);
//            bundle.putParcelable("step", step);
//        }

//        intent.putExtra("bundle",bundle);

//        startActivity(intent);
//    }
}
