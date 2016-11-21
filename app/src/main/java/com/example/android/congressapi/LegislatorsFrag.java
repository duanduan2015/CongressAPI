package com.example.android.congressapi;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by duanduan on 11/20/16.
 */
public class LegislatorsFrag extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Legislators");
        return inflater.inflate(
                R.layout.legislators, container, false);
    }
    /*public void updateLegislators() {
        getActivity().setTitle("Legislators");
    }*/
}

