package com.qamar4p.circularanimation.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qamar on 7/5/2017.
 */

public class ScaleBouncingAnimator {

    private final View[] mViews;
    private final AnimatorSet animatorSet;

    public ScaleBouncingAnimator(View... views){
        if(views == null || views.length ==0) throw new RuntimeException("Null view error");
        mViews = views;
        animatorSet = animScale(150,1,1.4f,new AnimatorListenerAdapter(){
            @Override public void onAnimationEnd(Animator animation) {
                animScale(150,1.4f, 1f,new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        animScale(180, 1f, 1.3f,new AnimatorListenerAdapter(){
                            @Override public void onAnimationEnd(Animator animation) {
                                animScale(180, 1.3f, 1f,null).start();
                            }
                        }).start();;
                    }
                }).start();;
            }
        });
    }

    private AnimatorSet animScale(final int duration, final float initialScale, final float lastScale, AnimatorListenerAdapter listenerAdapter) {
        List<Animator> animators = new ArrayList<>();
        for (View view : mViews) {
            animators.add(ObjectAnimator.ofFloat(view,"scaleX", initialScale, lastScale));
            animators.add(ObjectAnimator.ofFloat(view,"scaleY", initialScale, lastScale));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.setDuration(duration);
        if (listenerAdapter != null) {
            animatorSet.addListener(listenerAdapter);
        }
        return animatorSet;
    }

    public void play(){
        animatorSet.start();
    }
}
