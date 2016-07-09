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
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Period;

import lecho.lib.hellocharts.model.Axis;
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

    private LineChartView weightChart;
    private LineChartView bodyfatsChart;
    private LineChartView chestmeasurementsChart;
    private LineChartView waistmeasurementsChart;

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
        loadPersonalStats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_track_personal_stats, container, false);

        return rootView;
    }

    private void setupView()
    {
//        PersonalStat myStat = new PersonalStat(PersonalStat.StatType.Weight, 162,
//                PersonalStat.Unit.lb);
//        myStat.setTime(myStat.getTime().minusDays(15));
//        svc.addPersonalStat(myStat);
//        myStat.setTime(myStat.getTime().minusDays(3));
//        svc.addPersonalStat(myStat);
//        myStat.setTime(myStat.getTime().minusDays(3));
//        myStat.setMeasurement(100);
//        svc.addPersonalStat(myStat);

        int days = 30;
        setupWeightChart(days);

    }
    
    private void loadPersonalStats()
    {
        svc.getPersonalStatByType(new ResponseAction<ArrayList<PersonalStat>>() {
            @Override
            public void action(ArrayList<PersonalStat> input) {
                weights = input;
                setupView();
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

//        ArrayList<Entry> entries = new ArrayList<>();

//        PersonalStat firstListedPersonalStat = null;
//        PersonalStat lastKnownPersonalStat = null;
//
//        for (int i = 0; i < list.size(); i++)
//        {
//            int days = getDaysBetween(today, list.get(i).getTime());
//            Log.e("days", "" + days);
//            if(days < dateRange) {
//                if (firstListedPersonalStat == null)
//                {
//                    firstListedPersonalStat = list.get(i);
//                }
//                Log.e("tag", list.get(i).getTime().toString());
//                entries.add(new Entry(days, (float) list.get(i).getMeasurement()));
//            }
//            // if not within dateRange, update the most recent point
//            else
//            {
//                Log.e("here", entries.size() + "");
//                lastKnownPersonalStat = list.get(i);
//            }
//        }
//

//        LineDataSet lineDataSet = new LineDataSet(entries, "string");
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(lineDataSet);
//        for(Entry e: entries)
//        {
//            Log.e(e.getX() + "", "thin");
//        }
//        return new LineChartData(new Line())

    private LineChartData getLCDfromArrayList(ArrayList<PersonalStat> list, int dateRange,
                                              boolean continuous)
    {

        DateTime today = DateTime.now();
        DateTime myTime = new DateTime().now().minusDays(dateRange);

        List<Line> lines = new ArrayList<Line>();

        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < list.size(); ++j) {
            values.add(new PointValue(list.get(j).getTime().getMillis(),
                    (float) list.get(j).getMeasurement()));
        }

        if (values.size() > 0)
        {
//            double slope = (firstListedPersonalStat.getMeasurement()
//                    - lastKnownPersonalStat.getMeasurement())
//                    / ((double) getDaysBetween(firstListedPersonalStat.getTime(),
//                    lastKnownPersonalStat.getTime()));
//            double stat = (slope * (double) getDaysBetween(myTime,
//                    firstListedPersonalStat.getTime()));

            //entries.add(0, new Entry(0, (float) (firstListedPersonalStat.getMeasurement() + stat)));
        }

        Line line = new Line(values);
        line.setColor(getResources().getColor(R.color.colorAccent));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setHasLines(true);
        lines.add(line);

        if (values.size() > 0) {
            List<PointValue> extension = new ArrayList<>();
            if (continuous && getDaysBetween(today, list.get(list.size() - 1).getTime()) > 0) {
                extension.add(new PointValue(list.get(list.size() - 1).getTime().getMillis(),
                        (float) list.get(list.size() - 1).getMeasurement()));
                extension.add(new PointValue(today.getMillis(),
                        (float) list.get(list.size() - 1).getMeasurement()));

            }
            Line extensionLine = new Line(extension);
            extensionLine.setHasPoints(false);
            lines.add(extensionLine);
        }
        else
        {
            //TODO extend first mark across
        }


        LineChartData data = new LineChartData(lines);

        if (true) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (true) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            axisY.setAutoGenerated(true);
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        return data;
    }

    private int getDaysBetween(DateTime dateTime1, DateTime dateTime2)
    {
        return (int) (new DateTime(dateTime1.getMillis()
                - dateTime2.getMillis())).getMillis() / 1000 / 60 / 60 / 24;
    }

    private void setupWeightChart(int days)
    {
        weightChart = (LineChartView) findViewById(R.id.weightsChart);
        weightChart.setLineChartData(getLCDfromArrayList(weights, days, true));

        ArrayList<Float> recentStats = new ArrayList<>();
        for (PointValue pv : weightChart.getLineChartData().getLines().get(0).getValues())
        {
            recentStats.add(pv.getY());
        }

        Viewport v = weightChart.getMaximumViewport();
        Collections.sort(recentStats);
        int minvalue = Math.round(recentStats.get(0) / 10) * 10 - 10;
        int maxvalue = Math.round(recentStats.get(recentStats.size() - 1) / 10) * 10 + 10;
        v.set(v.left, maxvalue, v.right, minvalue);
        weightChart.setMaximumViewport(v);
        weightChart.setCurrentViewport(v);
    }
}
