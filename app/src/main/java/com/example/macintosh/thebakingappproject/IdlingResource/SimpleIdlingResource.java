package com.example.macintosh.thebakingappproject.IdlingResource;


import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.test.espresso.IdlingResource;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;


public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mCallback;

    private final String mResourceName;

    // Idleness is controlled with this boolean.
//    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Creates a SimpleCountingIdlingResource
     *
     * @param resourceName the resource name this resource should report to Espresso.
     */
    public SimpleIdlingResource(String resourceName) {
        mResourceName = checkNotNull(resourceName);
    }



    @Override
    public String getName() {
        return mResourceName;
//        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
//        mIsIdleNow.get();
        return counter.get() == 0;

    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

//    /**
//     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
//     * @param isIdleNow false if there are pending operations, true if idle.
//     */
//    public void setIdleState(boolean isIdleNow) {
//        mIsIdleNow.set(isIdleNow);
//        if (isIdleNow && mCallback != null) {
//            mCallback.onTransitionToIdle();
//        }
//    }


    /**
     * Increments the count of in-flight transactions to the resource being monitored.
     */
    public void increment() {
        counter.getAndIncrement();
    }

    /**
     * Decrements the count of in-flight transactions to the resource being monitored.
     *
     * If this operation results in the counter falling below 0 - an exception is raised.
     *
     * @throws IllegalStateException if the counter is below 0.
     */
    public void decrement() {
        int counterVal = counter.decrementAndGet();
        if (counterVal == 0) {
            // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
            if (null != mCallback) {
                mCallback.onTransitionToIdle();
            }
        }

        if (counterVal < 0) {
            throw new IllegalArgumentException("Counter has been corrupted!");
        }
    }
}
