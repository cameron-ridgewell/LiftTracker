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

    public FABInteractionActivity clickAction(ArrayList<Runnable> responseActions)
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
        getSupportFragmentManager().beginTransaction()
        .replace(fragment_container_id, fragment,
                fragment_string)
        .addToBackStack(fragment_string)
        .commit();
        getSupportFragmentManager().executePendingTransactions();
        setupFragmentFAB(fragment_string);
        return this;
    }
}
