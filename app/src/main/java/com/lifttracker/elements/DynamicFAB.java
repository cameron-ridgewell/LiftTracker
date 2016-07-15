package com.lifttracker.elements;

import android.animation.Animator;
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
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;

/**
 * Created by cameronridgewell on 7/12/16.
 */
public class DynamicFAB extends FloatingActionButton{

    private int bottomMargin = 10;

    public DynamicFAB(Context context) {
        super(context);
    }

    public DynamicFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupMargin()
    {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.fab_margin);
        layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.fab_margin);
        layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.fab_margin);
        layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.fab_margin);
        setLayoutParams(layoutParams);

        this.bottomMargin = layoutParams.bottomMargin;
    }

    public void moveToTop(final Application application)
    {
        try {
            Animation moveFABtoTop =
                    AnimationUtils.loadAnimation(application, R.anim.move_fab_bottom_to_top);
            moveFABtoTop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) getLayoutParams();
                    layoutParams.bottomMargin =  bottomMargin
                            + (int) ((double) getHeight() * -1
                            * getResources().getFraction(R.fraction.fab_vertical_percentage, 1, 1));
                    setLayoutParams(layoutParams);

                    clearAnimation();

                    setClickable(true);
                    startAnimation(rotateAndRestore(application));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            startAnimation(moveFABtoTop);

        }
        catch (Exception e)
        {
            Log.e("Error in toTopanimation", e.getMessage());
        }
    }
//    {
//        animate()
//                .setInterpolator(new LinearInterpolator())
//                .setDuration(getResources().getInteger(R.integer.fab_bounce_time))
//                .translationY(-1600)
//                .rotationBy(10);
//    }
    public void moveToHome(final Application application)
    {
        try
        {
            Animation moveFABtoBottom =
                    AnimationUtils.loadAnimation(application, R.anim.move_fab_top_to_bottom);
            final Animation residRotation =
                    AnimationUtils.loadAnimation(application, R.anim.fab_resid_rotation);
            residRotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    final Animation residRotationRestore =
                            AnimationUtils.loadAnimation(application,
                                    R.anim.restore_fab_from_resid);
                    startAnimation(residRotationRestore);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            moveFABtoBottom.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) getLayoutParams();
                    layoutParams.topMargin = (int) getResources()
                            .getDimension(R.dimen.fab_margin);
                    layoutParams.bottomMargin = (int) getResources()
                            .getDimension(R.dimen.fab_margin);
                    layoutParams.leftMargin = (int) getResources()
                            .getDimension(R.dimen.fab_margin);
                    layoutParams.rightMargin = (int) getResources()
                            .getDimension(R.dimen.fab_margin);
                    setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    clearAnimation();
                    startAnimation(residRotation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            startAnimation(moveFABtoBottom);
        }
        catch (Exception e)
        {
            Log.e("Error in toBotanimation", e.getMessage());
        }
    }

    private Animation rotateAndRestore(final Application application)
    {
        final Animation residRotation =
                AnimationUtils.loadAnimation(application, R.anim.fab_resid_rotation);
        residRotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation residRotationRestore =
                        AnimationUtils.loadAnimation(application,
                                R.anim.restore_fab_from_resid);
                startAnimation(residRotationRestore);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return residRotation;
    }
}
