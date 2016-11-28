package com.example.android.congressapi;

/**
 * Created by duanduan on 11/20/16.
 */
//import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BillsFrag extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Bills");
        View view = inflater.inflate(R.layout.bills, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_bills);
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVE BILLS"));
        tabLayout.addTab(tabLayout.newTab().setText("NEW BILLS"));
        tabLayout.setTabTextColors(Color.LTGRAY, Color.DKGRAY);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_bills);
        final PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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
        private BillsAdapter listAdapter ;
        private ListView billsList;
        View activeBillsView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            activeBillsView = inflater.inflate(R.layout.bills_active, container, false);
            billsList  = (ListView) activeBillsView.findViewById(R.id.activeBills);
            listAdapter = new BillsAdapter(getActivity(), GlobalData.getBillsId(GlobalData.activeBills),
                    GlobalData.getBillsTitle(GlobalData.activeBills), GlobalData.getBillsDate(GlobalData.activeBills), 0);
            billsList.setAdapter(listAdapter);
            return activeBillsView;
        }
    }

    public static class TabFragment2 extends Fragment {
        private BillsAdapter listAdapter ;
        private ListView billsList;
        View newBillsView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            newBillsView = inflater.inflate(R.layout.bills_new, container, false);
            billsList  = (ListView) newBillsView.findViewById(R.id.newBills);
            listAdapter = new BillsAdapter(getActivity(), GlobalData.getBillsId(GlobalData.newBills),
                    GlobalData.getBillsTitle(GlobalData.newBills), GlobalData.getBillsDate(GlobalData.newBills), 1);
            billsList.setAdapter(listAdapter);
            return newBillsView;
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
