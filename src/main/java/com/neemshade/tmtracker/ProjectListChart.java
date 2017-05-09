package com.neemshade.tmtracker;

import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.GsonBuilder;
import com.neemShade.TmTracker.dto.ProjectUserDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tejesvini on 09-03-17.
 */

public class ProjectListChart {

    public static String[] projectCategories = new String[] { "Communication", "Leadership"};

    public String[] loadChart(List<ProjectUserDto> projectUserDtos, LineChart chart) {



//        Collections.sort(projectUserDtos, ProjectUserDto.fetchComparator());

        Collections.sort(projectUserDtos, fetchDateComparator());  // this comparator should be defined in ProjectUserDto

        List<List<Entry>> listOfEntries = new ArrayList<List<Entry>>();
        listOfEntries.add(new ArrayList<Entry>());
        listOfEntries.add(new ArrayList<Entry>());

        final Map<String, Integer> xAxisLabelMap = new HashMap<String, Integer>();
        final Map<Integer, String> xAxisReverseLabelMap = new HashMap<Integer, String>();
        Integer index = 0;
        for (ProjectUserDto projectUserDto :projectUserDtos) {
            if(projectUserDto == null || projectUserDto.getProjectUser() == null || projectUserDto.getProjectUser().getProjectGivenDate() == null)
                continue;
            Date givenDate = projectUserDto.getProjectUser().getProjectGivenDate();
            String label = generateLabel(givenDate);

            if(xAxisLabelMap.containsKey(label))
                continue;

            xAxisReverseLabelMap.put(index, label);
            xAxisLabelMap.put(label, index);
            index++;

        }

        Date[] runningDateArray = new Date[projectCategories.length];
        Float[] durationArray = new Float[projectCategories.length];
        Integer[] numberOfDurationArray = new Integer[projectCategories.length];
        int commLeaderIndex = 0;
        for (ProjectUserDto projectUserDto :projectUserDtos) {
            if (projectUserDto == null || projectUserDto.getProjectUser() == null || projectUserDto.getProjectUser().getProjectGivenDate() == null)
                continue;

            // skip repeated projects
            if(projectUserDto.getProjectUser().getIsRepeat())
                continue;

            Date givenDate = projectUserDto.getProjectUser().getProjectGivenDate();
            String label = generateLabel(givenDate);

            commLeaderIndex = projectUserDto.getProjectUser().getProjectType().getIsCommunication() ? 0 : 1;


            float duration =
                    findNumberOfDays(runningDateArray[commLeaderIndex], projectUserDto.getProjectUser().getProjectGivenDate());
            Integer xAxisIndex = xAxisLabelMap.get(label) == null ? 0 : xAxisLabelMap.get(label);


//            Log.d("duration", duration + " " + xAxisIndex + " " + projectUserDto.getProjectUser().getProjectType().getTypeName() +
//                    projectUserDto.getProjectUser().getProjectLevel());
            Entry entry = new Entry(xAxisIndex, duration);
            listOfEntries.get(commLeaderIndex).add(entry);

            if(durationArray[commLeaderIndex] == null)
                durationArray[commLeaderIndex] = 0f;
            if(numberOfDurationArray[commLeaderIndex] == null)
                numberOfDurationArray[commLeaderIndex] = 0;

            runningDateArray[commLeaderIndex] = projectUserDto.getProjectUser().getProjectGivenDate();

            durationArray[commLeaderIndex] += duration;
            numberOfDurationArray[commLeaderIndex]++;
        }

        Float[] avgDurationArray = new Float[projectCategories.length];
        String[] avgDurationStringArray = new String[projectCategories.length];
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        LineDataSet dataSet = new
//        LineData data = new LineData();
        for (int i = 0; i < projectCategories.length ; i++  ) {
//            Log.d("beforeCheck", i + " " + listOfEntries.get(i).size());
            if(listOfEntries.get(i) == null || listOfEntries.get(i).size() <= 0)
                continue;

//            Log.d("afterCheck", i + " ");
//            Log.d("before lindatset", listOfEntries.get(i).size() + " " + i);

            LineDataSet lineDataSet = new LineDataSet(listOfEntries.get(i), projectCategories[i]);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//            int color = i == 0 ? R.color.green : R.color.blue;

            int color = ColorTemplate.MATERIAL_COLORS[i];

            lineDataSet.setColor(color);
            lineDataSet.setCircleColor(color);
            dataSets.add(lineDataSet);
//            data.addDataSet(lineDataSet);


            avgDurationArray[i] = numberOfDurationArray[i] == null || numberOfDurationArray[i] == 0 ||
                    durationArray[i] == null ? 0 :
                    durationArray[i] / numberOfDurationArray[i];
            avgDurationStringArray[i] = avgDurationArray[i] < 30 ?
                    avgDurationArray[i] + " days" :
                    avgDurationArray[i] / 30 + " months";

            avgDurationStringArray[i] = projectCategories[i] + " projects avg " + avgDurationStringArray[i];
        }

        // display avgDurationStringArray

//        LineData data = new LineData(dataSets);

//        data.addDataSet(dataSets);
        LineData data = new LineData(dataSets);

        chart.setData(data);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String label = xAxisReverseLabelMap.get((int) value);
                return label == null ? "Invalid" : label;
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(10);
        xAxis.setValueFormatter(formatter);


        chart.invalidate();

        return avgDurationStringArray;
    }

    private Comparator<? super ProjectUserDto> fetchDateComparator() {
        return new Comparator<ProjectUserDto>() {
            @Override
            public int compare(ProjectUserDto pud1, ProjectUserDto pud2) {
                return pud1.getProjectUser().getProjectGivenDate().compareTo(pud2.getProjectUser().getProjectGivenDate());
            }
        };
    }

    private long findNumberOfDays(Date firstDate, Date secondDate) {
        if(firstDate == null || secondDate == null)
            return 0;

        long diff = secondDate.getTime() - firstDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    private String generateLabel(Date givenDate) {
        final String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        return months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
    }


//    private void loadChartData() {
//
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("yyyy-MM-dd");
//        final Gson gson = gsonBuilder.create();
//        queue = Volley.newRequestQueue(getApplicationContext());
//
////        final JSONArray jsonArray = new JSONArray();
//
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.137.216:8080/projectUser/findByUserId/1", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
////                Log.d("Response: ", response.toString());
//
//
//                List<ProjectUserDto> projectUserDtos = Arrays.asList(gson.fromJson(response.toString(), ProjectUserDto[].class));
//                loadChart(projectUserDtos);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error: ", error.toString());
//            }
//        }) ;
//
//
//
//
//        queue.add(jsonArrayRequest);
//    }

//    private void loadDataObjects(List<DataObjects> dataObjectsList) {
//        DataObjects dataObjects = new DataObjects("10","21");
//        dataObjectsList.add(dataObjects);
//        dataObjects = new DataObjects("20","43");
//        dataObjectsList.add(dataObjects);
//        dataObjects = new DataObjects("30","47");
//        dataObjectsList.add(dataObjects);
//        dataObjects = new DataObjects("40","21");
//        dataObjectsList.add(dataObjects);
//        dataObjects = new DataObjects("50","44");
//        dataObjectsList.add(dataObjects);
//    }
}
