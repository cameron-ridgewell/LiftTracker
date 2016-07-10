package com.lifttracker.elements;

import android.content.Context;
import android.view.View;

import com.lifttracker.R;
import com.lifttracker.common.PersonalStat;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by cameronridgewell on 7/10/16.
 */
public class DateStatChart {
    private LineChartView lineChartView;
    private ArrayList<PersonalStat> personalStats;
    private int chartDataView;
    private int dateRange;
    
    private Context context;
    private View rootView;

    public DateStatChart(Context context, View rootView)
    {
        this.context = context;
        this.rootView = rootView;
    }

    public LineChartView getLineChartView() {
        return lineChartView;
    }

    public ArrayList<PersonalStat> getPersonalStats() {
        return personalStats;
    }

    public void setPersonalStats(ArrayList<PersonalStat> personalStats) {
        this.personalStats = personalStats;
    }

    public int getChartDataView() {
        return chartDataView;
    }

    public void setChartDataView(int chartDataView) {
        this.chartDataView = chartDataView;
        this.lineChartView = (LineChartView) rootView.findViewById(chartDataView);
    }

    public int getDateRange() {
        return dateRange;
    }

    public void setDateRange(int dateRange) {
        this.dateRange = dateRange;
    }

    private LineChartData getLCDfromArrayList(ArrayList<PersonalStat> list, boolean continuous)
    {
        DateTime today = DateTime.now();
        DateTime dateRangeTime = today.minusDays(dateRange);

        List<Line> lines = new ArrayList<Line>();

        // Read in the personalStats
        List<PointValue> values = new ArrayList<PointValue>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();

        for (int i = 0; i < list.size(); i++) {
            if (i != 0)
            {
                //if not the first point, fill in points between this one and previous, by days
                double slope = getSlopeFromPersonalStats(list.get(i), list.get(i-1));
                for (int j = 1;
                     j < getDaysBetween(list.get(i).getTime(), list.get(i-1).getTime()); j++)
                {
                    double measurement = slope * j + list.get(i-1).getMeasurement();
                    DateTime time = new DateTime(list.get(i-1).getTime()).plusDays(j);
                    AxisValue axisValue = new AxisValue(time.getMillis() / 1000 / 60 / 60 / 24);
                    axisValue.setLabel(time.toString("MMMM dd"));
                    axisValues.add(axisValue);
                    values.add(
                            new PointValue((int) (time.getMillis() / 1000 / 60 / 60 / 24),
                                    (float) measurement));
                }
            }
            AxisValue axisValue = new AxisValue(list.get(i).getTime().getMillis() / 1000 / 60 / 60 / 24);
            axisValue.setLabel(list.get(i).getTime().toString("MMMM dd"));
            axisValues.add(axisValue);
            values.add(
                    new PointValue((int) (list.get(i).getTime().getMillis() / 1000 / 60 / 60 / 24),
                            (float) list.get(i).getMeasurement()));
        }

        // Format Line
        Line line = new Line(values);
        line.setColor(context.getResources().getColor(R.color.colorAccent))
                .setHasPoints(false)
                .setStrokeWidth(4).setCubic(false)
                .setHasLines(true);
        lines.add(line);

        // Extend last mark across to today
        if (values.size() > 0) {
            List<PointValue> extension = new ArrayList<>();
            if (continuous && getDaysBetween(today, list.get(list.size() - 1).getTime()) > 0) {
                for (int j = 0;
                     j <= getDaysBetween(today, list.get(list.size()-1).getTime()); j++)
                {
                    DateTime time = new DateTime(list.get(list.size()-1).getTime()).plusDays(j);
                    AxisValue axisValue = new AxisValue(time.getMillis() / 1000 / 60 / 60 / 24);
                    if(j != 0) {
                        axisValue.setLabel(time.toString("MMMM dd"));
                        axisValues.add(axisValue);
                    }
                    extension.add(
                            new PointValue((int) (time.getMillis() / 1000 / 60 / 60 / 24),
                                    (float) list.get(list.size() - 1).getMeasurement()));
                }
            }
            Line extensionLine = new Line(extension);
            extensionLine.setHasPoints(false)
                    .setStrokeWidth(4).setCubic(false);
            extensionLine.setColor(context.getResources().getColor(R.color.colorAccentLight));
            lines.add(0, extensionLine);
        }

        LineChartData data = new LineChartData(lines);

        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);

        axisX.setName(" ");
        axisX.setHasTiltedLabels(true);
        axisX.setAutoGenerated(false);

        //TODO switch on stat_type
        axisY.setName("Weights (lb)");
        axisY.setAutoGenerated(true);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        return data;
    }

    public void renderChart(boolean continuous)
    {
        lineChartView.setLineChartData(getLCDfromArrayList(personalStats, continuous));

        ArrayList<Float> recentStats = new ArrayList<>();
        for (PointValue pv : lineChartView.getLineChartData().getLines().get(continuous ? 1 : 0).getValues())
        {
            recentStats.add(pv.getY());
        }

        Viewport v = lineChartView.getMaximumViewport();
        Collections.sort(recentStats);
        int minvalue = Math.round(recentStats.get(0) / 10) * 10 - 10;
        int maxvalue = Math.round(recentStats.get(recentStats.size() - 1) / 10) * 10 + 10;
        v.set(v.left, maxvalue, v.right, minvalue);
        lineChartView.setMaximumViewport(v);
        v.set(DateTime.now().minusDays(dateRange).getMillis()  / 1000 / 60 / 60 / 24,
                v.top, v.right, v.bottom);
        lineChartView.setCurrentViewport(v);
    }

    private double getSlopeFromPersonalStats(PersonalStat ps1, PersonalStat ps2)
    {
        double measDiff = ps1.getMeasurement() - ps2.getMeasurement();
        double dayDiff = getDaysBetween(ps1.getTime(), ps2.getTime());
        return measDiff / dayDiff;


    }

    private int getDaysBetween(DateTime dateTime1, DateTime dateTime2)
    {
        return (int) (new DateTime(dateTime1.getMillis()
                - dateTime2.getMillis())).getMillis() / 1000 / 60 / 60 / 24;
    }
}
