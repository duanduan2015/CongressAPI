package com.example.android.congressapi;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by duanduan on 11/22/16.
 */
public class GlobalData {
    public static JSONObject legislatorsJason;
    public static Legislator[] legislators;
    public static Legislator[] legislatorsByHouse;
    public static Legislator[] legislatorsBySenate;
    public static Legislator legislatorForDetails;
    public static int legislatorIndex;
    public static boolean byStates;
    public static boolean byHouse;
    public static boolean bySenate;

    public static JSONObject activeBillsJason;
    public static JSONObject newBillsJason;
    public static int billIndex;
    public static Bill[] activeBills;
    public static Bill[] newBills;
    public static Bill billForDetails;
    public static boolean isActiveBills;
    public static boolean isNewBills;

    public static void getActiveBills() {
        try {
            JSONArray results = activeBillsJason.getJSONArray("results");
            int num = results.length();
            activeBills = new Bill[num];
            for (int i = 0; i < num; i++) {
                activeBills[i] = new Bill(results.getJSONObject(i));
                activeBills[i].populateBill();
            }
        } catch (final JSONException e) {
            Log.i("error", e.toString());
        }
        Arrays.sort(activeBills, new Comparator<Bill>() {
            @Override
            public int compare(Bill lhs, Bill rhs) {
                return rhs.introduced_date.compareTo(lhs.introduced_date);
            }
        });
    }
    public static void getNewBills() {
        try {
            JSONArray results = newBillsJason.getJSONArray("results");
            int num = results.length();
            newBills = new Bill[num];
            for (int i = 0; i < num; i++) {
                newBills[i] = new Bill(results.getJSONObject(i));
                newBills[i].populateBill();
            }
        } catch (final JSONException e) {
            Log.i("error", e.toString());
        }
        Arrays.sort(newBills, new Comparator<Bill>() {
            @Override
            public int compare(Bill lhs, Bill rhs) {
                return rhs.introduced_date.compareTo(lhs.introduced_date);
            }
        });
    }
    public static String[] getBillsId(Bill[] bills) {
        int num = bills.length;
        String[] ids = new String[num];
        for (int i = 0; i < num; i++) {
            ids[i] = bills[i].bill_id;
        }
        return ids;
    }
    public static String[] getBillsTitle(Bill[] bills) {
        int num = bills.length;
        String[] titles = new String[num];
        for (int i = 0; i < num; i++) {
            titles[i] = bills[i].label_title;
        }
        return titles;
    }
    public static String[] getBillsDate(Bill[] bills) {
        int num = bills.length;
        String[] dates = new String[num];
        for (int i = 0; i < num; i++) {
            dates[i] = bills[i].introduced_on_date;
        }
        return dates;
    }
    public static void getLegislators() {
        try {
            JSONArray results = legislatorsJason.getJSONArray("results");
            int num = results.length();
            legislators = new Legislator[num];
            int indexHouse = 0;
            int indexSenate = 0;
            for (int i = 0; i < num; i++) {
                legislators[i] = new Legislator(results.getJSONObject(i));
                legislators[i].populateLegislator();
                if (legislators[i].chamber.equals("house")) {
                    indexHouse++;
                } else if (legislators[i].chamber.equals("senate")) {
                    indexSenate++;
                }
            }
            legislatorsByHouse = new Legislator[indexHouse];
            legislatorsBySenate = new Legislator[indexSenate];
            indexHouse = 0;
            indexSenate = 0;
            for (int i = 0; i < num; i++) {
                if (legislators[i].chamber.equals("house")) {
                    legislatorsByHouse[indexHouse] = legislators[i];
                    indexHouse++;
                } else if (legislators[i].chamber.equals("senate")) {
                    legislatorsBySenate[indexSenate] = legislators[i];
                    indexSenate++;
                }
            }
        } catch (final JSONException e) {
            Log.i("error", e.toString());
        }

        Arrays.sort(legislators, new Comparator<Legislator>() {
            @Override
            public int compare(Legislator lhs, Legislator rhs) {
                return lhs.state_name.compareTo(rhs.state_name);
            }
        });
        Arrays.sort(legislatorsByHouse, new Comparator<Legislator>() {
            @Override
            public int compare(Legislator lhs, Legislator rhs) {
                return lhs.last_name.compareTo(rhs.last_name);
            }
        });
        Arrays.sort(legislatorsBySenate, new Comparator<Legislator>() {
            @Override
            public int compare(Legislator lhs, Legislator rhs) {
                return lhs.last_name.compareTo(rhs.last_name);
            }
        });
    }
    public static String[] getLabels(Legislator[] legislators) {
        int num = legislators.length;
        String[] labels = new String[num];
        for (int i = 0; i < num; i++) {
            labels[i] = legislators[i].label;
        }
        return labels;
    }
    public static String[] getImgUrls(Legislator[] legislators) {
        int num = legislators.length;
        String[] imgUrls = new String[num];
        for (int i = 0; i < num; i++) {
            imgUrls[i] = legislators[i].imgUrl;
        }
        return imgUrls;
    }
    public static String[] getNames(Legislator[] legislators) {
        int num = legislators.length;
        String[] names = new String[num];
        for (int i = 0; i < num; i++) {
            names[i] = legislators[i].name;
        }
        return names;
    }
    public static String[] getLastNames(Legislator[] legislators) {
        int num = legislators.length;
        String[] names = new String[num];
        for (int i = 0; i < num; i++) {
            names[i] = legislators[i].last_name;
        }
        return names;
    }
    public static String[] getStatesName(Legislator[] legislators) {
        int num = legislators.length;
        String[] states = new String[num];
        for (int i = 0; i < num; i++) {
            states[i] = legislators[i].state_name;
        }
        return states;
    }
    public static int getLegislatorsCountByStates() {
        return legislators.length;
    }

    public static int getLegislatorsCountByHouse() {
        return legislatorsByHouse.length;
    }

    public static int getLegislatorsCountBySenate() {
        return legislatorsBySenate.length;
    }
}
