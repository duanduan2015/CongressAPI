package com.example.android.congressapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duanduan on 11/27/16.
 */
public class Committee {
    public String committee_id;
    public String committee_name;
    public String chamber;
    public String parent_committee_id;
    public String office;
    public String phone;
    public boolean favorite = false;
    public JSONObject jsonData;
    public Committee(JSONObject bill) {
        jsonData = bill;
    }
    public void populateCommittee() {
        try {

                committee_id = jsonData.getString("committee_id").toUpperCase();
                committee_name = jsonData.getString("name");
                chamber = jsonData.getString("chamber");
                chamber = chamber.substring(0,1).toUpperCase() + chamber.substring(1);
                if (!jsonData.has("parent_committee_id") || jsonData.isNull("parent_committee_id")) {
                    parent_committee_id = "None";
                } else {
                    parent_committee_id = jsonData.getString("parent_committee_id");
                }
                if (!jsonData.has("office") || jsonData.isNull("office")) {
                    office = "None";
                } else {
                    office = jsonData.getString("office");
                }
                if (!jsonData.has("phone") || jsonData.isNull("phone")) {
                    phone = "None";
                } else {
                    phone = jsonData.getString("phone");
                }
        } catch (JSONException e) {
                Log.e("error", e.toString());
        }
    }
}
