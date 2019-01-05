package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.os.Parcelable;
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
    Recipe recipe;
    ArrayList<Step> stepArrayList;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_master);

        Intent intent = getIntent();
        Bundle bundle  = intent.getParcelableExtra("rBundle");
        recipe = bundle.getParcelable("bundle");
        stepArrayList = (ArrayList<Step>) recipe.getSteps();

        fragmentManager = getSupportFragmentManager();


        if(findViewById(R.id.tab_root_linear_layout)!= null){
            mTwoPane = true;
            if(savedInstanceState==null){
                Bundle ingredBundle = new Bundle();
                ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
                ingredBundle.putString("Title",recipe.getName());
                ingredBundle.putParcelableArrayList("ingredients",ingredients);
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(ingredBundle);
                fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,ingredientsFragment,"ingredientsFragmentTag").commit();
//                fragmentManager.findFragmentByTag("recipeDetailMasterListFragmentTag").setArguments(bundle);
            }


        }else{
            mTwoPane = false;

            if (savedInstanceState == null) {
                RecipeDetailMasterListFragment recipeDetailMasterListFragment = new RecipeDetailMasterListFragment();
                recipeDetailMasterListFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,recipeDetailMasterListFragment,"recipeDetailMasterListFragmentTag").commit();
            }
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onItemClicked(int pos) {
        Bundle bundle = new Bundle();

        if (pos == 0) {
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
            bundle.putString("Title",recipe.getName());
            bundle.putParcelableArrayList("ingredients",ingredients);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            if(mTwoPane){
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment,"ingredientsFragmentTag").commit();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment,"ingredientsFragmentTag").addToBackStack(null).commit();
            }


        }
        else{
            //TODO 7: pass bundle to Step Fragment
            replaceStepsDetailFragment(pos - 1);
        }
    }

    @Override
    public void onItemClicked(int pos, Recipe recipe) {

    }

    private void replaceStepsDetailFragment(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt("stepArraySize",stepArrayList.size());
        bundle.putInt("currentposition",pos);
        //pass the next step
        bundle.putParcelable("theNextStep",stepArrayList.get(pos));
        //TODO 4: contain StepsDetailFragment
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.setArguments(bundle);

        fragmentManager.popBackStack();
        if(mTwoPane){
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment,"stepsDetailFragmentTag").commit();
        }else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment,"stepsDetailFragmentTag").addToBackStack(null).commit();
        }

    }

    @Override
    public void onNextPressed(int nextposition) {
        replaceStepsDetailFragment(nextposition);
    }

    @Override
    public void onBackPressed(int previousPosition) {
        replaceStepsDetailFragment(previousPosition);
    }


}
