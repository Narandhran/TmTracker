package com.neemshade.tmtracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import com.neemShade.TmTracker.pojo.ProjectType;

import org.json.JSONArray;

import java.util.List;

import layout.FClubMember;
import layout.FLogin;
import layout.FProjectList;
import layout.FFloodProjects;
import layout.FRegisterMember;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private boolean viewIsAtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //retrive some data from server
        initialCall();


        //create login screen for initial view
        displayFragmentView(new FLogin(),getString(R.string.str_login));

    }

    private void initialCall() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.REST_URL) + getString(R.string.GET_PROJECT_TYPE_LIST), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
//                Log.d("Response: ",response.toString());
                Gson gson = new Gson();
                List<ProjectType> projectTypes = gson.fromJson(String.valueOf(response),List.class);
                globalVariable.setProjectTypes(projectTypes);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }


    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }


    private void displayFragmentView(Fragment fragment, String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        if(!viewIsAtHome){
            displayFragmentView(new FProjectList(),getString(R.string.str_project_list));
            viewIsAtHome = true;
            navigationView.getMenu().getItem(1).setChecked(true);
        }
       else{

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        String title = getString(R.string.app_name) ;

        Fragment fragment = null;
        switch(item.getItemId()){
            case R.id.nav_login:
                fragment = new FLogin();
                title = getString(R.string.str_login);
                viewIsAtHome = false;
                break;
            case R.id.nav_project_list:
                fragment = new FProjectList();
                title = getString(R.string.str_project_list);
                viewIsAtHome = true;
                break;
            case R.id.nav_club_member:
                fragment = new FClubMember();
                title = getString(R.string.str_club_member);
                viewIsAtHome = false;
                break;
            case R.id.nav_flood_projects:
                fragment = new FFloodProjects();
                title = getString(R.string.str_flood_projects);
                viewIsAtHome = false;
                break;
            case R.id.nav_register_member:
                fragment = new FRegisterMember();
                title = getString(R.string.str_register_member);
                viewIsAtHome = false;
                break;
        }


        if (fragment != null) {
            displayFragmentView(fragment,title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
