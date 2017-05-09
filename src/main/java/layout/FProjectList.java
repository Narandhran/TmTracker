package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.pojo.User;
import com.neemshade.tmtracker.AddProjectActivity;
import com.neemshade.tmtracker.GlobalClass;
import com.neemshade.tmtracker.ProjectListChart;
import com.neemshade.tmtracker.ProjectUserAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.github.mikephil.charting.data.Entry;
import com.neemshade.tmtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FProjectList extends Fragment {


    private GlobalClass globalVariable;
    User user;

    private Gson gson;
    private RequestQueue requestQueue;
    private List<ProjectUserDto> projectUserDtos;
    private RecyclerView recyclerView ;
    private ProjectUserAdapter projectUserAdapter;
    String userName;
    public FProjectList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fproject_list, container, false);
       if(savedInstanceState == null){


           globalVariable = (GlobalClass) getActivity().getApplication();

           userName = globalVariable == null ||
                   globalVariable.getLoginUserDto() == null ||
                   globalVariable.getLoginUserDto().getUser() == null ||
                   globalVariable.getLoginUserDto().getUser().getUserName() == null
                   ? "Invalid" : globalVariable.getLoginUserDto().getUser().getUserName();

           TextView welcomeName = (TextView)view.findViewById(R.id.txt_welcomeName);
           welcomeName.setText("Welcome " + userName);
           recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
           GsonBuilder gsonBuilder = new GsonBuilder();
           gsonBuilder.setDateFormat("yyyy-MM-dd");
           gson = gsonBuilder.create();

           requestQueue = Volley.newRequestQueue(getContext());



           fetchPosts();

           if (user != null) {
               Bundle bundle = getArguments();
               User user= (User) bundle.getSerializable("userObject");
           }



           Button button = (Button)view.findViewById(R.id.btn_add_project);
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(getActivity(),AddProjectActivity.class));
               }
           });

       }
        return view;
    }

    private void fetchPosts() {

        String userId = globalVariable == null ||
                globalVariable.getLoginUserDto() == null ||
                globalVariable.getLoginUserDto().getUser() == null ||
                globalVariable.getLoginUserDto().getUser().getUserId() == null
                ? "Invalid" : globalVariable.getLoginUserDto().getUser().getUserId().toString();

        StringRequest request = new StringRequest(Request.Method.GET,
                getString(R.string.REST_URL) + getString(R.string.GET_PROJECT_LIST) + userId,
//                getString(R.string.REST_URL) + getString(R.string.GET_PROJECT_LIST),
                onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Log.d("response", response.toString());

            projectUserDtos = Arrays.asList(gson.fromJson(response, ProjectUserDto[].class));

            projectUserAdapter = new ProjectUserAdapter(projectUserDtos);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(projectUserAdapter);


            List<ProjectUserDto> chartProjectUserDtos = new ArrayList<ProjectUserDto>();
            for (int i = 0; i < projectUserDtos.size(); i++) {
                chartProjectUserDtos.add(null);
            }
            Collections.copy(chartProjectUserDtos, projectUserDtos);

            createChart(chartProjectUserDtos,getView());

        }
    };

    private void createChart(List<ProjectUserDto> projectUserDtos, View view) {

        LineChart chart = (LineChart) view.findViewById(R.id.chart);
        ProjectListChart projectListChart = new ProjectListChart();
        String[] msgs = projectListChart.loadChart(projectUserDtos,chart);

        TextView commText = (TextView)view.findViewById(R.id.txt_communication_avg_msg);
        commText.setText(msgs[0]);

        TextView leadershipText = (TextView)view.findViewById(R.id.txt_leadership_avg_msg);
        leadershipText.setText(msgs[1]);
    }

    private Comparator<? super ProjectUserDto> dateComparator() {
        return new Comparator<ProjectUserDto>() {
            @Override
            public int compare(ProjectUserDto projectUserDto, ProjectUserDto t1) {
                return projectUserDto.getProjectUser().getProjectGivenDate().compareTo(t1.getProjectUser().getProjectGivenDate());
            }
        };
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
