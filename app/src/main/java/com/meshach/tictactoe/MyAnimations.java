package com.meshach.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

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
                // We wanna set the view to VISIBLE, but with alpha 0. So it appear invisible in the layout.
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0);
            }
        });

        fadeIn.start();
    }
}
