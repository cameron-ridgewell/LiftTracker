package com.lifttracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class ExerciseSearchDialog extends DialogFragment {
    private ServerRequest svc;
    private View rootView;
    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private ArrayList<Exercise> viewableExercises = new ArrayList<>();
    private ExerciseSearchFragmentAdapter itemsAdapter;
    private OnFragmentInteractionListener mListener;

    private EditText exercise_search;
    private ListView exercise_list_view;

    private ResponseAction<Exercise> itemClickedResponse;

    public ExerciseSearchDialog() {
        // Required empty public constructor
    }

    public static ExerciseSearchDialog newInstance() {
        ExerciseSearchDialog fragment = new ExerciseSearchDialog();
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
                .inflate(R.layout.fragment_exercise_search, null);

        setupView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        return super.onCreateView(inflater, container, savedInstanceState);
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

        exercise_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (itemClickedResponse != null)
                {
                    itemClickedResponse.action(viewableExercises.get(position));
                }
                dismiss();
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

    public void addClickedAction(ResponseAction<Exercise> responseAction)
    {
        this.itemClickedResponse = responseAction;
    }
}
