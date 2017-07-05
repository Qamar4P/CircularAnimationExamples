package com.qamar4p.circularanimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Progress view on circle. Creates a circle with a given color and drop shadow. Setting
 * animateProgress() will animate an arc on the border to the percentage given. 1 for complete, 0
 * for no border.
 */
public class CircleRevealHideView extends View {

    /**
     * Logging tag.
     */
    static final String TAG = "CircleRevealView";

    /**
     * Progress starts at the top of the circle.
     */
    public static final float START_TOP = -90;

    /**
     * Progress starts at the right side of the circle.
     */
    public static final float START_RIGHT = 0;

    /**
     * Progress starts at the bottom of the circle.
     */
    public static final float START_BOTTOM = 90;

    /**
     * Progress starts at the left side of the circle.
     */
    public static final float START_LEFT = 180;

    /**
     * Default border width.
     */
    private static final int DEFAULT_BORDER_WIDTH = 10;

    /**
     * Default border color.
     */
    private static final int DEFAULT_BORDER_COLOR = Color.BLUE;

    /**
     * Default circle color
     */
    private static final int DEFAULT_CIRCLE_COLOR = Color.parseColor("#FF5252");

    /**
     * Value to use for clockwise rotation.
     */
    private static final int ROTATION_CLOCKWISE = 360;

    /**
     * Paint to use to draw the circle.
     */
    private Paint mMainPaint;


    /**
     * Paint to use to draw the drop shadow.
     */
    private Paint mShadowPaint;

    /**
     * Paint for icon image.
     */
    private Paint mIconPaint;

    /**
     * Paint to use for next icon image to use.
     */
    private Paint mNextIconPaint;

    /**
     * Current border withd.
     */
    private int mBorderWidth = 10;

    /**
     * The blur radius of the drop shadow.
     */

    /**
     * The current progress percentage. Value is from 0 - 1.
     */
    private float mProgress = 0;

    /**
     * The current progress rotation.
     */
    private float mRotation = ROTATION_CLOCKWISE;

    /**
     * Runnable instance to invoke when the progress meter reaches completion.
     */
    private Runnable mOnComplete;

    /**
     * The value animator for the progress arc.
     */
    private ValueAnimator mValueAnimator;
    private Toast toast;
    final Path mPath = new Path();
    private Paint mMaskPaint;

    public CircleRevealHideView(Context context) {
        super(context);
        init();
    }

    void toast(String msg){
        if(toast !=null) toast.cancel();
        toast = Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public CircleRevealHideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleRevealHideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
//    private int mMaskingColor = Color.parseColor("#D20E0F02");
    private int mMaskingColor = Color.parseColor("#000E0F02");

    private void init() {

        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);


//        mShadowPaint = new Paint();
//        setLayerType(LAYER_TYPE_SOFTWARE, mShadowPaint);
//        mShadowPaint.setShadowLayer(mShadowRadius, 0, 0, Color.argb(100, 0, 0, 0));

        mMaskPaint = new Paint();
//        setLayerType(LAYER_TYPE_SOFTWARE, mMaskPaint);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mIconPaint = new Paint();
        mNextIconPaint = new Paint();

        mValueAnimator = ValueAnimator.ofFloat(0, 100);
        mValueAnimator.setDuration(500);
//        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (Float)animation.getAnimatedValue();
                invalidate();
            }
        });

        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mProgress >= 1 && mOnComplete != null) {
                    mOnComplete.run();
                }
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                mProgress = mToProgress = 0;
//                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    /**
     * @see View#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mMaskingColor);
        // Create a circular path.
        final float halfWidth = canvas.getWidth()/2;
        final float halfHeight = canvas.getHeight()/2;
        final float radius = Math.max(halfWidth, halfHeight);

        float delta = mProgress * radius / 100;
//        double x = radius + (radius)*Math.cos(-mRotation * mProgress);
//        double y = radius + (radius)*Math.sin(-mRotation * mProgress);
//        float delta = mShadowRadius + (mBorderWidth / 2);
//        path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW);
        Log.d(TAG,"mProgress:"+ mProgress);
        mPath.reset();
        mPath.addCircle(halfWidth, // xCenter
                halfHeight, //yCenter
                delta, // radius
                Path.Direction.CW
        );
        mPath.lineTo(0,0);
        mPath.lineTo(canvas.getWidth(),0);
        mPath.lineTo(canvas.getWidth(),canvas.getHeight());
        mPath.lineTo(0,canvas.getHeight());
        mPath.lineTo(0,0);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        mPath.close();
//        canvas.clipPath(mPath);
        super.onDraw(canvas);
//        canvas.drawPath(mPath,mArcPaint);
//        canvas.drawCircle(cx, cy, getWidth() / 2 - mShadowRadius - DEFAULT_BORDER_WIDTH, mShadowPaint);
//        canvas.drawCircle(cx, cy, getWidth() / 2 - mShadowRadius - DEFAULT_BORDER_WIDTH, mMainPaint);

        canvas.drawPath(mPath, // use center
                mMaskPaint // paint
        );
    }

    /**
     * Animate the progress percentage to the given value.
     */
    public void hideAnimate() {
        mProgress = 100;
        mValueAnimator.cancel();
        mValueAnimator.setFloatValues(100,0);
        mValueAnimator.start();

    }
    /**
     * Animate the progress percentage to the given value.
     */
    public void showAnimate() {
        mProgress = 0;
        mValueAnimator.cancel();
        mValueAnimator.setFloatValues(0,100);
        mValueAnimator.start();

    }

    /**
     * Set the duration the animation to a given percentage should take. This is in milliseconds.
     * @param duration The duration of the progress animation in milliseconds.
     */
    public void setIncrementDuration(int duration) {
        mValueAnimator.setDuration(duration);
    }


    /**
     * Set the runnable to invoke when the progress meter is complete.
     * @param runnable The runnable.
     */
    public void setOnComplete(Runnable runnable) {
        mOnComplete = runnable;
    }
}