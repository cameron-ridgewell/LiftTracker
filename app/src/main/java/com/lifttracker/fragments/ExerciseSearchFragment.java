package com.lifttracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseSearchFragment extends Fragment {
    private ServerRequest svc;
    private View rootView;
    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    private EditText exercise_search;
    private ListView exercise_list_view;

    public ExerciseSearchFragment() {
        // Required empty public constructor
    }

    public static ExerciseSearchFragment newInstance() {
        ExerciseSearchFragment fragment = new ExerciseSearchFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exercise_search, container, false);
        svc.getAllExercises(new ResponseAction() {
            @Override
            public void action(Object input) {
                if (((Response) input).code() < 400)
                {
                    exerciseList.clear();
                    exerciseList.addAll(((Response<List<Exercise>>) input).body());
                    Collections.sort(exerciseList, new Comparator<Exercise>() {
                        @Override public int compare(Exercise e1, Exercise e2) {
                            return e1.getName().compareTo(e2.getName()); // Ascending
                        }

                    });
                }
            }
        });

        setupView();

        return rootView;
    }

    private void setupView()
    {
        exercise_search = (EditText) findViewById(R.id.exercise_search);
        //TODO listener for change and search
//        exercise_list_view = (ListView) findViewById(R.id.list_view);
//        SimpleExerciseArrayAdapter itemsAdapter = new SimpleExerciseArrayAdapter(getContext(),
//                exerciseList);
//        exercise_list_view.setAdapter(itemsAdapter);

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
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }
}
