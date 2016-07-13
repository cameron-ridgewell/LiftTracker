package com.lifttracker.elements;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.common.Set;
import com.lifttracker.common.Workout;
import com.lifttracker.common.WorkoutItem;
import com.lifttracker.fragments.WorkoutFragment;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutExerciseAdapter extends ArrayAdapter<Exercise> {
    private Context context;
    private ArrayList<Exercise> exercises;
    private View rootView;

    public CreateWorkoutExerciseAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
        this.context = context;
        this.exercises = exercises;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rootView = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_create_workout_exercise_item,
                parent, false);

        TextView exercise_name = (TextView) findViewById(R.id.exercise_name);
        exercise_name.setText(exercises.get(position).getName());
        TextView exercise_name_alt_text = (TextView) findViewById(R.id.exercise_name_alt_text);
        exercise_name_alt_text.setText(exercises.get(position).getName());

        return rootView;
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }
}
