package com.lifttracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifttracker.common.Exercise;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;

public class CreateExerciseDialog extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private Spinner workout_type_spinner;
    private Spinner lift_type_spinner;
    private EditText exercise_name_text;
    private View rootView;
    private ServerRequest svc;

    public CreateExerciseDialog() {
        // Required empty public constructor
    }


    public static CreateExerciseDialog newInstance() {
        CreateExerciseDialog fragment = new CreateExerciseDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        svc = ServerRequest.getInstance();

        rootView = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_create_exercise, null);

        setupView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String exercise_name = exercise_name_text.getText().toString();
                        if (!exercise_name.equals(""))
                        {
                            Exercise newExercise = new Exercise(exercise_name);
                            newExercise.setExerciseType(Exercise.ExerciseType
                                    .values()[workout_type_spinner.getSelectedItemPosition()]);
                            if (Exercise.ExerciseType.values()[workout_type_spinner
                                    .getSelectedItemPosition()]
                                    == Exercise.ExerciseType.Weightlifting)
                            {
                                newExercise.setLiftType(Exercise.LiftType.values()[lift_type_spinner.getSelectedItemPosition()]);
                            }
                            else
                            {
                                newExercise.setLiftType(Exercise.LiftType.None);
                            }

                            //TODO: Toast response of this
                            Toast.makeText(getContext(), "Thing happened", Toast.LENGTH_SHORT).show();

                            svc.addExercise(newExercise);
                        }
                    }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dismiss();
                    }
                });
        return builder.create();
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

        exercise_name_text = (EditText) findViewById(R.id.exercise_name_input);
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
                LinearLayout exercise_type_input_block
                        = (LinearLayout) findViewById(R.id.exercise_type_input_block);
                if (Exercise.ExerciseType.values()[position] != Exercise.ExerciseType.Weightlifting)
                {
                    exercise_type_input_block.setVisibility(View.GONE);
                }
                else {
                    exercise_type_input_block.setVisibility(View.VISIBLE);
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
