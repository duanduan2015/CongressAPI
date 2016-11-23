package com.example.android.congressapi;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duanduan on 11/22/16.
 */
public class GlobalData {
    public static JSONObject legislatorsJason;
    public static Legislator[] legislators;
    public static String[] imgUrls;
    public static String[] names;
    public static String[] labels;
    public static void getLegislators() {
        try {
            JSONArray results = legislatorsJason.getJSONArray("results");
            int num = results.length();
            legislators = new Legislator[num];
            for (int i = 0; i < num; i++) {
                legislators[i] = new Legislator(results.getJSONObject(i));
                legislators[i].populateLegislator();
            }
        } catch (final JSONException e) {
            Log.i("error", e.toString());
        }
    }
    public static void getLabels() {
        int num = legislators.length;
        labels = new String[num];
        for (int i = 0; i < num; i++) {
            labels[i] = legislators[i].label;
        }
    }
    public static void getImgUrls() {
        int num = legislators.length;
        imgUrls = new String[num];
        for (int i = 0; i < num; i++) {
            imgUrls[i] = legislators[i].imgUrl;
        }
    }
    public static void getNames() {
        int num = legislators.length;
        names = new String[num];
        for (int i = 0; i < num; i++) {
            names[i] = legislators[i].name;
        }
    }
}
