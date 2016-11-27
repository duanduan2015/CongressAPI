package com.example.android.congressapi;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.system.Os;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by duanduan on 11/20/16.
 */
public class LegislatorsFrag extends Fragment {

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
        return view;
    }


    public static class TabFragment1 extends Fragment {
        private LegislatorAdapter listAdapter ;
        private ListView legislatorList;
        Map<String, Integer> mapIndex;
        View legislatorsByStateView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            legislatorsByStateView = inflater.inflate(R.layout.legislators_by_states, container, false);
            legislatorList  = (ListView) legislatorsByStateView.findViewById(R.id.legislatorsStates);
            listAdapter = new LegislatorAdapter(getActivity(), GlobalData.getNames(GlobalData.legislators),
                    GlobalData.getImgUrls(GlobalData.legislators), GlobalData.getLabels(GlobalData.legislators), 0);
            legislatorList.setAdapter(listAdapter);
            generateIndexList(GlobalData.getStatesName(GlobalData.legislators), legislatorsByStateView);
            return legislatorsByStateView;
        }
        private void generateIndexList(String[] names, View view) {
            mapIndex = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                String index = name.substring(0, 1);
                if (mapIndex.get(index) == null) {
                    mapIndex.put(index, i);
                }
            }
            ViewGroup indexLayout = (ViewGroup) legislatorsByStateView.findViewById(R.id.alphabetical_index);
            TextView textView;
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (String index : indexList) {
                textView = (TextView) inflater.inflate(R.layout.alphabetical_index, null);
                textView.setText(index);
                textView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TextView selectedIndex = (TextView) v;
                        legislatorList.setSelection(mapIndex.get(selectedIndex.getText()));
                    }
                });
                indexLayout.addView(textView);
            }
        }
    }

    public static class TabFragment2 extends Fragment {
        private LegislatorAdapter listAdapter ;
        private ListView legislatorList;
        Map<String, Integer> mapIndex;
        View legislatorsByHouseView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            legislatorsByHouseView = inflater.inflate(R.layout.legislators_by_house, container, false);
            legislatorList  = (ListView) legislatorsByHouseView.findViewById(R.id.legislatorsHouse);
            listAdapter = new LegislatorAdapter(getActivity(), GlobalData.getNames(GlobalData.legislatorsByHouse),
                    GlobalData.getImgUrls(GlobalData.legislatorsByHouse), GlobalData.getLabels(GlobalData.legislatorsByHouse), 1);
            legislatorList.setAdapter(listAdapter);
            generateIndexList(GlobalData.getLastNames(GlobalData.legislatorsByHouse), legislatorsByHouseView);
            return legislatorsByHouseView;
        }
        private void generateIndexList(String[] names, View view) {
            mapIndex = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                String index = name.substring(0, 1);
                if (mapIndex.get(index) == null) {
                    mapIndex.put(index, i);
                }
            }
            ViewGroup indexLayout = (ViewGroup) legislatorsByHouseView.findViewById(R.id.alphabetical_index_house);
            TextView textView;
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (String index : indexList) {
                textView = (TextView) inflater.inflate(R.layout.alphabetical_index, null);
                textView.setText(index);
                textView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TextView selectedIndex = (TextView) v;
                        legislatorList.setSelection(mapIndex.get(selectedIndex.getText()));
                    }
                });
                indexLayout.addView(textView);
            }
        }
    }

    public static class TabFragment3 extends Fragment {
        private LegislatorAdapter listAdapter ;
        private ListView legislatorList;
        Map<String, Integer> mapIndex;
        View legislatorsBySenateView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            legislatorsBySenateView = inflater.inflate(R.layout.legislators_by_senate, container, false);
            legislatorList  = (ListView) legislatorsBySenateView.findViewById(R.id.legislatorsSenate);
            listAdapter = new LegislatorAdapter(getActivity(), GlobalData.getNames(GlobalData.legislatorsBySenate),
                    GlobalData.getImgUrls(GlobalData.legislatorsBySenate), GlobalData.getLabels(GlobalData.legislatorsBySenate), 2);
            legislatorList.setAdapter(listAdapter);
            generateIndexList(GlobalData.getLastNames(GlobalData.legislatorsBySenate), legislatorsBySenateView);
            return legislatorsBySenateView;
        }
        private void generateIndexList(String[] names, View view) {
            mapIndex = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                String index = name.substring(0, 1);
                if (mapIndex.get(index) == null) {
                    mapIndex.put(index, i);
                }
            }
            ViewGroup indexLayout = (ViewGroup) legislatorsBySenateView.findViewById(R.id.alphabetical_index_senate);
            TextView textView;
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (String index : indexList) {
                textView = (TextView) inflater.inflate(R.layout.alphabetical_index, null);
                textView.setText(index);
                textView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TextView selectedIndex = (TextView) v;
                        legislatorList.setSelection(mapIndex.get(selectedIndex.getText()));
                    }
                });
                indexLayout.addView(textView);
            }
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

