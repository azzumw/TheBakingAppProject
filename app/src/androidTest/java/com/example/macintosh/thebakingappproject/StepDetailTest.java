package com.example.macintosh.thebakingappproject;


import junit.extensions.ActiveTestSuite;

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

@RunWith(AndroidJUnit4.class)
public class StepDetailTest {

    Random random = new Random();
    int rand = random.nextInt(3);

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_simple_player_view_exist_for_recipe(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Recipe Introduction")),click()));
        onView(withId(R.id.simpleExoPlayerView)).check(matches(isDisplayed()));
    }

    @Test
    public void test_next_button(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Recipe Introduction")),click()));
        onView(withId(R.id.simpleExoPlayerView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(withText("Recipe Introduction")));
        onView(withId(R.id.nextbtn)).perform(click());
        onView(withId(R.id.empty_img_view)).check(matches(isDisplayed()));
    }

    @Test
    public void test_play_video(){
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.actionOnItemAtPosition(rand,click()));
        onView(withId(R.id.masterListRecyclerView)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Recipe Introduction")),click()));
        onView(withId(R.id.simpleExoPlayerView)).check(matches(isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.exo_play)).perform(click());
        try {
            Thread.sleep(16000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
