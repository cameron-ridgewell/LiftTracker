package com.lifttracker.elements;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;

import java.util.ArrayList;

public class ExerciseSearchFragmentAdapter extends ArrayAdapter<Exercise> {
    private Context context;
    private ArrayList<Exercise> exercises;
    private View rootView;

    public ExerciseSearchFragmentAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
        this.context = context;
        this.exercises = exercises;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rootView = convertView;

        if(rootView == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rootView = inflater.inflate(R.layout.fragment_exercise_search_fragment_item,
                    parent, false);

            TextView exercise_name = (TextView) findViewById(R.id.exercise_name);
            exercise_name.setText(exercises.get(position).getName());
            TextView exercise_name_alt_text = (TextView) findViewById(R.id.exercise_name_alt_text);
            exercise_name_alt_text.setText(exercises.get(position).getName());
        }
        return rootView;
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }
}

