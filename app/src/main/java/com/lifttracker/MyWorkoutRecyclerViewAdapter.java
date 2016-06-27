package com.lifttracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifttracker.common.Set;
import com.lifttracker.common.Workout;
import com.lifttracker.common.WorkoutItem;

import java.util.List;

public class MyWorkoutRecyclerViewAdapter extends
        RecyclerView.Adapter<MyWorkoutRecyclerViewAdapter.ViewHolder> {

    private final List<WorkoutItem> mValues;
    private final WorkoutFragment.OnListFragmentInteractionListener mListener;
    private Context mContext;

    public MyWorkoutRecyclerViewAdapter(Workout workout,
            WorkoutFragment.OnListFragmentInteractionListener listener, Context context) {
        mValues = workout.getExerciseList();
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_workout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.superset_block.getLayoutParams().width = 0;
        WorkoutItem mExercise = mValues.get(position);
        holder.exerciseTitle.setText(mExercise.getExercise().getName());
        String setInfoString = "";
        for (Set set: mExercise.getSetList())
        {
            setInfoString = setInfoString + set.getWeight() + " " + set.getWeightUnits()
                    + " x "  + set.getReps() + "\n";
        }
        if (!setInfoString.equals("")) {
            holder.setInfo.setText(setInfoString.trim());
        }
        else
        {
            holder.setInfo.setHeight(0);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView exerciseTitle;
        public final TextView setInfo;
        public final RelativeLayout superset_block;
        public WorkoutItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            exerciseTitle = (TextView) view.findViewById(R.id.exercise_title);
            setInfo = (TextView) view.findViewById(R.id.set_info);
            superset_block = (RelativeLayout) view.findViewById(R.id.superset_block);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + exerciseTitle.getText() + "'";
        }
    }
}
