package com.lifttracker.elements;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.common.SuperSetExerciseList;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutListAdapter extends RecyclerView.Adapter<CreateWorkoutListAdapter.ViewHolder> {

    private SuperSetExerciseList exercise_list;
    private Context mContext;

    public CreateWorkoutListAdapter(Context context, SuperSetExerciseList exercise_list) {
        this.exercise_list = exercise_list;
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public CreateWorkoutListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_create_workout_exercise_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CreateWorkoutListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Exercise exercise;
        if (!exercise_list.isSuperset(position))
        {
            exercise = exercise_list.get(position).get(0);
        }
        else
        {
            //TODO iterate to get all exercises to inflate
            exercise = exercise_list.get(position).get(0);
        }

        // Set item views based on your views and data model
        TextView exercise_name = holder.exercise_name;
        TextView exercise_name_alt_text = holder.exercise_name_alt_text;
        exercise_name.setText(exercise.getName());
        exercise_name_alt_text.setText(exercise.getName());
    }

    @Override
    public int getItemCount() {
        return exercise_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView exercise_name;
        public TextView exercise_name_alt_text;

        public ViewHolder(View itemView) {
            super(itemView);

            exercise_name = (TextView) itemView.findViewById(R.id.exercise_name);
            exercise_name_alt_text = (TextView) itemView.findViewById(R.id.exercise_name_alt_text);

        }
    }
}

