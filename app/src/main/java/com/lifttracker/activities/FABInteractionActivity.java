package com.lifttracker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.lifttracker.utilities.ResponseAction;

import java.util.ArrayList;

/**
 * Created by cameronridgewell on 7/13/16.
 */
public abstract class FABInteractionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ResponseAction fabBackAction;

    public FABInteractionActivity doAThing(ArrayList<ResponseAction> responseActions)
    {
        return this;
    }

    public FABInteractionActivity setupFragmentFAB(String fragment_name)
    {
        return this;
    }

    public FABInteractionActivity transitionFragment(int fragment_container_id, Fragment fragment,
                                                     String fragment_string)
    {
        setupFragmentFAB(fragment_string)
                .getSupportFragmentManager().beginTransaction()
                .replace(fragment_container_id, fragment,
                        fragment_string)
                .addToBackStack(fragment_string)
                .commit();
        return this;
    }

    public void setFABBackAction(ResponseAction<Object> ra)
    {
        fabBackAction = ra;
    }

    public void responseAction()
    {
        fabBackAction.action(null);
    }
}
