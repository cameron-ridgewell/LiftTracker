package com.lifttracker.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lifttracker.elements.DynamicFAB;
import com.lifttracker.elements.MovingFloatingActionButton;
import com.lifttracker.fragments.CreateExerciseDialog;
import com.lifttracker.fragments.CreateWorkoutFragment;
import com.lifttracker.fragments.MainPageViewFragment;
import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.fragments.TrackPersonalStatsFragment;
import com.lifttracker.fragments.WorkoutFragment;
import com.lifttracker.utilities.MemoryRequisition;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends  FABInteractionActivity {

    private Toolbar toolbar;

    public static  final String MAIN_PAGE_VIEW_FRAGMENT = "main_page_view_fragment";
    public static final String CREATE_EXERCISE_FRAGMENT = "create_exercise_fragment";
    public static final String CREATE_WORKOUT_FRAGMENT = "create_workout_fragment";
    public static final String TRACK_PERSONAL_STAT_FRAGMENT = "track_personal_stats_fragment";

    private Button clickButton;
    private DynamicFAB fab;
    private boolean myToggle;

    private ArrayList<ResponseAction> fabResponseActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = MainPageViewFragment.newInstance();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, MAIN_PAGE_VIEW_FRAGMENT)
                .addToBackStack(MAIN_PAGE_VIEW_FRAGMENT)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (DynamicFAB) findViewById(R.id.fab);
        fab.setupMargin();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabResponseActions.get(0).action(null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        generateExercises();

        /*
         * TODO:
         * Attempt to load previous maxes (with timeout)
         * Attempt to load any new exercises
         * Access stored memory:
         *      previous 7 days workouts
         *      previous max lifts
         *      list of known exercises
         */
    }

    private void generateExercises()
    {
        ServerRequest svc = ServerRequest.getInstance();
        final Exercise exercise = new Exercise("Barbell Bench Press");
        exercise.setExerciseType(Exercise.ExerciseType.Weightlifting);
        exercise.setLiftType(Exercise.LiftType.Chest);
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Incline Barbell Bench Press");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Decline Barbell Bench Press");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Dumbbell Bench Press");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Incline Dumbbell Bench Press");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Decline Dumbbell Bench Press");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Chest Dip");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Incline Cable Chest Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Cable Chest Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Decline Cable Chest Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Dumbbell Bench Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Incline Dumbbell Bench Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Decline Dumbbell Bench Fly");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Push-Up");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Incline Push-Up");
        svc.addExercise(exercise, getApplicationContext());
        exercise.setName("Decline Push-Up");
        svc.addExercise(exercise, getApplicationContext());

        svc.getAllExercises(new ResponseAction<List<Exercise>>() {
            @Override
            public void action(List<Exercise> input) {
                ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
                exerciseList.addAll(input);
                Collections.sort(exerciseList, new Comparator<Exercise>() {
                    @Override public int compare(Exercise e1, Exercise e2) {
                        return e1.getName().compareTo(e2.getName()); // Ascending
                    }

                });

                for (Exercise e : exerciseList)
                {
                    MemoryRequisition.getInstance(getApplicationContext())
                            .addExerciseDbItem(e, e.getLastPerformedDate());
                    Exercise exercise1 = MemoryRequisition.getInstance(getApplicationContext())
                            .getExerciseDbItem(e.getName());
                    Log.e(exercise1.getName(), exercise1.getId());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.track_personal_stats) {
            Fragment fragment = TrackPersonalStatsFragment.newInstance();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, TRACK_PERSONAL_STAT_FRAGMENT)
                    .addToBackStack(TRACK_PERSONAL_STAT_FRAGMENT)
                    .commit();
        } else if (id == R.id.track_performance_stats) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.create_exercise) {
            CreateExerciseDialog createExerciseDialog = CreateExerciseDialog.newInstance();
            createExerciseDialog.show(getSupportFragmentManager(), CREATE_EXERCISE_FRAGMENT);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public FABInteractionActivity doAThing(ArrayList<ResponseAction> responseActions)
    {
        this.fabResponseActions = responseActions;
        return this;
    }

    @Override
    public FABInteractionActivity setupFragmentFAB(String fragment_name)
    {
        // Do Nothing
        switch (fragment_name) {
            case MAIN_PAGE_VIEW_FRAGMENT:
                fab.moveToHome(getApplication());

                break;
            case CREATE_EXERCISE_FRAGMENT:
                // Do Nothing
                break;
            case CREATE_WORKOUT_FRAGMENT:
                fab.moveToTop(getApplication());
                setFABBackAction(new ResponseAction<Object>() {
                    @Override
                    public void action(Object o) {
                        fab.moveToHome(getApplication());
                    }
                });
                break;
            case TRACK_PERSONAL_STAT_FRAGMENT:
                break;
            default:
                break;
        }
        return this;
    }
}
