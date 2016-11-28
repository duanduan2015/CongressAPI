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

import javax.microedition.khronos.opengles.GL;


public class DisplayCommitteesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_committees_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Committees Info");
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
                if (GlobalData.isHouseCommittees) {
                    GlobalData.committeeForDetails = GlobalData.houseCommittees[GlobalData.committeeIndex];
                } else if (GlobalData.isSenateCommittees) {
                    GlobalData.committeeForDetails = GlobalData.senateCommittees[GlobalData.committeeIndex];
                } else if (GlobalData.isJointCommittees) {
                    GlobalData.committeeForDetails = GlobalData.jointCommittees[GlobalData.committeeIndex];
                } else if (GlobalData.isFavoriteCommittees) {
                    GlobalData.committeeForDetails = GlobalData.favoriteCommittees[GlobalData.committeeIndex];
                }

                Committee c = GlobalData.committeeForDetails;

                TextView committee_id = (TextView) findViewById(R.id.committeesTable_id);
                committee_id.setText(c.committee_id);
                TextView name = (TextView) findViewById(R.id.committeesTable_name);
                name.setText(c.committee_name);
                ImageView logo = (ImageView) findViewById(R.id.committeesTable_chamberLogo);
                TextView chamber = (TextView) findViewById(R.id.committeesTable_chamber);
                if (c.chamber.equals("House")) {
                    logo.setBackgroundResource(R.mipmap.house);
                    chamber.setText("House");
                } else {
                    logo.setImageResource(R.drawable.senate);
                    chamber.setText("Senate");
                }
                TextView parent = (TextView) findViewById(R.id.committeesTable_parent);
                parent.setText(c.parent_committee_id);
                TextView contact = (TextView) findViewById(R.id.committeesTable_contact);
                contact.setText(c.phone);
                TextView office = (TextView) findViewById(R.id.committeesTable_office);
                office.setText(c.office);
                ImageButton favorite = (ImageButton) findViewById(R.id.favorite_committees);
                if (c.favorite) {
                    favorite.setBackgroundResource(R.drawable.star_pressed);
                } else {
                    favorite.setBackgroundResource(R.drawable.star_default);
                }
                favorite.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isFavorite = GlobalData.committeeForDetails.favorite;
                        if (isFavorite) {
                            GlobalData.committeeForDetails.favorite = false;
                            v.setBackgroundResource(R.drawable.star_default);
                        } else {
                            GlobalData.committeeForDetails.favorite = true;
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
