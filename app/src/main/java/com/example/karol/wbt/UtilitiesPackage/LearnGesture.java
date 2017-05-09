package com.example.karol.wbt.UtilitiesPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class LearnGesture extends GestureDetector.SimpleOnGestureListener {

    private final Context context;

    private Intent prevActivity;

    private Intent nextActivity;

    public LearnGesture(Context context, Intent prev, Intent next){

        this.context = context;

        prevActivity = prev;

        nextActivity = next;

    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float X,float Y){
        if ( event2.getX() > event1.getX()){//Swap left to right
            if ( prevActivity != null){
                context.startActivity(prevActivity);
                ((Activity)context).finish();
            }
        }
        else{
            if (event2.getX() < event1.getX()){//Swap right to lefi
                context.startActivity(nextActivity);
                ((Activity)context).finish();
            }
        }
        return true;
    }
}
