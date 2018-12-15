package com.example.macintosh.thebakingappproject;

import com.example.macintosh.thebakingappproject.Models.Recipe;
import com.example.macintosh.thebakingappproject.Models.Step;

import java.util.ArrayList;

public interface OnImageClickListener {
    void onItemClicked(int pos, Recipe recipe);
    void onNextPressed(int nextposition);
    void onBackPressed(int previousPosition);
}
