package com.lifttracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifttracker.R;
import com.lifttracker.activities.FABInteractionActivity;
import com.lifttracker.activities.MainActivity;
import com.lifttracker.common.Exercise;
import com.lifttracker.common.SuperSetExerciseList;
import com.lifttracker.elements.CreateWorkoutListAdapter;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class CreateWorkoutFragment extends Fragment {
    private ServerRequest svc;
    private View rootView;
    private SuperSetExerciseList exerciseList = new SuperSetExerciseList();
    private CreateWorkoutListAdapter itemsAdapter;
    private OnFragmentInteractionListener mListener;

    private RecyclerView exercise_list_view;
    private FancyButton timerButton;
    private ExerciseSearchDialog exerciseSearchDialog;
    private FancyButton actionButton;

    public final Runnable fabClickAction = new Runnable() {
        @Override
        public void run() {
            exerciseSearchDialog.show(getActivity().getSupportFragmentManager(),
                    MainActivity.EXERCISE_SEARCH_DIALOG);

        }
    };

    public CreateWorkoutFragment() {
        // Required empty public constructor
    }

//    private class ReceiverThread extends Thread {
//        @Override
//        public void run() {
//
//        }


        public static CreateWorkoutFragment newInstance() {
        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        svc = ServerRequest.getInstance();

        setupFABActions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_workout, container, false);

        setupView();

        return rootView;
    }

    private void setupView()
    {

        exercise_list_view = (RecyclerView) findViewById(R.id.list_view);
        itemsAdapter = new CreateWorkoutListAdapter(getContext(),
                exerciseList, this);
        exercise_list_view.setAdapter(itemsAdapter);
        exercise_list_view.setLayoutManager(new LinearLayoutManager(getContext()));
        actionButton = (FancyButton) findViewById(R.id.action_button);

        exerciseSearchDialog = ExerciseSearchDialog.newInstance();

        ResponseAction<Exercise> itemClickedAction = new ResponseAction<Exercise>()
        {
            @Override
            public void action(Exercise exercise) {
                exerciseList.add(exercise);
                Log.e("Exercise Added", exerciseList.get(exerciseList.size() - 1).get(0).getName());
                itemsAdapter.notifyItemInserted(exerciseList.size() - 1);
            }
        };
        exerciseSearchDialog.addClickedAction(itemClickedAction);
    }

    private void setupFABActions()
    {
        ArrayList<Runnable> myArray = new ArrayList<Runnable>();
        myArray.add(fabClickAction);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        ((FABInteractionActivity) getActivity()).setupFragmentFAB(getFragmentManager().getBackStackEntryAt(getFragmentManager()
                .getBackStackEntryCount() - 1).getName());
        super.onDetach();
        mListener = null;
    }

    public void showButton(String buttonText, final Runnable clickResponse)
    {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setText(buttonText);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResponse.run();
            }
        });
    }

    public void hideButton()
    {
        actionButton.setVisibility(View.INVISIBLE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }
}
