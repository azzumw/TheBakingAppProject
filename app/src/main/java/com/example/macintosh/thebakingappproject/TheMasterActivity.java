package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Step;

import java.util.ArrayList;

public class TheMasterActivity extends AppCompatActivity implements OnImageClickListener{

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_master);

        //TODO 2: get data from MainActivity
        Intent intent = getIntent();
        Bundle bundle  = intent.getParcelableExtra("rBundle");


        //TODO 3: contain RecipeDetailMasterListFragment

            fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            RecipeDetailMasterListFragment recipeDetailMasterListFragment = new RecipeDetailMasterListFragment();
            recipeDetailMasterListFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,recipeDetailMasterListFragment,"recipeDetailMasterListFragmentTag").commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
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

            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment,"ingredientsFragmentTag").addToBackStack(null).commit();

        }
        else{
            //TODO 7: pass bundle to Step Fragment
            replaceStepsDetailFragment(pos - 1, (ArrayList<Step>) recipe.getSteps());
        }
    }

    private void replaceStepsDetailFragment(int pos, ArrayList<Step> stepArrayList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stepsList", stepArrayList);
        bundle.putInt("currentposition",pos);

        //TODO 4: contain StepsDetailFragment
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.setArguments(bundle);

        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment,"stepsDetailFragmentTag").addToBackStack(null).commit();
    }

    @Override
    public void onNextPressed(int nextposition, ArrayList<Step> stepArrayList) {
        replaceStepsDetailFragment(nextposition, stepArrayList);
    }

    @Override
    public void onBackPressed(int previousPosition, ArrayList<Step> stepArrayList) {
        replaceStepsDetailFragment(previousPosition, stepArrayList);
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

