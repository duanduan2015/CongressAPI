package com.example.android.congressapi;

//import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.URL;
//import android.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private JSONObject legislators;
    private JSONObject activeBills;
    private JSONObject newBills;
    private JSONObject committees;
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

        String[] urls = new String[1];
        urls[0] = "http://homework8-148505.appspot.com/main.php?query=legislators";
        new GetLegislatorJsonData().execute(urls);
    }

    private class GetLegislatorJsonData extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String[] urls) {
            long totalSize = 0;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls[0], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GlobalData.legislatorsJason = response;
                            GlobalData.getLegislators();
                            Fragment fr = new LegislatorsFrag();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_place, fr);
                            fragmentTransaction.commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
            return totalSize;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        int id = item.getItemId();
        if (id == R.id.legislators) {
            // Handle the camera action
            Fragment fr = new LegislatorsFrag();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        } else if (id == R.id.bills) {
            if (GlobalData.activeBills != null && GlobalData.newBills != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Fragment fr = new BillsFrag();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();
            } else {
                String[] urls = new String[2];
                urls[0] = "http://homework8-148505.appspot.com/main.php?query=billsTrue";
                urls[1] = "http://homework8-148505.appspot.com/main.php?query=billsNew";
                new GetBillsJsonData().execute(urls);
            }
        } else if (id == R.id.committees) {
            if (GlobalData.houseCommittees != null && GlobalData.senateCommittees != null && GlobalData.jointCommittees != null) {
                Fragment fr = new CommitteesFrag();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();
            } else {
                String[] urls = new String[1];
                urls[0] = "http://homework8-148505.appspot.com/main.php?query=committees";
                new GetCommitteesJsonData().execute(urls);
            }
        } else if (id == R.id.favorites) {
            Fragment fr = new FavoritesFrag();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        } else if (id == R.id.aboutMe) {
            Intent intent = new Intent(this, AboutMe.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class GetBillsJsonData extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String[] urls) {
            long totalSize = 0;
            JsonObjectRequest jsObjRequestActive = new JsonObjectRequest
                    (Request.Method.GET, urls[0], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GlobalData.activeBillsJason = response;
                            GlobalData.getActiveBills();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequestActive);
            JsonObjectRequest jsObjRequestNew = new JsonObjectRequest
                    (Request.Method.GET, urls[1], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GlobalData.newBillsJason = response;
                            GlobalData.getNewBills();
                            Fragment fr = new BillsFrag();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_place, fr);
                            fragmentTransaction.commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequestNew);
            return totalSize;
        }
    }
    private class GetCommitteesJsonData extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String[] urls) {
            long totalSize = 0;
            JsonObjectRequest jsObjRequestNew = new JsonObjectRequest
                    (Request.Method.GET, urls[0], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GlobalData.committeesJason = response;
                            GlobalData.getCommittees();
                            Fragment fr = new CommitteesFrag();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_place, fr);
                            fragmentTransaction.commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequestNew);
            return totalSize;
        }
    }
}
