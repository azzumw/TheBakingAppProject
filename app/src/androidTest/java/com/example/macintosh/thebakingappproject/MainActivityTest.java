package com.example.macintosh.thebakingappproject;




import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);


//    @Before
//    public void prepare() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void scrollToPosition_mainRecipeList() {

        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.scrollToPosition(1));
//        onView(withText("Brownies")).check(matches(isDisplayed()));

//        onView(allOf(withId(R.id.mainRecipeRV))).perform(RecyclerViewActions.scrollToPosition(1));
//        onView(withText("Brownies")).check(matches(isDisplayed()));

//        onView(allOf(withId(R.id.mainRecipeRV), isDisplayed())).perform(RecyclerViewActions
//                .actionOnItem(hasDescendant(withText("Brownies")), click()));

    }


}
