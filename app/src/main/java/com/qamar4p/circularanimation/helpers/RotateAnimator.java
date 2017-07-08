package com.qamar4p.circularanimation.helpers;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by Qamar on 7/5/2017.
 */

public class RotateAnimator {

    private final View mView;
    private final RotateAnimation rotate;

    public RotateAnimator(View view,int fromDegree, int toDegree){
        mView = view;
        rotate = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF,
                0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
    }
    public void play(int forTime){
        rotate.setDuration(forTime);
        mView.startAnimation(rotate);
    }
}
