package com.qamar4p.circularanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qamar4p.circularanimation.custom_views.CircleProgressView;
import com.qamar4p.circularanimation.custom_views.GageMeterView;

public class MainActivity extends AppCompatActivity {

    private float downX, downY;
    private VelocityTracker velocityTracker;
    private View view1;
    private CircleProgressView buttonPrimary;
    Context context;
    private PopupWindow popupMessage;
    private View layoutOfPopup;
//    private FreqDiffIndicator imageFreqDiffView;
    private GageMeterView imageFreqDiffView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        velocityTracker = VelocityTracker.obtain();

//        view1 = findViewById (R.id.view1);
        findViewById (R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Main2Activity.class));
            }
        });
        findViewById (R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Main3Activity.class));
            }
        });
        findViewById (R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Main4Activity.class));
            }
        });
        buttonPrimary = findViewById (R.id.buttonPrimary);
        imageFreqDiffView = findViewById (R.id.imageFreqDiff);
        imageFreqDiffView.post(new Runnable() {
            @Override
            public void run() {
                imageFreqDiffView.setHiLightImage(R.drawable.gage_animation_circle, Color.GREEN);
            }
        });

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
        initPopup();
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

    private void initPopup() {
        popupMessage = new PopupWindow(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
//        TextView tvMsg = new TextView(context);
//        tvMsg.setText("Hi this is pop up window...");
//
//        LinearLayout layoutOfPopup = new LinearLayout(context);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
//        layoutOfPopup.addView(tvMsg, layoutParams);
        layoutOfPopup = getLayoutInflater().inflate(R.layout.popup_alert_ask, null);
        layoutOfPopup.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMessage.dismiss();
                buttonPrimary.setPopupView(null);

                Toast.makeText(context,"buttonClose",Toast.LENGTH_SHORT).show();
            }
        });
        layoutOfPopup.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMessage.dismiss();
                buttonPrimary.setPopupView(null);

                Toast.makeText(context,"buttonNo",Toast.LENGTH_SHORT).show();
            }
        });
        layoutOfPopup.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPrimary.setPopupView(null);

                popupMessage.dismiss();
                Toast.makeText(context,"buttonYes",Toast.LENGTH_SHORT).show();
            }
        });
        popupMessage.setTouchable(true);
        popupMessage.setContentView(layoutOfPopup);
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
