package com.example.macintosh.thebakingappproject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> menuActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    Random rand = new Random();

    int random_num = rand.nextInt(3);


    @Test
    public void scrollToPosition_mainRecipeList(){
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.scrollToPosition(1));
//
//        onView(allOf(withId(R.id.mainRecipeRV), isDisplayed())).perform(RecyclerViewActions
//                .actionOnItem(hasDescendant(withText("Brownies")), click()));

    }
}
