package com.neemshade.tmtracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.pojo.ProjectType;
import com.neemShade.TmTracker.pojo.ProjectUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AddProjectActivity extends AppCompatActivity {

    GlobalClass globalVariable;
    private Spinner prjtTypeSpinner,prjtLvlSpinner;
    private List<ProjectType> spinnerData;
    private Switch repeatedSwitch;
    private EditText projectGivenDateEdt,projectCommentEdt,projectThemeEdt,projectTitleEdt;
    private DatePickerDialog projectDateDialog;
    Button sendToServerBtn;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

//repeated Switch
        repeatedSwitch = (Switch)findViewById(R.id.switch_prjt_repeat);
        final TextView newProjectText = (TextView)findViewById(R.id.txt_repeat);
        repeatedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!repeatedSwitch.isChecked()) newProjectText.setText("New Project");
                else newProjectText.setText("Repeated project");
            }
        });
//Project level spinner
        prjtLvlSpinner = (Spinner)findViewById(R.id.spn_prjt_lvl);
        String[] prjtLvl = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,prjtLvl);
        prjtLvlSpinner.setAdapter(adapter);

//date events
        projectGivenDateEdt = (EditText)findViewById(R.id.edt_prjt_date);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        projectGivenDateEdt.setInputType(InputType.TYPE_NULL);
        projectGivenDateEdt.requestFocus();
        Calendar newCalendar = Calendar.getInstance();
        projectDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                projectGivenDateEdt.setText(simpleDateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        projectGivenDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectDateDialog.show();
            }
        });

        sendToServerBtn = (Button)findViewById(R.id.btn_send_to_rest);
        sendToServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setAnimation(new AlphaAnimation(2f,1f));
                projectTitleEdt = (EditText)findViewById(R.id.edt_prjt_title);
                prjtTypeSpinner = (Spinner)findViewById(R.id.spn_prjt_type);
                prjtLvlSpinner = (Spinner)findViewById(R.id.spn_prjt_lvl);
                projectThemeEdt = (EditText)findViewById(R.id.edt_prjt_theme);
                projectGivenDateEdt = (EditText)findViewById(R.id.edt_prjt_date);
                projectCommentEdt = (EditText)findViewById(R.id.edt_prjt_cmt);
            sendToServer(projectTitleEdt,prjtTypeSpinner,prjtLvlSpinner,projectThemeEdt,projectGivenDateEdt,projectCommentEdt,repeatedSwitch);
            }
        });


        serverCallSpinnerProjectType();

    }

    private void serverCallSpinnerProjectType() {
        String URL = getString(R.string.REST_URL) + getString(R.string.GET_PROJECT_TYPE_LIST);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                spinnerData = Arrays.asList(mGson.fromJson(String.valueOf(response), ProjectType[].class));
                List<ProjectType> projectTypeSpinnerData = new ArrayList<>();
                for (ProjectType p :
                        spinnerData) {
//                    Log.d("data:  ",p.getTypeName().toString());
                    projectTypeSpinnerData.add(p);

                }
                Collections.reverse( projectTypeSpinnerData);
                prjtTypeSpinner = (Spinner)findViewById(R.id.spn_prjt_type);
                ArrayAdapter<ProjectType> dataAdapter = new ArrayAdapter<ProjectType>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,projectTypeSpinnerData);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                prjtTypeSpinner.setAdapter(dataAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorResponse: ",error.toString());
            }
        });
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void sendToServer(EditText projectTitleEdt, Spinner prjtTypeSpinner, Spinner prjtLvlSpinner, EditText projectThemeEdt, EditText projectGivenDateEdt, EditText projectCommentEdt, Switch repeatedSwitch){
        globalVariable = (GlobalClass) getApplicationContext();

        ProjectUserDto projectUserDto = new ProjectUserDto();
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectTitle(projectTitleEdt.getText().toString());
        projectUser.setProjectType((ProjectType) prjtTypeSpinner.getSelectedItem());
        ProjectType projectType = (ProjectType) prjtTypeSpinner.getSelectedItem();
        Log.d("spinnerObject1",projectType.getTypeName());
        projectUser.setProjectLevel(Integer.parseInt( prjtLvlSpinner.getSelectedItem().toString()));
        projectUser.setProjectTheme(projectThemeEdt.getText().toString());
        projectUser.setIsRepeat(repeatedSwitch.isChecked());
        projectUser.setUser(globalVariable.getLoginUserDto().getUser());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = formatter.parse(projectGivenDateEdt.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        projectUser.setProjectGivenDate(date);

        projectUser.setComments(projectCommentEdt.getText().toString());

        if(projectUser.getProjectUserId() == null) projectUser.setProjectUserId(new Long(0));

        projectUserDto.setProjectUser(projectUser);
        serverCallAddProject(projectUserDto);

    }

    private void serverCallAddProject(ProjectUserDto projectUserDto) {

        Log.d("projectType",projectUserDto.getProjectUser().getProjectType().toString());
        String url = getString(R.string.REST_URL) + "projectUser/create";

//jackson

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);

        try {
            String jsonString =  mapper.writeValueAsString(projectUserDto);
            Log.d("jsonString", jsonString);
            jsonObject = new JSONObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }


//Gson
//        Gson gson;
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("yyyy-MM-dd");
//        gson = gsonBuilder.create();
//
//        try {
//            Log.d("serverCallAddProject: ",gson.toJson(projectUserDto));
//            jsonObject = new JSONObject(gson.toJson(projectUserDto));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Log.d("jsonObject: ",jsonObject.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("postREsponse: ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(jsonRequest);

    }
}
