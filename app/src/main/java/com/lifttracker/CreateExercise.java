package com.lifttracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifttracker.common.Exercise;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateExercise extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Spinner workout_type_spinner;
    private Spinner lift_type_spinner;
    private View rootView;

    public CreateExercise() {
        // Required empty public constructor
    }


    public static CreateExercise newInstance() {
        CreateExercise fragment = new CreateExercise();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        setupView();

        return rootView;
    }

    private void setupView()
    {
        ArrayAdapter<String> workoutTypeAdapter= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                enumToStringArray(Exercise.ExerciseType.values()));
        workout_type_spinner = (Spinner) findViewById(R.id.workout_type_spinner);
        workout_type_spinner.setAdapter(workoutTypeAdapter);

        ArrayAdapter<String> liftTypeAdapter= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                enumToStringArray(Exercise.LiftType.values()));
        lift_type_spinner = (Spinner) findViewById(R.id.lift_type_spinner);
        lift_type_spinner.setAdapter(liftTypeAdapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        workout_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //if the category is Weightlifting, request Liftype, otherwise, hide spinner
                if (Exercise.ExerciseType.values()[position] != Exercise.ExerciseType.Weightlifting)
                {
                    TextView spinnerText = (TextView) findViewById(R.id.lift_type_text);
                    spinnerText.setVisibility(View.GONE);
                    lift_type_spinner.setVisibility(View.GONE);
                } else {
                    TextView spinnerText = (TextView) findViewById(R.id.lift_type_text);
                    spinnerText.setVisibility(View.VISIBLE);
                    lift_type_spinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }

    private String[] enumToStringArray(Object[] objArr){
        ArrayList<String> arrayList = new ArrayList<>();
        for (Object obj : objArr)
        {
            Log.e("TAG", obj.toString());
            arrayList.add(obj.toString());
        }
        return arrayList.toArray(new String[0]);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
