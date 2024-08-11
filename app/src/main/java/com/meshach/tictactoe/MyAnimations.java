package com.meshach.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;

public class MyAnimations {

    public void fadeOut (View view) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

        fadeOut.setDuration(1000);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // We wanna set the view to GONE, after it's fade out. so it actually disappear from the layout & don't take up space.
                view.setVisibility(View.INVISIBLE);
            }
        });

        fadeOut.start();
    }

    public void fadeIn(View view) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(1000);

        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0);
            }
        });

        fadeIn.start();
    }

    public void fadeInActivity(Activity activity) {
        // Get the decor view of the activity, which represents the entire window
        final View decorView = activity.getWindow().getDecorView();

        // Create the fade-in animator
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(decorView, "alpha", 0f, 1f);
        fadeIn.setDuration(1000); // Duration of 1 second

        // Add a listener to handle the visibility and alpha properties
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                decorView.setVisibility(View.VISIBLE);
                decorView.setAlpha(0f); // Start from transparent
            }
        });

        // Start the fade-in animation
        fadeIn.start();
    }

    public void fadeOutActivity(Activity activity) {
        // Get the decor view of the activity, which represents the entire window
        final View decorView = activity.getWindow().getDecorView();

        // Create the fade-in animator
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(decorView, "alpha", 1f, 0f);
        fadeOut.setDuration(1000); // Duration of 1 second

        // Add a listener to handle the visibility and alpha properties
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                decorView.setVisibility(View.VISIBLE);
                decorView.setAlpha(0f); // Start from transparent
            }
        });

        // Start the fade-in animation
        fadeOut.start();
    }

    public void scaleAnimation( EditText view) {
        view.animate()
                .scaleX(3.5f) // Scale up X axis to 3.5 times
                .scaleY(3.5f) // Scale up Y axis to 3.5 times
                .setStartDelay(1)
                .setDuration(600) // Duration of the scale animation
                .setInterpolator(new AccelerateDecelerateInterpolator()) // Interpolator for smooth effect
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the scale back to the original size
                        view.animate()
                                .scaleX(1.0f) // Scale back X axis to original size
                                .scaleY(1.0f) // Scale back Y axis to original size
                                .setDuration(600) // Duration of the revert animation
                                .setInterpolator(new AccelerateDecelerateInterpolator()) // Interpolator for smooth effect
                                .start();
                    }
                })
                .start();
    }

    public void blinkAnimation(View view) {
        Animation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(500); // duration - half a second
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(5); // repeat 5 times
        view.startAnimation(blink);
    }


}
