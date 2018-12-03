package com.example.macintosh.thebakingappproject;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface OnImageClickListener {
    void onItemClicked(int pos, Recipe recipe);
    void onNextPressed(int nextposition);
    void onBackPressed(int previousPosition);
}
