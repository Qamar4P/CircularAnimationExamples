package com.qamar4p.circularanimation.helpers;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Qamar on 6/21/2017.
 */

public class TouchRelativeLayout extends RelativeLayout {
    private Toast toast;
    private float offsetY;

    public TouchRelativeLayout(Context context) {
        this(context,null);
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        offsetY = 20 * context.getResources().getDisplayMetrics().density;
//        setOnTouchListener(this);        
    }

//    @Override
//    public boolean onInterceptHoverEvent(MotionEvent event) {
//        if(toast !=null) toast.cancel();
//        toast = Toast.makeText(getContext(),"onInterceptHoverEvent"+event.getAction(),Toast.LENGTH_SHORT);
//        toast.show();
//        return super.onInterceptHoverEvent(event);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(toast !=null) toast.cancel();

        int x = Math.round(ev.getRawX());
        int y = Math.round(ev.getRawY() - offsetY ) ;

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_MOVE:
                for (int i=0; i<getChildCount(); i++){
                    View child = getChildAt(i);
                    Rect rect = new Rect();
                    child.getGlobalVisibleRect(rect);
                    if(x > rect.left && x < rect.right && y > rect.top && y < rect.bottom){
                        child.setActivated(true);
                    }else {
                        child.setActivated(false);
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                toast = Toast.makeText(getContext(),"ACTION_UP  x"+x+",y"+y,Toast.LENGTH_SHORT);
//                toast.show();
                for (int i=0; i<getChildCount(); i++){
                    View child = getChildAt(i);
                    if(x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()){
                        child.performClick();
                    }
                }
                return false;

        }
        return true;
    }

    /**
     * Determines if given points are inside view
     * @param x - x coordinate of point
     * @param y - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view){
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if(( x > viewX && x < (viewX + view.getWidth())) &&
                ( y > viewY && y < (viewY + view.getHeight()))){
            return true;
        } else {
            return false;
        }
    }
}
