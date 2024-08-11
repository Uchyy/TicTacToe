package com.meshach.tictactoe;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.meshach.tictactoe.GameOverActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class GameOverActivityTest {

    @Rule
    public ActivityScenarioRule<GameOverActivity> activityRule =
            new ActivityScenarioRule<>(GameOverActivity.class);

    @Test
    public void testPlayerWinDisplay() {
        // Simulate the winning scenario by passing the required intent extras
        Intent intent = new Intent();
        intent.putExtra("playerSymbol", "X");
        intent.putExtra("isCPU", false);

        try (ActivityScenario<GameOverActivity> scenario = ActivityScenario.launch(intent)) {
            // Check if the winning message is displayed correctly
            //onView(withId(R.id.))
            onView(withId(R.id.textView01)).check(matches(withText("YOU WON!!")));
            onView(withId(R.id.textView03)).check(matches(withText("X")));
        }
    }

    @Test
    public void testLoseToCPUDisplay() {
        // Simulate the losing scenario by passing the required intent extras
        Intent intent = new Intent();
        intent.putExtra("playerSymbol", "O");
        intent.putExtra("isCPU", true);

        try (ActivityScenario<GameOverActivity> scenario = ActivityScenario.launch(intent)) {
            // Check if the losing message is displayed correctly
            onView(withId(R.id.textView01)).check(matches(withText("YOU LOST!")));
            onView(withId(R.id.textView03)).check(matches(withText("O")));
        }
    }

    /*@Test
    public void testQuitButtonFunctionality() {
        // Click on the Quit button and verify the intended behavior
        onView(withId(R.id.quitBtn)).perform(click());

        // Since there's a delay before the intent is launched, you may need to wait
        onView(isRoot()).perform(waitFor(2000)); // Custom helper method to wait for 2 seconds

        // Verify that the StartUp activity is started
        intended(hasComponent(StartUp.class.getName()));
    }

    @Test
    public void testAgainButtonFunctionality() {
        // Click on the Again button and verify the intended behavior
        onView(withId(R.id.againBtn)).perform(click());

        // Since there's a delay before the intent is launched, you may need to wait
        onView(isRoot()).perform(waitFor(2000)); // Custom helper method to wait for 2 seconds

        // Verify that the MainActivity is started
        intended(hasComponent(MainActivity.class.getName()));
    }*/
}
