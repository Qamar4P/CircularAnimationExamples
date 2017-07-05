package com.qamar4p.circularanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float downX, downY;
    private VelocityTracker velocityTracker;
    private View view1;
    private ButtonProgress buttonPrimary;
    Context context;
    private PopupWindow popupMessage;
    private View layoutOfPopup;
//    private FreqDiffIndicator imageFreqDiffView;
    private FreqDiffIndicator2 imageFreqDiffView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        velocityTracker = VelocityTracker.obtain();

//        view1 = findViewById (R.id.view1);
        findViewById (R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Main2Activity.class));
            }
        });
        findViewById (R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Main3Activity.class));
            }
        });
        buttonPrimary = findViewById (R.id.buttonPrimary);
        imageFreqDiffView = findViewById (R.id.imageFreqDiff);
        imageFreqDiffView.setIcon(R.drawable.lugtuner_animation_circle);
        imageFreqDiffView.setOnClickListener(new View.OnClickListener() {
            public boolean isToLeft = true;

            @Override
            public void onClick(View view) {
                if (isToLeft) {
                    imageFreqDiffView.animateProgress(0.25f);
                }else {
                    imageFreqDiffView.animateProgress(-0.25f);
                }
                isToLeft = !isToLeft;
            }
        });
//        imageFreqDiffView.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));

        buttonPrimary.setIcon(R.mipmap.ic_launcher);
        buttonPrimary.setOnComplete(new Runnable() {
            @Override
            public void run() {
                popupMessage.showAsDropDown(buttonPrimary);

                // The bounds for the delegate view (an ImageButton
                // in this example)
                Rect delegateArea = new Rect();

                // The hit rectangle for the ImageButton
                buttonPrimary.getHitRect(delegateArea);

                // Extend the touch area of the ImageButton beyond its bounds
                // on the right and bottom.
                delegateArea.left = 0;
                delegateArea.top = 0;
                delegateArea.right = context.getResources().getDisplayMetrics().widthPixels;
                delegateArea.bottom = context.getResources().getDisplayMetrics().heightPixels;

                // Instantiate a TouchDelegate.
                // "delegateArea" is the bounds in local coordinates of
                // the containing view to be mapped to the delegate view.
                // "myButton" is the child view that should receive motion
                // events.
                TouchDelegate touchDelegate = new TouchDelegate(delegateArea,
                        layoutOfPopup);

                // Sets the TouchDelegate on the parent view, such that touches
                // within the touch delegate bounds are routed to the child.
                if (View.class.isInstance(buttonPrimary.getParent())) {
                    ((View) buttonPrimary.getParent()).setTouchDelegate(touchDelegate);
                }
                buttonPrimary.setPopupView(layoutOfPopup);
            }
        });
    }



//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_UP){
//
////            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view1.getLayoutParams();
////            view1.setLayoutParams(params);
//            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//            float centerX = displayMetrics.widthPixels / 2;
//            float centerY = displayMetrics.heightPixels / 2;
//            float start_x = centerX+200;
//            float a1y = centerY;
//            float final_x = centerX;
//            float final_y = centerY+200;
//            view1.setX(start_x);
//            view1.setY(a1y);
//            view1.setRotation(0f);
//            velocityTracker.computeCurrentVelocity(1000);
////            final SpringAnimation animationX = new SpringAnimation(view1, SpringAnimation.X,final_x);
//
////            // create a spring with desired parameters
////            SpringForce spring = new SpringForce();
////            // can also be passed directly in the constructor
////            spring.setFinalPosition(final_x);
////            spring.setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY);
////            // optional, default is STIFFNESS_MEDIUM
////            spring.setStiffness(SpringForce.STIFFNESS_LOW);
////            // set your animation's spring
////            animationX.setSpring(spring);
//            // animate!
//
//            final SpringAnimation animationX = SpringAnimationUtil.createSpringAnimation(view1,
//                    SpringAnimation.X, final_x,
//                    SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_NO_BOUNCY);
//            final SpringAnimation animationX2 = SpringAnimationUtil.createSpringAnimation(view1,
//                    SpringAnimation.X, start_x,
//                    SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_NO_BOUNCY);
//            final SpringAnimation animationY = SpringAnimationUtil.createSpringAnimation(view1,
//                    SpringAnimation.Y, final_y,
//                    SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_NO_BOUNCY);
//            final SpringAnimation anim2 = SpringAnimationUtil.createSpringAnimation(view1,
//                    SpringAnimation.ROTATION, 45f,
//                    SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_NO_BOUNCY);
//            animationX.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
//                @Override
//                public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//                    animationY.animateToFinalPosition(value);
////                    animationX2.animateToFinalPosition(value);
//                    anim2.animateToFinalPosition(value);
//                }
//            });
//
//            animationX.start();
//        }
//        return super.onTouchEvent(event);
//    }
}
