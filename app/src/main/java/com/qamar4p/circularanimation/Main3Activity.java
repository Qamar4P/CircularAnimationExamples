package com.qamar4p.circularanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qamar4p.circularanimation.custom_views.CircleRevealHideView;
import com.qamar4p.circularanimation.helpers.RotateAnimator;
import com.qamar4p.circularanimation.helpers.ScaleBouncingAnimator;
import com.qamar4p.circularanimation.helpers.StickAnimator;

public class Main3Activity extends AppCompatActivity {

    private TextView textView1;
    private View textView2;
    private View textView3;
    private CircleRevealHideView circleRevealView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        circleRevealView = findViewById(R.id.circleRevealView);
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            public boolean isCircleShowed;

            @Override
            public void onClick(View view) {

                if (isCircleShowed) {
                    circleRevealView.hideAnimate();
                }else {
                    circleRevealView.showAnimate();
                }
                isCircleShowed = !isCircleShowed;

//                Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_bounce);
//                textView1.startAnimation(an);

//                ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1.5f),
//                        PropertyValuesHolder.ofFloat("scaleY", 1.5f));
//                ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1f),
//                        PropertyValuesHolder.ofFloat("scaleY", 1f));
//                ObjectAnimator scaleUp2 = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1.2f),
//                        PropertyValuesHolder.ofFloat("scaleY", 1.2f));
//
//                AnimatorSet scaleBounceAnim = new AnimatorSet();
//                scaleBounceAnim.setInterpolator(new DecelerateInterpolator());
//                scaleBounceAnim.setDuration(800);
//                scaleBounceAnim.playSequentially(scaleUp,scaleDown/*,scaleUp2,scaleDown*/);
//                scaleDown.setTarget(textView1);
//                scaleDown.start();

                new ScaleBouncingAnimator(textView1).play();

                //-----------------
//                AnimatorSet set = new AnimatorSet(); //this is your animation set.
//                //add as many Value animator to it as you like
//
//                ValueAnimator scaleUp = ValueAnimator.ofFloat(1,(float)1.3);
////                scaleUp.setDuration(800);
////                scaleUp.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
//                scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        Float newValue = (Float) valueAnimator.getAnimatedValue();
//                        textView1.setScaleY(newValue);
//                        textView1.setScaleX(newValue);
//                    }
//                });
//
//                ValueAnimator scaleDown = ValueAnimator.ofFloat((float)1.3,1);
////                scaleDown.setDuration(800);
////                scaleDown.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
//                scaleDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        Float newValue = (Float) valueAnimator.getAnimatedValue();
//                        textView1.setScaleY(newValue);
//                        textView1.setScaleX(newValue);
//                    }
//                });
//                ValueAnimator scaleUp2 = ValueAnimator.ofFloat(1,(float)1.2);
////                scaleUp.setDuration(800);
////                scaleUp.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
//                scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        Float newValue = (Float) valueAnimator.getAnimatedValue();
//                        textView1.setScaleY(newValue);
//                        textView1.setScaleX(newValue);
//                    }
//                });
//
//                ValueAnimator scaleDown2 = ValueAnimator.ofFloat((float)1.2,1);
////                scaleDown.setDuration(800);
////                scaleDown.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
//                scaleDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        Float newValue = (Float) valueAnimator.getAnimatedValue();
//                        textView1.setScaleY(newValue);
//                        textView1.setScaleX(newValue);
//                    }
//                });
//                set.setDuration(800);
////                set.setInterpolator(new BounceInterpolator());
////                set.play(scaleUp);
//                set.setInterpolator(new AccelerateInterpolator());
//                set.playSequentially(scaleUp,scaleDown,scaleUp2,scaleDown2);
//                set.start();
//                Toast.makeText(getApplicationContext(),"H",Toast.LENGTH_SHORT).show();

                new StickAnimator(findViewById(R.id.loliView)).play();
                new RotateAnimator(textView1,0,360).play(800);
                new ScaleBouncingAnimator(textView2,textView3).play();

//                ViewAnimator
//                        .animate(textView2,textView3)
//                        .scale(1,1.4f)
//                        .duration(150)
//                        .thenAnimate(textView2,textView3)
//                        .scale(1.4f,1)
//                        .duration(150)
//                        .thenAnimate(textView2,textView3)
//                        .scale(1,1.3f)
//                        .duration(180)
//                        .thenAnimate(textView2,textView3)
//                        .scale(1.3f,1)
//                        .duration(180)
//                        .start();

            }
        });
    }
}
