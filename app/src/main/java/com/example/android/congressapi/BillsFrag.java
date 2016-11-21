package com.example.android.congressapi;

/**
 * Created by duanduan on 11/20/16.
 */
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BillsFrag extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Bills");
        return inflater.inflate(
                R.layout.bills, container, false);
    }
}
