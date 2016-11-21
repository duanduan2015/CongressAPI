package com.example.android.congressapi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by duanduan on 11/20/16.
 */
public class FavoritesFrag extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Favorites");
        return inflater.inflate(
                R.layout.favorites, container, false);
    }
}
