package layout;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.User;
import com.neemshade.tmtracker.GlobalClass;
import com.neemshade.tmtracker.MainActivity;
import com.neemshade.tmtracker.R;


import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FLogin extends Fragment implements View.OnClickListener {


    private EditText userPhone,countryCodeText;
    private TelephonyManager telemanager;

    public FLogin() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_flogin, container, false);


        Activity a=getActivity();
        telemanager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        userPhone = (EditText)view.findViewById(R.id.edt_user_id);
        countryCodeText = (EditText)view.findViewById(R.id.edt_country_code);
        countryCodeText.setText("+" + getCountryDialCode(a));
        userPhone.setText(telemanager.getLine1Number());
        userPhone.setSelection(userPhone.getText().length());

        Button login = (Button)view.findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        return view;
    }


    public static String getCountryDialCode(Activity a){
        String conutryId = null;
        String contryDialCode = null;

        TelephonyManager telephonyMngr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);

        conutryId = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrContryCode=a.getResources().getStringArray(R.array.DialingCountryCode);
        for(int i=0; i<arrContryCode.length; i++){
            String[] arrDial = arrContryCode[i].split(",");
            if(arrDial[1].trim().equals(conutryId.trim())){
                contryDialCode = arrDial[0];
                break;
            }
        }
        return contryDialCode;
    }


    @Override
    public void onClick(View view) {
        view.startAnimation(new AlphaAnimation(2f,0.8f));
        String countryCode = countryCodeText.getText().toString().trim();
        String phone = userPhone.getText().toString().trim();
        String fullPhoneNumber = countryCode + phone;
        fullPhoneNumber = UserDto.purifier(fullPhoneNumber);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
         getString(R.string.REST_URL) + getString(R.string.GET_BY_PHONE) + fullPhoneNumber , null, new Response.Listener<JSONObject>() {
//                getString(R.string.REST_URL) +  getString(R.string.GET_BY_PHONE), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response",response.toString());

                final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
                UserDto userDto;
                Gson gson = new Gson();


                userDto = gson.fromJson(String.valueOf(response),UserDto.class);

                if (userDto.getUser() == null){
                    Log.d("errorResponse","invalidUser");
                    userPhone.setError("Invalid User");
                }else{
                    globalVariable.setLoginUserDto(userDto);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    FProjectList fragment2 = new FProjectList();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userObject", userDto.getUser());

                    fragment2.setArguments(bundle);
                    ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.str_project_list));
                    ft.replace(R.id.content_frame, fragment2);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.d("errorResponse","error");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }


}
