package com.example.android.congressapi;

/**
 * Created by duanduan on 11/20/16.
 */
//import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class CommitteesFrag extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Committees");
        View view = inflater.inflate(R.layout.committees, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_committees);
        tabLayout.addTab(tabLayout.newTab().setText("HOUSE"));
        tabLayout.addTab(tabLayout.newTab().setText("SENATE"));
        tabLayout.addTab(tabLayout.newTab().setText("JOINT"));
        tabLayout.setTabTextColors(Color.LTGRAY, Color.DKGRAY);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_committees);
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
        private CommitteesAdapter listAdapter;
        private ListView committeesList;
        View houseCommitteesView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            houseCommitteesView = inflater.inflate(R.layout.committees_house, container, false);
            committeesList  = (ListView) houseCommitteesView.findViewById(R.id.committeesByHouse);
            listAdapter = new CommitteesAdapter(getActivity(), GlobalData.getCommitteesId(GlobalData.houseCommittees),
                    GlobalData.getCommitteesName(GlobalData.houseCommittees), GlobalData.getCommitteesChamber(GlobalData.houseCommittees), 0);
            committeesList.setAdapter(listAdapter);
            return houseCommitteesView;
        }
    }

    public static class TabFragment2 extends Fragment {
        private CommitteesAdapter listAdapter;
        private ListView committeesList;
        View senateCommitteesView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            senateCommitteesView = inflater.inflate(R.layout.committees_senate, container, false);
            committeesList  = (ListView) senateCommitteesView.findViewById(R.id.committeesBySenate);
            listAdapter = new CommitteesAdapter(getActivity(), GlobalData.getCommitteesId(GlobalData.senateCommittees),
                    GlobalData.getCommitteesName(GlobalData.senateCommittees), GlobalData.getCommitteesChamber(GlobalData.senateCommittees), 1);
            committeesList.setAdapter(listAdapter);
            return senateCommitteesView;
        }
    }

    public static class TabFragment3 extends Fragment {
        private CommitteesAdapter listAdapter ;
        private ListView committeesList;
        View jointCommitteesView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            jointCommitteesView = inflater.inflate(R.layout.committees_joint, container, false);
            committeesList  = (ListView) jointCommitteesView.findViewById(R.id.committeesByJoint);
            listAdapter = new CommitteesAdapter(getActivity(), GlobalData.getCommitteesId(GlobalData.jointCommittees),
                    GlobalData.getCommitteesName(GlobalData.jointCommittees), GlobalData.getCommitteesChamber(GlobalData.jointCommittees), 2);
            committeesList.setAdapter(listAdapter);
            return jointCommitteesView;
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
