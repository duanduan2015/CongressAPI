package com.example.android.congressapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by duanduan on 11/27/16.
 */
public class Bill {
    public String bill_id;
    public String official_title;
    public String introduced_on;
    public String short_title;
    public String sponsor_last_name;
    public String sponsor_first_name;
    public String sponsor_title;
    public String bill_type;
    public String chamber;
    public String status;
    public String congress;
    public String version_name;
    public String pdf;
    public String label_title;
    public String introduced_on_date;
    public String sponsorName;
    public Date introduced_date;
    public boolean favorite = false;
    public JSONObject jsonData;
    public Bill(JSONObject bill) {
        jsonData = bill;
    }
    public void populateBill() {
        try {

            bill_id = jsonData.getString("bill_id").toUpperCase();
            official_title = jsonData.getString("official_title");
            introduced_on = jsonData.getString("introduced_on");
            short_title = jsonData.getString("short_title");
            sponsor_first_name = jsonData.getJSONObject("sponsor").getString("first_name");
            sponsor_last_name = jsonData.getJSONObject("sponsor").getString("last_name");
            sponsor_title = jsonData.getJSONObject("sponsor").getString("title");
            bill_type = jsonData.getString("bill_type").toUpperCase();
            chamber = jsonData.getString("chamber");
            chamber = chamber.substring(0,1).toUpperCase() + chamber.substring(1);
            boolean active = jsonData.getJSONObject("history").getBoolean("active");
            if (active) {
                status = "Active";
            } else {
                status = "New";
            }
            congress = jsonData.getJSONObject("urls").getString("congress");
            version_name = jsonData.getJSONObject("last_version").getString("version_name");
            pdf = jsonData.getJSONObject("last_version").getJSONObject("urls").getString("pdf");
        } catch (JSONException e) {
            Log.e("error", e.toString());
        }
        if (short_title.equals("null")) {
            label_title = official_title;
            //Log.i("official_title", label_title);
        } else {
            label_title = short_title;
            //Log.i("short_title", label_title);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(introduced_on);
            introduced_date = date;
        } catch (Exception e) {
        }
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        introduced_on_date = format.format(date);
        //Log.i("json", introduced_on);
        //Log.i("date", introduced_on_date);
        sponsorName = sponsor_title + ". " + sponsor_last_name + ", " + sponsor_first_name;
    }
}
