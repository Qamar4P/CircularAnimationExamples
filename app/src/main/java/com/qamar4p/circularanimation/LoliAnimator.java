package com.qamar4p.circularanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qamar on 7/5/2017.
 */

public class LoliAnimator {

    private final View mView;
    private final AnimatorSet animatorSet;

    public LoliAnimator(View view){
        mView = view;
        animatorSet = animRotate(150,0,55,new AnimatorListenerAdapter(){
            @Override public void onAnimationEnd(Animator animation) {
                animRotate(150,55, 35,new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        animRotate(180, 35, 55,new AnimatorListenerAdapter(){
                            @Override public void onAnimationEnd(Animator animation) {
                                animRotate(180, 55, 0,null).start();
                            }
                        }).start();;
                    }
                }).start();;
            }
        });
    }

    private AnimatorSet animRotate(final int duration, final float fromDegree, final float toDegree, AnimatorListenerAdapter listenerAdapter) {
        AnimatorSet animatorSet = new AnimatorSet();
        RotateAnimation rotation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animatorSet.playTogether(ObjectAnimator.ofFloat(mView,"rotation", fromDegree, toDegree));
        animatorSet.setDuration(duration);
        if (listenerAdapter != null) {
            animatorSet.addListener(listenerAdapter);
        }
        return animatorSet;
    }

    public void play(){
        mView.setPivotX(15);
        mView.setPivotY(20);
        animatorSet.start();
    }
    public void cancel(){
        animatorSet.cancel();
    }
}
