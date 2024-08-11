package com.meshach.tictactoe;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testGameBoardInitialization() {
        // Launch MainActivity with Intent extras
        ActivityScenario<MainActivity> scenario = activityScenarioRule.getScenario();
        scenario.onActivity(activity -> {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("board", 3);
            intent.putExtra("player1", "X");
            intent.putExtra("vsCPU", true);
            activity.startActivity(intent);
        });

        // Check that the game board is displayed
        //onView(withId(R.id.)).check(ViewAssertions.matches(isDisplayed()));

        // Optionally, interact with the game board
        //onView(withId(R.id.gameBoard)).perform(click());
    }



}