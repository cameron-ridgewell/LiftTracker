package com.lifttracker.elements;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;

/**
 * Created by cameronridgewell on 7/12/16.
 */
public class DynamicFAB extends FloatingActionButton{

    public DynamicFAB(Context context) {
        super(context);
    }

    public DynamicFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void moveToTop(Application application)
    {
        try {
            Animation moveFABtoTop = AnimationUtils.loadAnimation(application, R.anim.move_fab_bottom_to_top);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
            //layoutParams.rightMargin += (int) (fab.getWidth() * 1.7);
            int tmp = layoutParams.topMargin;
            layoutParams.topMargin = layoutParams.bottomMargin;
            layoutParams.bottomMargin = tmp;
            setLayoutParams(layoutParams);
            startAnimation(moveFABtoTop);
        }
        catch (Exception e)
        {
            Log.e("Error in animation", e.getMessage());
        }
    }

    public void moveToBottom(Application application)
    {
        try
        {
            Animation moveFABtoBottom = AnimationUtils.loadAnimation(application, R.anim.move_fab_top_to_bottom);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
            //layoutParams.rightMargin -= (int) (fab.getWidth() * 1.7);
            int tmp = layoutParams.topMargin;
            layoutParams.topMargin = layoutParams.bottomMargin;
            layoutParams.bottomMargin = tmp;
            setLayoutParams(layoutParams);
            startAnimation(moveFABtoBottom);
        }
        catch (Exception e)
        {
            Log.e("Error in animation", e.getMessage());
        }
    }
}
