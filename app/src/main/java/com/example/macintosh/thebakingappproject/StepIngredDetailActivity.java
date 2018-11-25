package com.example.macintosh.thebakingappproject;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepIngredDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_ingred_detail);

        StepIngredDetailFragment stepIngredDetailFragment = new StepIngredDetailFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.fragmentContainerFL,stepIngredDetailFragment).commit();
    }
}
