package com.lifttracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.lifttracker.R;
import com.lifttracker.activities.FABInteractionActivity;
import com.lifttracker.activities.MainActivity;
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
public class CreateWorkoutFragment extends Fragment {
    private ServerRequest svc;
    private View rootView;
    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private ArrayList<Exercise> viewableExercises = new ArrayList<>();
    private ExerciseSearchFragmentAdapter itemsAdapter;
    private OnFragmentInteractionListener mListener;

    private EditText exercise_search;
    private ListView exercise_list_view;

    public CreateWorkoutFragment() {
        // Required empty public constructor
    }

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

    }

    private void setupFABActions()
    {
        ResponseAction ra = new ResponseAction<Integer>() {
            @Override
            public void action(Integer o) {
//                Fragment fragment = MainPageViewFragment.newInstance();
//                ((FABInteractionActivity) getActivity())
//                        .transitionFragment(R.id.fragment_container, fragment,
//                                MainActivity.MAIN_PAGE_VIEW_FRAGMENT);
            ExerciseSearchDialog exerciseSearchDialog = ExerciseSearchDialog.newInstance();
            exerciseSearchDialog.show(getActivity().getSupportFragmentManager(),
                    MainActivity.EXERCISE_SEARCH_DIALOG);
            }
        };
        ArrayList<ResponseAction> myArray = new ArrayList<ResponseAction>();
        myArray.add(ra);

        ((FABInteractionActivity) getActivity())
                .doAThing(myArray);
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
        ((FABInteractionActivity) getActivity()).responseAction();
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
