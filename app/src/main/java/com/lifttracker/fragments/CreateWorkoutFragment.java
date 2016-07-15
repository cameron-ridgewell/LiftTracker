package com.lifttracker.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lifttracker.R;
import com.lifttracker.activities.FABInteractionActivity;
import com.lifttracker.activities.MainActivity;
import com.lifttracker.common.Exercise;
import com.lifttracker.common.SuperSetExerciseList;
import com.lifttracker.elements.CreateWorkoutListAdapter;
import com.lifttracker.elements.ExerciseSearchFragmentAdapter;
import com.lifttracker.utilities.MemoryRequisition;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;

import org.joda.time.DateTime;

import mehdi.sakout.fancybuttons.FancyButton;

public class CreateWorkoutFragment extends Fragment {
    private ServerRequest svc;
    private View rootView;
    private SuperSetExerciseList exerciseList = new SuperSetExerciseList();
    private CreateWorkoutListAdapter itemsAdapter;
    private OnFragmentInteractionListener mListener;

    private RecyclerView exercise_list_view;
    private FancyButton timerButton;

    public final ResponseAction<Integer> fabClickAction = new ResponseAction<Integer>() {
        @Override
        public void action(Integer o) {
            ExerciseSearchDialog exerciseSearchDialog = ExerciseSearchDialog.newInstance();
            exerciseSearchDialog.show(getActivity().getSupportFragmentManager(),
                    MainActivity.EXERCISE_SEARCH_DIALOG);
            ResponseAction<Exercise> itemClickedAction = new ResponseAction<Exercise>() {
                @Override
                public void action(Exercise exercise) {
                    exerciseList.add(exercise);
                    //itemsAdapter.notifyItemInserted(itemsAdapter.getItemCount()-1);
                    itemsAdapter.notifyDataSetChanged();
                }
            };
            exerciseSearchDialog.addClickedAction(itemClickedAction);
        }
    };

    public CreateWorkoutFragment() {
        // Required empty public constructor
    }

//    private class ReceiverThread extends Thread {
//        @Override
//        public void run() {
//            Activity_name.this.runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
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
        exerciseList.add(new Exercise("Exercise Name"));
        exercise_list_view = (RecyclerView) findViewById(R.id.list_view);
        itemsAdapter = new CreateWorkoutListAdapter(getContext(),
                exerciseList);
        exercise_list_view.setAdapter(itemsAdapter);
        exercise_list_view.setLayoutManager(new LinearLayoutManager(getContext()));
        timerButton = (FancyButton) findViewById(R.id.timer_button);
    }

    private void setupFABActions()
    {
        ArrayList<ResponseAction> myArray = new ArrayList<ResponseAction>();
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
