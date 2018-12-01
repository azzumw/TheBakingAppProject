package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.ArrayList;

public class TheMasterActivity extends AppCompatActivity implements OnImageClickListener{

    FragmentManager fragmentManager;
    RecipeDetailMasterListFragment recipeDetailMasterListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_master);

        //TODO 2: get data from MainActivity
        Intent intent = getIntent();
        Bundle bundle  = intent.getParcelableExtra("rBundle");


        //TODO 3: contain RecipeDetailMasterListFragment

        recipeDetailMasterListFragment = new RecipeDetailMasterListFragment();
        recipeDetailMasterListFragment.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,recipeDetailMasterListFragment).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(fragmentManager.getBackStackEntryCount()==0){
                finish();
            }else{
                fragmentManager.popBackStack();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClicked(int pos, Recipe recipe) {
        Bundle bundle = new Bundle();

        if (pos == 0) {
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
            bundle.putParcelableArrayList("ingredients",ingredients);
            //TODO 6: create Ingredients Fragment
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            //TODO 5: contain IngredientFragment

            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment).addToBackStack(null).commit();

        }
        else{
            //TODO 7: pass bundle to Step Fragment
            Steps step = recipe.getSteps().get(pos-1);
            bundle.putParcelable("step", step);

            //TODO 4: contain StepsDetailFragment
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setArguments(bundle);

            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment).addToBackStack(null).commit();
        }
    }

    /* @Override
    public void onBackPressed() {

        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }*/
}
