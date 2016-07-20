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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.common.ExerciseWithViewId;
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
        View contactView = inflater.inflate(R.layout.fragment_create_workout_item_holder, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CreateWorkoutListAdapter.ViewHolder holder, int position) {
        for (ExerciseWithViewId e : exercise_list.get(position))
        {
            if (holder.rootView.findViewById(e.getViewId()) != null)
            {
                holder.remove(holder.rootView.findViewById(e.getViewId()));
            }
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater
                    .inflate(R.layout.fragment_create_workout_exercise_item, null);
            itemView.setId(e.getViewId());
            TextView exercise_name =
                    (TextView) itemView.findViewById(R.id.exercise_name);
            TextView exercise_name_alt_text =
                    (TextView) itemView.findViewById(R.id.exercise_name_alt_text);
            exercise_name.setText(e.getName());
            exercise_name_alt_text.setText(e.getName());

            holder.add(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return exercise_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }

        public void add(View view)
        {
            ((LinearLayout) rootView.findViewById(R.id.linear_layout_container)).addView(view);
        }

        public void remove(View view)
        {
            ((LinearLayout) rootView.findViewById(R.id.linear_layout_container)).removeView(view);
        }
    }
}

