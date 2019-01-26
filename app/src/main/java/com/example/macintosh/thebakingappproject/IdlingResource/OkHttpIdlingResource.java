package com.example.macintosh.thebakingappproject.IdlingResource;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import androidx.test.espresso.IdlingResource;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

public class OkHttpIdlingResource implements IdlingResource {

    private final String name;
    private final Dispatcher dispatcher;
    volatile ResourceCallback callback;

    @CheckResult
    @NonNull
    @SuppressWarnings("ConstantConditions") // Extra guards as a library.
    public static OkHttpIdlingResource create(@NonNull String name, @NonNull OkHttpClient client) {
        if (name == null) throw new NullPointerException("name == null");
        if (client == null) throw new NullPointerException("client == null");
        return new OkHttpIdlingResource(name, client.dispatcher());
    }


    private OkHttpIdlingResource(String name, Dispatcher dispatcher) {
        this.name = name;
        this.dispatcher = dispatcher;
        dispatcher.setIdleCallback(new Runnable() {
            @Override public void run() {
                ResourceCallback callback = OkHttpIdlingResource.this.callback;
                if (callback != null) {
                    callback.onTransitionToIdle();
                }
            }
        });
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isIdleNow() {
        return dispatcher.runningCallsCount() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
