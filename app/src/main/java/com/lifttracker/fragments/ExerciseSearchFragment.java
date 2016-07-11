package com.lifttracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.lifttracker.R;
import com.lifttracker.common.Exercise;
import com.lifttracker.elements.ExerciseSearchFragmentAdapter;
import com.lifttracker.utilities.MemoryRequisition;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.lifttracker.utilities.simmetrics.StringDistance;
import com.lifttracker.utilities.simmetrics.StringMetric;
import com.lifttracker.utilities.simmetrics.builders.StringMetricBuilder;
import com.lifttracker.utilities.simmetrics.metrics.CosineSimilarity;
import com.lifttracker.utilities.simmetrics.metrics.Levenshtein;
import com.lifttracker.utilities.simmetrics.metrics.StringMetrics;
import com.lifttracker.utilities.simmetrics.simplifiers.Simplifiers;
import com.lifttracker.utilities.simmetrics.tokenizers.Tokenizers;

import org.joda.time.DateTime;

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
    private ArrayList<Exercise> viewableExercises = new ArrayList<>();
    private ExerciseSearchFragmentAdapter itemsAdapter;
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
//        svc.getAllExercises(new ResponseAction<ArrayList<Exercise>>() {
//            @Override
//            public void action(ArrayList<Exercise> input) {
//                exerciseList.clear();
//                exerciseList.addAll(input);
//                Collections.sort(exerciseList, new Comparator<Exercise>() {
//                    @Override public int compare(Exercise e1, Exercise e2) {
//                        return e1.getName().compareTo(e2.getName()); // Ascending
//                    }
//                });
//
//                for (Exercise e : exerciseList)
//                {
//                    MemoryRequisition.getInstance(getContext())
//                            .addExerciseDbItem(e, new DateTime());
//                }
//            }
//        });


        setupView();

        return rootView;
    }

    private void setupView()
    {
        exerciseList.clear();
        exerciseList.addAll(MemoryRequisition.getInstance(getContext()).getAllExercises());
        viewableExercises.clear();
        viewableExercises.addAll(exerciseList);

        exercise_list_view = (ListView) findViewById(R.id.list_view);
        itemsAdapter = new ExerciseSearchFragmentAdapter(getContext(),
                viewableExercises);
        exercise_list_view.setAdapter(itemsAdapter);

        //final StringMetric metric = StringMetrics.cosineSimilarity();
        final StringMetric metric = StringMetrics.damerauLevenshtein();

        exercise_search = (EditText) findViewById(R.id.exercise_search);
        exercise_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewableExercises.clear();
                ArrayList<Exercise> secondaryList = new ArrayList<Exercise>();
                if (!charSequence.toString().equals("")) {
                    for (Exercise e : exerciseList) {
                        if (e.getName().toLowerCase().contains(charSequence.toString()
                                .toLowerCase())) {
                            viewableExercises.add(e);
                        }
                        else
                        {
                            String str[] = e.getName().split(" ");
                            for (int j = 0; j < str.length; j++) {
                                if (metric.compare(charSequence.toString().toLowerCase(),
                                        str[j]) > 0.3) {
                                    secondaryList.add(e);
                                    break;
                                }
                            }
                        }
                    }
                }
                else
                {
                    viewableExercises.addAll(exerciseList);
                }

                viewableExercises.addAll(secondaryList);

                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
