package com.meshach.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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

    public void scaleAnimation(EditText view) {

        /*Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //new ScaleAnimation(0, -500, 1, 1); //REMOVES THE EDITTEXT
        scaleAnimation.setDuration(750);
        scaleAnimation.setFillAfter(true);
        editText.startAnimation(scaleAnimation);*/

        view.animate()
                .scaleX(3.5f) // Scale up X axis to 1.2 times
                .scaleY(3.5f) // Scale up Y axis to 1.2 times
                .setStartDelay(1)
                .setDuration(600) // Duration of the scale animation
                .setInterpolator(new AccelerateDecelerateInterpolator()) // Interpolator for smooth effect
                .start();
    }


}
