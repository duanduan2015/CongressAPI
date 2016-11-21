package com.example.android.congressapi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by duanduan on 11/20/16.
 */
public class LegislatorsFrag extends Fragment {
    private ArrayAdapter<String> listAdapter ;
    private String[] mPlanetTitles = new String[]{"a", "b", "c", "d", "e"};
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Legislators");
        View view = inflater.inflate(R.layout.legislators, container, false);
        ListView mDrawerList  = (ListView) view.findViewById(R.id.legislatorsList);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars","Jupiter", "Saturn", "Uranus", "Neptune"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.legislators_item, planetList);
        listAdapter.add( "Ceres" );
        listAdapter.add( "Pluto" );
        listAdapter.add( "Haumea" );
        listAdapter.add( "Makemake" );
        listAdapter.add( "Eris" );
        mDrawerList.setAdapter(listAdapter);
        return view;
    }
    /*public void updateLegislators() {
        getActivity().setTitle("Legislators");
    }*/

}

