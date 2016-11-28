package com.example.android.congressapi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.FitWindowsLinearLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class DisplayBillsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bills_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Bills Info");
        //Intent intent = getIntent();
        new PopulateTable().execute();
        //addTableRows();
    }
    private class PopulateTable extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String[] views) {
            long totalSize = 0;
            addTableRows();
            return totalSize;
        }
    }

    private void addTableRows() {
        runOnUiThread(new Runnable(){
            public void run() {
                if (GlobalData.isActiveBills) {
                    GlobalData.billForDetails = GlobalData.activeBills[GlobalData.billIndex];
                } else if (GlobalData.isNewBills) {
                    GlobalData.billForDetails = GlobalData.newBills[GlobalData.billIndex];
                }
                Bill b = GlobalData.billForDetails;

                TextView bill_id = (TextView) findViewById(R.id.billsTable_id);
                bill_id.setText(b.bill_id);
                TextView bill_title = (TextView) findViewById(R.id.billsTable_title);
                bill_title.setText(b.official_title);
                TextView bill_type = (TextView) findViewById(R.id.billsTable_type);
                bill_type.setText(b.bill_type);
                TextView sponsor = (TextView) findViewById(R.id.billsTable_sponsor);
                sponsor.setText(b.sponsorName);
                TextView chamber = (TextView) findViewById(R.id.billsTable_chamber);
                chamber.setText(b.chamber);
                TextView status = (TextView) findViewById(R.id.billsTable_status);
                status.setText(b.status);
                TextView introduced = (TextView) findViewById(R.id.billsTable_introduced);
                introduced.setText(b.introduced_on_date);
                TextView congress = (TextView) findViewById(R.id.billsTable_congress);
                congress.setText(b.congress);
                TextView version = (TextView) findViewById(R.id.billsTable_version_status);
                version.setText(b.version_name);
                TextView pdf = (TextView) findViewById(R.id.billsTable_pdf);
                pdf.setText(b.pdf);
                ImageButton favorite = (ImageButton) findViewById(R.id.favorite_bills);
                if (b.favorite) {
                    favorite.setBackgroundResource(R.drawable.star_pressed);
                } else {
                    favorite.setBackgroundResource(R.drawable.star_default);
                }
                favorite.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isFavorite = GlobalData.billForDetails.favorite;
                        if (isFavorite) {
                            GlobalData.billForDetails.favorite = false;
                            v.setBackgroundResource(R.drawable.star_default);
                        } else {
                            GlobalData.billForDetails.favorite = true;
                            v.setBackgroundResource(R.drawable.star_pressed);
                        }
                    }
                });
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        super.onBackPressed();
        return true;
    }
}
