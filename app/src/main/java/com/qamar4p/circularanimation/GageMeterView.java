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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

/**
 * Progress view on circle. Creates a circle with a given color and drop shadow. Setting
 * animateProgress() will animate an arc on the border to the percentage given. 1 for complete, 0
 * for no border.
 */
public class GageMeterView extends View {

    /**
     * Logging tag.
     */
    static final String TAG = "CircleProgress";

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
     * Value to use for counter clockwise rotation.
     */
    private static final int ROTATION_COUNTER_CLOCKWISE = -360;

    /**
     * Paint to use to draw the circle.
     */
    private Paint mMainPaint;

    /**
     * Paint to use to draw the progress meter arc.
     */
    private Paint mArcPaint;

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
     * The time it takes in milliseconds to animate the progress to the next set value.
     */
    private int mIncrementDuration = 500;

    /**
     * The blur radius of the drop shadow.
     */
    private int mShadowRadius = 20;

    /**
     * The current progress percentage. Value is from 0 - 1.
     */
    private float mProgress = 0;

    /**
     * The percentage the progress should go to.
     */
    private float mToProgress = 0;

    /**
     * The start angle of the progress arc.
     */
    private float mStartAngle = START_TOP;

    /**
     * The current progress rotation.
     */
    private float mRotation = ROTATION_CLOCKWISE;

    /**
     * Runnable instance to invoke when the progress meter reaches completion.
     */
    private Runnable mOnComplete;

    /**
     * Icon image to display at the center of the view.
     */
    private Bitmap mIconImage;

    /**
     * The next icon image to display at the center. Will perform a transition between current and
     * this new image.
     */
    private Bitmap mNextImage;

    RectF oval = new RectF();
    private float mFadeAlpha;
    private Paint mBitPaint;

    /**
     * The value animator for the progress arc.
     */
    private ValueAnimator mValueAnimator;
    private Toast toast;
    private boolean isProgressDone;
    final Path mPath = new Path();
    private Paint mMaskPaint;
    private Paint mBackgroundPaint;

    public GageMeterView(Context context) {
        super(context);
        init();
    }

    void toast(String msg){
        if(toast !=null) toast.cancel();
        toast = Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Set the resource id of the icon to use in the middle of the circle view.
     * @param resourceId The resource id of the drawable.
     */
    public void setHiLightImage(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
        mIconImage = bitmap;
        mBitPaint.setShader(new BitmapShader(mIconImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                animateProgress(100);
//                return true;
//
//            case MotionEvent.ACTION_MOVE:
//                return true;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                if (mValueAnimator != null) {
//                    mValueAnimator.cancel();
//                }
//                isProgressDone = false;
//                return false;
//
//        }
//
//        return true;
//    }

    public GageMeterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GageMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
//    private int mMaskingColor = Color.parseColor("#D20E0F02");
    private int mMaskingColor = Color.parseColor("#000E0F02");

    private void init() {
        mMainPaint = new Paint();
        mMainPaint.setColor(DEFAULT_CIRCLE_COLOR);
        mMainPaint.setAntiAlias(true);

        mBitPaint = new Paint();

        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(DEFAULT_BORDER_WIDTH);
        mArcPaint.setColor(DEFAULT_BORDER_COLOR);
        mArcPaint.setAntiAlias(true);

//        mShadowPaint = new Paint();
//        setLayerType(LAYER_TYPE_SOFTWARE, mShadowPaint);
//        mShadowPaint.setShadowLayer(mShadowRadius, 0, 0, Color.argb(100, 0, 0, 0));

        mMaskPaint = new Paint();
//        setLayerType(LAYER_TYPE_SOFTWARE, mMaskPaint);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mIconPaint = new Paint();
        mNextIconPaint = new Paint();

        mValueAnimator = ValueAnimator.ofFloat(0, 100);
        mValueAnimator.setDuration(mIncrementDuration);
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
//                if (mProgress >= 1 && mOnComplete != null) {
//                    mOnComplete.run();
//                }
//                mProgress = mToProgress = 0;
//                invalidate();
                isProgressDone = true;
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
//        canvas.drawColor(mMaskingColor);
        // Create a circular path.
        final float halfWidth = canvas.getWidth()/2;
        final float halfHeight = canvas.getHeight()/2;
        final float radius = Math.max(halfWidth, halfHeight);
        final Path path = new Path();

        float delta = radius;
//        double x = radius + (radius)*Math.cos(-mRotation * mProgress);
//        double y = radius + (radius)*Math.sin(-mRotation * mProgress);
//        float delta = mShadowRadius + (mBorderWidth / 2);
//        path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW);
        if(Build.VERSION.SDK_INT >= 21){
            path.addArc(
                    halfWidth - delta, // left
                    halfHeight - delta, // right
                    getWidth() + delta, // right
                    getHeight() + delta, // bottom
                    mStartAngle, // start angle
                    mRotation * mProgress // sweep
            );
        }else {
            oval.set(
                    0 + delta, // left
                    0 + delta, // right
                    getWidth() - delta, // right
                    getHeight() - delta); // bottom);

            path.addArc(oval,
                    mStartAngle, // start angle
                    mRotation * mProgress// sweep
            );
        }
        Log.d(TAG,"startAngle:"+mStartAngle);
        Log.d(TAG,"mProgress:"+ mProgress);
        Log.d(TAG,"sweepAngle:"+mRotation * mProgress);
        path.close();
        mPath.reset();
//        mPath.addRect(getLeft(),getTop(),getRight(),getBottom(), Path.Direction.CW);
        mPath.addPath(path);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.close();

//        mPath.reset();
//        mPath.moveTo((float) canvas.getWidth() * mProgress, 0.0f);
//        mPath.lineTo((float) canvas.getWidth() * mProgress, canvas.getHeight());
//        mPath.lineTo(canvas.getWidth(), canvas.getHeight());
//        mPath.lineTo(canvas.getWidth(), 0.0f);
//        mPath.close();
//        canvas.clipPath(mPath);
        super.onDraw(canvas);
//        if(mProgress == 0) canvas.drawPaint(mBackgroundPaint);
        if(mProgress > 0) {
//        canvas.drawPath(mPath,mArcPaint);
//        canvas.drawCircle(cx, cy, getWidth() / 2 - mShadowRadius - DEFAULT_BORDER_WIDTH, mShadowPaint);
//        canvas.drawCircle(cx, cy, getWidth() / 2 - mShadowRadius - DEFAULT_BORDER_WIDTH, mMainPaint);
            if (Build.VERSION.SDK_INT >= 21) {
//            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paint.setColor(0xFFFFFFFF);
//            paint.setStyle(Paint.Style.FILL);
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                canvas.drawArc(
                        0 - delta, // left
                        0 - delta, // right
                        getWidth() + delta, // right
                        getHeight() + delta, // bottom
                        mStartAngle, // start angle
                        mRotation * mProgress, // sweep
                        true, // use center
                        mBitPaint // paint
                );
            } else {
                oval.set(
                        0 + delta, // left
                        0 + delta, // right
                        getWidth() - delta, // right
                        getHeight() - delta); // bottom);

                canvas.drawArc(oval,
                        mStartAngle, // start angle
                        mRotation * mProgress, // sweep
                        true, // use center
                        mArcPaint // paint
                );
            }
        }
//
//        if (mIconImage != null) {
//            mIconPaint.setAlpha((int)(255 * (1 - mFadeAlpha)));
//            canvas.drawBitmap(mIconImage, (getWidth() - mIconImage.getWidth()) / 2, (getHeight() - mIconImage.getHeight()) / 2, mIconPaint);
//        }
//
//        if (mNextImage != null) {
//            mNextIconPaint.setAlpha((int)(255 * mFadeAlpha));
//            canvas.drawBitmap(mNextImage, (getWidth() - mNextImage.getWidth()) / 2, (getHeight() - mNextImage.getHeight()) / 2, mNextIconPaint);
//        }
    }

    /**
     * Animate the progress percentage to the given value.
     * @param progress The value to animate to.
     */
    public void animateProgress(float progress) {

        // Valid values for progress are only between 0 and 1
        if (progress < 0) {
            mRotation = ROTATION_COUNTER_CLOCKWISE;
            progress = Math.abs(progress);
        }else {
            mRotation = ROTATION_CLOCKWISE;
        }

        if (progress > 1) {
            progress = 1;
        }

        final float toProgress = progress;

        // No change in progress, do nothing.
        if (toProgress == mToProgress) {
            return;
        }

        mValueAnimator.cancel();
        mValueAnimator.setFloatValues(mToProgress, toProgress);
        mValueAnimator.start();

    }

    /**
     * Set the duration the animation to a given percentage should take. This is in milliseconds.
     * @param duration The duration of the progress animation in milliseconds.
     */
    public void setIncrementDuration(int duration) {
        mIncrementDuration = duration;
    }

    /**
     * Set the starting angle of the progress meter arc.
     * @param startAngle The start angle in degrees from 0 to 360.
     */
    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
    }

    /**
     * Set the runnable to invoke when the progress meter is complete.
     * @param runnable The runnable.
     */
    public void setOnComplete(Runnable runnable) {
        mOnComplete = runnable;
    }
}