package com.example.macintosh.thebakingappproject;

import com.example.macintosh.thebakingappproject.IdlingResource.EspressoIdlingResource;
import com.example.macintosh.thebakingappproject.IdlingResource.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//    Random rand = new Random();

//    int random_num = rand.nextInt(3);


//    private IdlingResource idlingResource;

//    @Before
//    public void registerIdlingResource(){
//        idlingResource =  mainActivityTestRule.getActivity().getIdlingResource();
//
//        Espresso.registerIdlingResources(idlingResource);
//    }

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void scrollToPosition_mainRecipeList(){
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.scrollToPosition(1));
//
//        onView(allOf(withId(R.id.mainRecipeRV), isDisplayed())).perform(RecyclerViewActions
//                .actionOnItem(hasDescendant(withText("Brownies")), click()));

    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

//
//    @After
//    public void unRegisterIdlingResource(){
//        if(idlingResource!= null){
//            Espresso.unregisterIdlingResources(idlingResource);
//        }
//    }
}
