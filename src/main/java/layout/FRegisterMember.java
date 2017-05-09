package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FRegisterMember extends Fragment{


   private Button btnRegisterMember;
   private RequestQueue requestQueue;

    public FRegisterMember() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fregister_member, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        btnRegisterMember = (Button)view.findViewById(R.id.btn_register_member_from_csv);
        btnRegisterMember.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
              serverCallRegisterMember();
            }

            });

        return view;
    }

    private void serverCallRegisterMember() {
        String URL = getString(R.string.REST_URL) + getString(R.string.GET_REGISTER_MEMBER);
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("registerResponse ",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RegisterCSV error", error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}