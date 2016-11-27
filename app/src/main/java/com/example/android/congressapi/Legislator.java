package com.example.android.congressapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by duanduan on 11/22/16.
 */
public class Legislator {
    public JSONObject jsonData;
     public String bioguide_id;
     public String govtrack_id;
     public String first_name;
     public String last_name;
     public String birthday;
     public String term_start;
     public String term_end;
     public String state;
     public String state_name;
     public String party;
     public String title;
     public String chamber;
     public String phone;
     public String fax;
     public String website;
     public String office;
     public String district;
     public String oc_email;
     public String twitter_id;
     public String facebook_id;
     public String imgUrl = "https://theunitedstates.io/images/congress/original/";
     public String name;
     public String label;
     public boolean favorite = false;
    public long progress;
    public Legislator(JSONObject singleLegislator) {
        jsonData = singleLegislator;
    }
    public void populateLegislator() {
        try {
            bioguide_id = jsonData.getString("bioguide_id");
            govtrack_id = jsonData.getString("govtrack_id");
            first_name = jsonData.getString("first_name");
            last_name = jsonData.getString("last_name");
            birthday = jsonData.getString("birthday");
            term_start = jsonData.getString("term_start");
            term_end = jsonData.getString("term_end");
            state = jsonData.getString("state");
            state_name = jsonData.getString("state_name");
            party = jsonData.getString("party");
            title = jsonData.getString("title");
            chamber = jsonData.getString("chamber");
            phone = jsonData.getString("phone");
            fax = jsonData.getString("fax");
            website = jsonData.getString("website");
            office = jsonData.getString("office");
            district = jsonData.getString("district");
            oc_email = jsonData.getString("oc_email");
            twitter_id = jsonData.getString("twitter_id");
            facebook_id = jsonData.getString("facebook_id");
        } catch (JSONException e) {
            Log.e("error", e.toString());
        }
        imgUrl = imgUrl + bioguide_id + ".jpg";
        name = title + ". " + last_name + ", " + first_name;
        label = "(" + party + ")" + state_name + " - " + "District" + " " + district;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date startTermDate = new Date();
        Date endTermDate = new Date();
        Calendar c = Calendar.getInstance();
        long seconds = c.getTimeInMillis();
        try {
            startTermDate = dateFormat.parse(term_start);
            endTermDate = dateFormat.parse(term_end);
        } catch (Exception e) {

        }
        progress = (seconds - startTermDate.getTime()) * 100 / (endTermDate.getTime() - startTermDate.getTime());
    }
}
