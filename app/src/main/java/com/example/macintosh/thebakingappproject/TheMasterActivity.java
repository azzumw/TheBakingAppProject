package com.example.macintosh.thebakingappproject;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
        recipe = bundle.getParcelable(getString(R.string.RECIPE_BUNDLE_KEY));
        stepArrayList = (ArrayList<Step>) recipe.getSteps();

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.tab_root_linear_layout)!= null){
            mTwoPane = true;
            if(savedInstanceState==null){
                Bundle ingredBundle = new Bundle();
                ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
                ingredBundle.putString(getString(R.string.RECIPE_TITLE_KEY),recipe.getName());
                ingredBundle.putParcelableArrayList(getString(R.string.INGREDIENT_ARRAY_BUNDLE_KEY),ingredients);
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(ingredBundle);
                fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,ingredientsFragment,getString(R.string.INGREDIENT_FRAGMENT_TAG)).commit();
            }


        }else{
            mTwoPane = false;

            if (savedInstanceState == null) {
                RecipeDetailMasterListFragment recipeDetailMasterListFragment = new RecipeDetailMasterListFragment();
                recipeDetailMasterListFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.fragmentContainerFLMasterAct,recipeDetailMasterListFragment,getString(R.string.RECIPE_DETAIL_MASTERLIST_FRAG_TAG)).commit();
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
            /*check if this recipe exists in the sharedPref
            if exists, then set title to 'Unpin' and remove/replace recipe object.
            else, set title to 'pin' and add recipe object to sharedpref*/
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            int recipIDFromPref = preferences.getInt(getString(R.string.RECIPE_ID),-1);
            if(recipe.getId() == recipIDFromPref){
                menuOptionTitle = getString(R.string.pin);
                editor.remove(getString(R.string.JSON_KEY)).apply();
                editor.remove(getString(R.string.RECIPE_ID)).apply();
                Toast.makeText(this, R.string.RECIPE_UNPINNED_TOAST, Toast.LENGTH_SHORT).show();
            }else{

                menuOptionTitle = getString(R.string.unpin);
                String json = getJsonString(recipe);
                editor.putString(getString(R.string.JSON_KEY),json);
                editor.putInt(getString(R.string.RECIPE_ID),recipe.getId());
                editor.apply();
                Toast.makeText(this, R.string.RECIPE_PINNED_TOAST, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClicked(int pos) {
        Bundle bundle = new Bundle();

        if (pos == 0) {
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
            bundle.putString("Title",recipe.getName());
            bundle.putParcelableArrayList(getString(R.string.INGREDIENT_ARRAY_BUNDLE_KEY),ingredients);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            if(mTwoPane){
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment,getString(R.string.INGREDIENT_FRAGMENT_TAG)).commit();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,ingredientsFragment,getString(R.string.INGREDIENT_FRAGMENT_TAG)).addToBackStack(null).commit();
            }
        }
        else{
            replaceStepsDetailFragment(pos - 1);
        }
    }


    private void replaceStepsDetailFragment(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.STEP_ARRAY_SIZE_KEY),stepArrayList.size());
        bundle.putInt(getString(R.string.CURRENT_POSITON_KEY),pos);
        //pass the next step
        bundle.putParcelable(getString(R.string.NEXT_STEP_KEY),stepArrayList.get(pos));
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.setArguments(bundle);

        fragmentManager.popBackStack();
        if(mTwoPane){
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment,getString(R.string.STEP_DETAIL_FRAG_TAG)).commit();
        }else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerFLMasterAct,stepsDetailFragment,getString(R.string.STEP_DETAIL_FRAG_TAG)).addToBackStack(null).commit();
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
        sendWidgeUpdate();
    }

    private void sendWidgeUpdate() {
        Intent intent = new Intent(this, WidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ComponentName thisAppWidget = new ComponentName(this, WidgetProvider.class);
        int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(thisAppWidget);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}
