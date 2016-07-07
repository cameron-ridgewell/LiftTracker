package com.lifttracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifttracker.R;
import com.lifttracker.common.PersonalStat;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.joda.time.DateTime;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackPersonalStatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackPersonalStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackPersonalStatsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private ServerRequest svc;

    private ArrayList<PersonalStat> weights;
    private ArrayList<PersonalStat> bodyfats;
    private ArrayList<PersonalStat> chestmeasurements;
    private ArrayList<PersonalStat> waistmeasurements;

    public TrackPersonalStatsFragment() {
        // Required empty public constructor
    }

    public static TrackPersonalStatsFragment newInstance() {
        TrackPersonalStatsFragment fragment = new TrackPersonalStatsFragment();
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
        rootView = inflater.inflate(R.layout.fragment_track_personal_stats, container, false);

        setupView();

        return rootView;
    }

    private void setupView()
    {
        svc.getPersonalStatByType(new ResponseAction() {
            @Override
            public void action(Object input) {
                if (((Response) input).code() < 400)
                {
                    weights.clear();
                    weights.addAll(((Response<List<PersonalStat>>) input).body());
                }
            }
        }, PersonalStat.StatType.Weight);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View findViewById(int id)
    {
        return rootView.findViewById(id);
    }
}