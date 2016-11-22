package com.example.android.congressapi;

//import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by duanduan on 11/20/16.
 */
public class LegislatorsFrag extends Fragment {
    private JSONObject legislators;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Legislators");
        View view = inflater.inflate(R.layout.legislators, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("By States"));
        tabLayout.addTab(tabLayout.newTab().setText("House"));
        tabLayout.addTab(tabLayout.newTab().setText("Senate"));
        tabLayout.setTabTextColors (Color.LTGRAY, Color.DKGRAY);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String[] urls = new String[3];
        urls[0] = "http://homework8-148505.appspot.com/main.php?query=legislators";
        GetJasonData getter = new GetJasonData();
        getter.doInBackground(urls);
        //getter.onPostExecute(legislators);
        /*JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls[0], null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        legislators = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);*/
        return view;
    }
    private class GetJasonData extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String[] urls) {
            int count = urls.length;
            long totalSize = 0;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls[0], null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                           legislators = response;
                            Log.i("data", legislators.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
            return totalSize;
        }
    }

    public static class TabFragment1 extends Fragment {
        //private ArrayAdapter<String> listAdapter ;
        private LegislatorAdapter listAdapter ;
        public static int [] prgmImages={R.drawable.bills,R.drawable.legislators,R.drawable.speaker};
        public static String[] planets = new String[] { "Mercury_house", "Venus_senate", "Earth_senate"};
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View legislatorsByStateView = inflater.inflate(R.layout.legislators_by_states, container, false);
            ListView mDrawerList  = (ListView) legislatorsByStateView.findViewById(R.id.legislatorsStates);
            //String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars","Jupiter", "Saturn", "Uranus", "Neptune"};
            ArrayList<String> planetList = new ArrayList<String>();
            planetList.addAll(Arrays.asList(planets));
            //listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.legislators_item, planetList);
            listAdapter = new LegislatorAdapter(getActivity(), planets, prgmImages);
            mDrawerList.setAdapter(listAdapter);

            return legislatorsByStateView;
        }
    }

    public static class TabFragment2 extends Fragment {
        //private ArrayAdapter<String> listAdapter ;
        private LegislatorAdapter listAdapter ;
        public static int [] prgmImages={R.drawable.bills,R.drawable.legislators,R.drawable.speaker};
        public static String[] planets = new String[] { "Mercury_house", "Venus_senate", "Earth_senate"};
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View legislatorsByHouseView = inflater.inflate(R.layout.legislators_by_house, container, false);
            ListView mDrawerList  = (ListView) legislatorsByHouseView.findViewById(R.id.legislatorsHouse);
            ArrayList<String> planetList = new ArrayList<String>();
            planetList.addAll(Arrays.asList(planets));
            //listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.legislators_item, planetList);
            listAdapter = new LegislatorAdapter(getActivity(), planets, prgmImages);
            mDrawerList.setAdapter(listAdapter);
            return legislatorsByHouseView;
        }
    }

    public static class TabFragment3 extends Fragment {
        private LegislatorAdapter listAdapter ;
        public static int [] prgmImages={R.drawable.bills,R.drawable.legislators,R.drawable.speaker};
        public static String[] planets = new String[] { "Mercury_house", "Venus_senate", "Earth_senate"};
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View legislatorsBySenateView = inflater.inflate(R.layout.legislators_by_senate, container, false);
            ListView mDrawerList  = (ListView) legislatorsBySenateView.findViewById(R.id.legislatorsSenate);
            ArrayList<String> planetList = new ArrayList<String>();
            planetList.addAll(Arrays.asList(planets));
            //listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.legislators_item, planetList);
            listAdapter = new LegislatorAdapter(getActivity(), planets, prgmImages);
            mDrawerList.setAdapter(listAdapter);
            return legislatorsBySenateView;
        }
    }

    public static class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    TabFragment1 tab1 = new TabFragment1();
                    return tab1;
                case 1:
                    TabFragment2 tab2 = new TabFragment2();
                    return tab2;
                case 2:
                    TabFragment3 tab3 = new TabFragment3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}

