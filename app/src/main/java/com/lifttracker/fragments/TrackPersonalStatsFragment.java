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
import com.lifttracker.elements.DateStatChart;
import com.lifttracker.utilities.ResponseAction;
import com.lifttracker.utilities.ServerRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Period;

import lecho.lib.hellocharts.formatter.AxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
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

    private DateStatChart weightChart;
    private DateStatChart bodyfatsChart;
    private DateStatChart chestmeasurementsChart;
    private DateStatChart waistmeasurementsChart;

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
        int days = 30;
        weightChart = new DateStatChart(getContext(), rootView);
        weightChart.setChartDataView(R.id.weightsChart);
        weightChart.setDateRange(days);
        weightChart.setPersonalStats(weights);

        loadPersonalStats();
    }

    private void loadPersonalStats()
    {
        svc.getPersonalStatByType(new ResponseAction<ArrayList<PersonalStat>>() {
            @Override
            public void action(ArrayList<PersonalStat> input) {
                weights = input;
                weightChart.setPersonalStats(weights);
                refreshCharts();
            }
        }, PersonalStat.StatType.Weight);
        svc.getPersonalStatByType(new ResponseAction<ArrayList<PersonalStat>>() {
            @Override
            public void action(ArrayList<PersonalStat> input) {
                bodyfats = input;
            }
        }, PersonalStat.StatType.BodyFatPercentage);
        svc.getPersonalStatByType(new ResponseAction<ArrayList<PersonalStat>>() {
            @Override
            public void action(ArrayList<PersonalStat> input) {
                chestmeasurements = input;
            }
        }, PersonalStat.StatType.ChestMeasurement);
        svc.getPersonalStatByType(new ResponseAction<ArrayList<PersonalStat>>() {
            @Override
            public void action(ArrayList<PersonalStat> input) {
                waistmeasurements = input;
            }
        }, PersonalStat.StatType.WaistMeasurement);
    }

    private void refreshCharts()
    {
        weightChart.renderChart(true);
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
