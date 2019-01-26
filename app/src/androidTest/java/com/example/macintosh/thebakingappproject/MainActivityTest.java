package com.example.macintosh.thebakingappproject;

import com.example.macintosh.thebakingappproject.IdlingResource.EspressoIdlingResource;
import com.example.macintosh.thebakingappproject.IdlingResource.OkHttpIdlingResource;
import com.example.macintosh.thebakingappproject.IdlingResource.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import okhttp3.OkHttpClient;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//    Random rand = new Random();

//    int random_num = rand.nextInt(3);


    private IdlingResource idlingResource;

//    @Before
//    public void registerIdlingResource(){
//
////        idlingResource =  mainActivityTestRule.getActivity().getIdlingResource();
//
//        Espresso.registerIdlingResources(idlingResource);
//    }

//    @Before
//    public void registerIdlingResource() {
//        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
//        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
//            @Override
//            public void perform(MainActivity activity) {
//                idlingResource = activity.getIdlingResource();
//                // To prove that the test fails, omit this call:
//                IdlingRegistry.getInstance().register(idlingResource);
//            }
//        });
//    }


    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void scrollToPosition_mainRecipeList() {
        OkHttpClient client = new OkHttpClient();
        IdlingResource idlingResource = OkHttpIdlingResource.create("Ok!", client);
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.mainRecipeRV)).perform(RecyclerViewActions.scrollToPosition(1));
//        onView(withText("Brownies")).check(matches(isDisplayed()));

//        onView(allOf(withId(R.id.mainRecipeRV), isDisplayed())).perform(RecyclerViewActions
//                .actionOnItem(hasDescendant(withText("Brownies")), click()));

    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

//    @After
//    public void unregisterIdlingResource() {
//        if (idlingResource != null) {
////            IdlingRegistry.getInstance().unregister(idlingResource);
//            Espresso.unregisterIdlingResources(idlingResource);
//        }
//    }

}
