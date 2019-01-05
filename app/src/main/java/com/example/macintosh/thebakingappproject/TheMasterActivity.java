package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Step;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TheMasterActivity extends AppCompatActivity implements OnImageClickListener,SharedPreferences.OnSharedPreferenceChangeListener{

    private String menuOptionTitle;
    private FragmentManager fragmentManager;
    private Recipe recipe;
    private ArrayList<Step> stepArrayList;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_master);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        MenuItem item = menu.findItem(R.id.pin_to_widget_id);

        int recipIDFromPref = preferences.getInt(getString(R.string.RECIPE_ID),-1);
        if(recipe.getId() == recipIDFromPref){
            menuOptionTitle = getString(R.string.unpin);
        }else {
            menuOptionTitle = getString(R.string.pin);
        }

        item.setTitle(menuOptionTitle);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.pin_to_widget_id){
            //TODO: do something
            //check if this recipe exists in the sharedPref
            //if exists, then set title to 'Unpin' and remove/replace recipe object.
            //else, set title to 'pin' and add recipe object to sharedpref
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            int recipIDFromPref = preferences.getInt(getString(R.string.RECIPE_ID),-1);
            if(recipe.getId() == recipIDFromPref){
                menuOptionTitle = getString(R.string.pin);
                editor.remove(getString(R.string.JSON_KEY)).apply();
                editor.remove(getString(R.string.RECIPE_ID)).apply();
                Toast.makeText(this, "Recipe UNPINNED", Toast.LENGTH_SHORT).show();
            }else{

                menuOptionTitle = getString(R.string.unpin);
                String json = getJsonString(recipe);
                editor.putString(getString(R.string.JSON_KEY),json);
                editor.putInt(getString(R.string.RECIPE_ID),recipe.getId());
                editor.apply();
                Toast.makeText(this, "Recipe Pinned", Toast.LENGTH_SHORT).show();
            }
            item.setTitle(menuOptionTitle);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.masteractivity_menu,menu);
        return true;
    }
    private String getJsonString(Recipe recipe){ ;
        Gson gson = new Gson();
        return gson.toJson(recipe);
    }

    private Recipe getRecipeFromJson(String json){
        Gson gson = new Gson();
        Recipe recipeFromJson = gson.fromJson(json,Recipe.class);
        return recipeFromJson;
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
            replaceStepsDetailFragment(pos - 1);
        }
    }


    private void replaceStepsDetailFragment(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt("stepArraySize",stepArrayList.size());
        bundle.putInt("currentposition",pos);
        //pass the next step
        bundle.putParcelable("theNextStep",stepArrayList.get(pos));
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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        WidgetUpdateService.startActionUpdateAppWidgets(this);
    }
}
