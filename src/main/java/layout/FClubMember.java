package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemshade.tmtracker.ClubMemberAdapter;
import com.neemshade.tmtracker.GlobalClass;
import com.neemshade.tmtracker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FClubMember extends Fragment implements AdapterView.OnItemSelectedListener {

    GlobalClass globalVariable;

    private Gson gson;
    private RequestQueue requestQueue;
    private Spinner clubListSpinner;
    private ListView clubMemberList;

    private List<UserDto> clubDtoMemberList;
    private List<ClubDto> clubDtos;


    public FClubMember() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fclub_member, container, false);

        globalVariable = (GlobalClass) getActivity().getApplication();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gson = gsonBuilder.create();
        requestQueue = Volley.newRequestQueue(getContext());

        clubListSpinner = (Spinner) view.findViewById(R.id.spn_club_list);
        clubMemberList = (ListView)view.findViewById(R.id.list_member_list);

        clubListSpinner.setOnItemSelectedListener(this);

        fetchPosts();
        return view;
    }


    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET,
                 getString(R.string.REST_URL) + getString(R.string.GET_CLUB_CODE) + globalVariable.getLoginUserDto().getUser().getUserId() + getString(R.string.GET_MEMBER_TYPE),onPostsLoaded, onPostsError);
//                getString(R.string.REST_URL) + getString(R.string.GET_CLUB_CODE), onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }


    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            clubDtos = Arrays.asList(gson.fromJson(String.valueOf(response), ClubDto[].class));

            ArrayAdapter<String> clubDtoArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
            for (ClubDto c : clubDtos) {
                clubDtoArrayAdapter.add(c.getClub().getClubCode());
            }

            clubDtoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            clubListSpinner.setAdapter(clubDtoArrayAdapter);
        }


    };

    private  final Response.Listener<String> onPostsLoaded1 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            clubDtoMemberList = Arrays.asList(gson.fromJson(response,UserDto[].class));
            ClubMemberAdapter clubMemberAdapter = new ClubMemberAdapter(getActivity(),clubDtoMemberList);
            clubMemberList.setAdapter(clubMemberAdapter);
        }
    };

   private final Response.ErrorListener onPostsError1 = new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {

       }
   };


    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("id: ",clubDtos.get(i).getClub().getClubId().toString());
        String selectedClub = clubDtos.get(i).getClub().getClubId().toString();

        StringRequest request1 = new StringRequest(Request.Method.GET,
                getString(R.string.REST_URL) +  getString(R.string.GET_CLUB_MEMBER) + selectedClub + getString(R.string.GET_MEMBER_TYPE) ,onPostsLoaded1,onPostsError1);
//                getString(R.string.REST_URL) + getString(R.string.GET_CLUB_MEMBER),onPostsLoaded1,onPostsError1);
        requestQueue.add(request1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}