package com.example.macintosh.thebakingappproject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    Random  random = new Random();
    int rand = random.nextInt(3);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToBrowniesAndClick() {

        onView(allOf(withId(R.id.mainRecipeRV), isDisplayed())).perform(RecyclerViewActions
                .actionOnItem(hasDescendant(withText("Brownies")), click()));
    }

    @Test
    public void scrollToRandomListItemAndClick(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void test_recipe_introduction_step_exist(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Recipe Introduction")),click()));
    }

    @Test
    public void test_ingredient_text_exist(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Ingredients")),click()));
    }
}
