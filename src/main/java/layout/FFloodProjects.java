package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.ProjectType;
import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemshade.tmtracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejesvini on 07-03-17.
 */
public class FFloodProjects extends Fragment {

    private RequestQueue requestQueue;
    private Spinner spnUserList, spnProjecttypeCommunication,spnProjecttypeLeadership, spnProjectLevelCommunication,spnProjectLevelLeadership;
    public FFloodProjects() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fflood_projects, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        spnUserList = (Spinner)view.findViewById(R.id.spn_users);

        spnProjectLevelCommunication = (Spinner)view.findViewById(R.id.spn_project_level_communication);
        spnProjectLevelLeadership = (Spinner)view.findViewById(R.id.spn_project_level_leadership);
        Integer[] prjtLvl = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,prjtLvl);
        spnProjectLevelCommunication.setAdapter(adapter);
        spnProjectLevelLeadership.setAdapter(adapter);

        spnProjecttypeCommunication = (Spinner)view.findViewById(R.id.spn_project_type_communication);
        spnProjecttypeLeadership = (Spinner)view.findViewById(R.id.spn_project_type_leadership);

        serverCallUserList();
        serverCallProjectType();

        Button btnDummyProjects = (Button)view.findViewById(R.id.btn_dummy_projects);
        btnDummyProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDto userDtos = (UserDto) spnUserList.getSelectedItem();

                ProjectType communicationProjectType = (ProjectType) spnProjecttypeCommunication.getSelectedItem();
                ProjectType leadershipProjectType = (ProjectType) spnProjecttypeLeadership.getSelectedItem();

                Integer communicationProjectLevel = (Integer) spnProjectLevelCommunication.getSelectedItem();
                Integer leadershipProjectLevel = (Integer) spnProjectLevelLeadership.getSelectedItem();

                ProjectUser comminicationProjectUser = new ProjectUser();
                comminicationProjectUser.setUser(userDtos.getUser());
                comminicationProjectUser.setProjectType(communicationProjectType);
                comminicationProjectUser.setProjectLevel(communicationProjectLevel);

                ProjectUser leadershipProjectUser = new ProjectUser();
                leadershipProjectUser.setUser(userDtos.getUser());
                leadershipProjectUser.setProjectType(leadershipProjectType);
                leadershipProjectUser.setProjectLevel(leadershipProjectLevel);

                serverCallSendDummyProjects(comminicationProjectUser,leadershipProjectUser,requestQueue);
            }
        });


        return view;

    }

    private void serverCallSendDummyProjects(ProjectUser communicationProjectUser, ProjectUser leadershipProjectUser, RequestQueue requestQueue) {
        String URL = getString(R.string.REST_URL) + getString(R.string.POST_DUMMY_PROJECTS);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject communicationJsonObject = null,leadershipJsonObject = null;
        JSONArray jsonArray = new JSONArray();
        try {
            String communicationObjectString = mapper.writeValueAsString(communicationProjectUser);
            communicationJsonObject = new JSONObject(communicationObjectString);

            String leadershipObjectString = mapper.writeValueAsString(leadershipProjectUser);
            leadershipJsonObject = new JSONObject(leadershipObjectString);


            jsonArray.put(communicationJsonObject);
            jsonArray.put(leadershipJsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("dummyResponse", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dummyError", error.toString());
            }

        });
        requestQueue.add(jsonArrayRequest);
    }

    private void serverCallProjectType() {
        String URL = getString(R.string.REST_URL) + getString(R.string.GET_PROJECT_TYPE_LIST);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ProjectType- Response ",response.toString());
                ObjectMapper mapper = new ObjectMapper();
                List<ProjectType> lists = new ArrayList<>();

                ArrayAdapter<ProjectType> communicationSpinnerDataAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item);
                ArrayAdapter<ProjectType>leadershipSpinnerDataAdapter = new ArrayAdapter<ProjectType>(getContext(),
                        android.R.layout.simple_spinner_item);
                communicationSpinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                communicationSpinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                try {
                    lists =  mapper.readValue(String.valueOf(response), new TypeReference<List<ProjectType>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (ProjectType projectType : lists){
                    if(projectType.getIsCommunication()) {
                        communicationSpinnerDataAdapter.add(projectType);
                    }
                    else
                    {
                        leadershipSpinnerDataAdapter.add(projectType);
                    }
                }
                spnProjecttypeCommunication.setAdapter(communicationSpinnerDataAdapter);
                spnProjecttypeLeadership.setAdapter(leadershipSpinnerDataAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorResponse ", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void serverCallUserList() {
        String URL = getString(R.string.REST_URL)  + getString(R.string.GET_USER_LIST);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("UserList- Response ",response.toString());
                ObjectMapper mapper = new ObjectMapper();
                List<UserDto> lists = new ArrayList<>();

                ArrayAdapter<UserDto> userSpinnerDataAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item);
                userSpinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                try {
                    lists =  mapper.readValue(String.valueOf(response), new TypeReference<List<UserDto>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (UserDto user : lists){
                    userSpinnerDataAdapter.add(user);
                }
                spnUserList.setAdapter(userSpinnerDataAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorResponse ", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
