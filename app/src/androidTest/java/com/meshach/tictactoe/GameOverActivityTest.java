package com.meshach.tictactoe;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.meshach.tictactoe._GameOver.GameOverActivity;
import com.meshach.tictactoe.MainActivity;

import org.junit.Before;
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
    public ActivityScenarioRule<GameOverActivity> activityScenarioRule =
            new ActivityScenarioRule<>(GameOverActivity.class);

    @Test
    public void testPlayerWinDisplay() {
        // Simulate the winning scenario by passing the required intent extras
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.getBooleanExtra("newRound", false);
        intent.getIntExtra("board", 3);  // Assuming 3x3 board
        intent.putExtra("player1", "X");
        intent.getBooleanExtra("vsCPU", true);

        try (ActivityScenario<GameOverActivity> scenario = ActivityScenario.launch(intent)) {
            // Check if the winning message is displayed correctly
            onView(withId(R.id.textView01)).check(matches(withText("YOU WON!!")));
            onView(withId(R.id.textView03)).check(matches(withText("X")));
        }
    }

    @Test
    public void testLoseToCPUDisplay() {
        // Simulate the losing scenario by passing the required intent extras
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.getBooleanExtra("newRound", false);
        intent.getIntExtra("board", 3);  // Assuming 3x3 board
        intent.putExtra("player1", "X");
        intent.getBooleanExtra("vsCPU", true);

        try (ActivityScenario<GameOverActivity> scenario = ActivityScenario.launch(intent)) {
            // Check if the losing message is displayed correctly
            onView(withId(R.id.textView01)).check(matches(withText("YOU LOST!")));
            onView(withId(R.id.textView03)).check(matches(withText("O")));
        }
    }

    // Skipping the commented out test methods that were previously dependent on ActivityTestRule

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Set up any required preconditions here
    }

    @Test
    public void testBoardInitialization() {
        // Create an Intent with the required extras to start MainActivity
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.getBooleanExtra("newRound", false);
        intent.getIntExtra("board", 3);  // Assuming 3x3 board
        intent.putExtra("player1", "X");
        intent.getBooleanExtra("vsCPU", true);

        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent)) {
            // Check if the table layout is displayed
            onView(withId(R.id.gameBoard)).check(matches(isDisplayed()));

            // Check if the slider mode is initialized correctly
            onView(withId(R.id.sliderMode)).check(matches(isDisplayed()));

            // Add more checks for the specific EditTexts, Buttons, or TextViews
            onView(withId(R.id.restartBtn)).check(matches(withText("Restart")));

            // You can also check if the first cell in the board is empty
            onView(withId(R.id.gameBoard)).check(matches(isDisplayed()));
        }
    }
}
