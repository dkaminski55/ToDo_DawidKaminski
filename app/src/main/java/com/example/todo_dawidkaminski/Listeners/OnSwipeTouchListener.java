package com.example.todo_dawidkaminski.Listeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener{

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context context){
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            boolean result = false;
            try{
                float diffY = event2.getY() - event1.getY(); //used for top and bottom swipes
                float diffX = event2.getX() - event1.getX(); //used for left and right swipes
                if(Math.abs(diffX) > Math.abs(diffY)){
                    if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                        if(diffX > 0){
                            onSwipeRight();
                        }
                        else{
                            onSwipeLeft();
                        }
                        return true;
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight(){

    }

    public void onSwipeLeft() {

    }
}