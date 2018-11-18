package com.example.macintosh.thebakingappproject.Network;

import com.example.macintosh.thebakingappproject.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
