package com.example.macintosh.thebakingappproject;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import androidx.test.espresso.IdlingRegistry;
import okhttp3.OkHttpClient;




public abstract class IdlingResource {

    public static void registerOkHttp(OkHttpClient client){
    }
}
