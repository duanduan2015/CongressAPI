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
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class DisplayLegislatorsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_legislators_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Legislator Info");
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
                if (GlobalData.byStates) {
                    GlobalData.legislatorForDetails = GlobalData.legislators[GlobalData.legislatorIndex];
                } else if (GlobalData.byHouse) {
                    GlobalData.legislatorForDetails = GlobalData.legislatorsByHouse[GlobalData.legislatorIndex];
                } else if (GlobalData.bySenate) {
                    GlobalData.legislatorForDetails =  GlobalData.legislatorsBySenate[GlobalData.legislatorIndex];
                } else if (GlobalData.byFavorite) {
                    GlobalData.legislatorForDetails =  GlobalData.legislatorsByFavorite[GlobalData.legislatorIndex];
                }
                Legislator l = GlobalData.legislatorForDetails;
                ViewGroup content_view = (ViewGroup) getLayoutInflater().inflate(R.layout.legislator_details_content, null);
                ViewGroup views = (ViewGroup) findViewById(R.id.display_legislators_details);
                NetworkImageView image = (NetworkImageView) content_view.findViewById(R.id.imageInDetails);
                ImageLoader mImageLoader;
                mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
                image.setImageUrl(l.imgUrl, mImageLoader);

                TextView partyName = (TextView) content_view.findViewById(R.id.partyName);
                ImageView partyLogo = (ImageView) content_view.findViewById(R.id.partyLogo);
                if (l.party.equals("R")) {
                    partyLogo.setImageResource(R.drawable.republican);
                    partyName.setText("Republican");
                } else if (l.party.equals("D")) {
                    partyLogo.setImageResource(R.drawable.democrat);
                    partyName.setText("Democrat");
                }
                //If there are stories, add them to the table
                TextView nameRow = (TextView) content_view.findViewById(R.id.legislatorTable_name);
                String name = l.title + ". " + l.last_name + ", " + l.first_name;
                nameRow.setText(name);
                TextView emailRow = (TextView) content_view.findViewById(R.id.legislatorTable_email);
                String email = l.oc_email;
                emailRow.setText(email);
                TextView chamberRow = (TextView) content_view.findViewById(R.id.legislatorTable_chamber);
                String chamber = l.chamber.substring(0,1).toUpperCase() + l.chamber.substring(1).toLowerCase();
                chamberRow.setText(chamber);
                TextView contactRow = (TextView) content_view.findViewById(R.id.legislatorTable_contact);
                String contact = l.phone;
                contactRow.setText(contact);
                TextView startTermRow = (TextView) content_view.findViewById(R.id.legislatorTable_start_term);
                String start_term = l.term_start;
                TextView endTermRow = (TextView) content_view.findViewById(R.id.legislatorTable_end_term);
                String end_term = l.term_end;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startTermDate = new Date();
                Date endTermDate = new Date();
                Date birthdayDate = new Date();
                try {
                    startTermDate = dateFormat.parse(l.term_start);
                    endTermDate = dateFormat.parse(l.term_end);
                    birthdayDate = dateFormat.parse(l.birthday);
                } catch (Exception e) {

                }
                DateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                start_term = format.format(startTermDate);
                end_term = format.format(endTermDate);
                startTermRow.setText(start_term);
                endTermRow.setText(end_term);
                ProgressBar progressBar = (ProgressBar) content_view.findViewById(R.id.legislatorTable_progressbar);
                long progress = l.progress;
                progressBar.setProgress((int)progress);
                TextView progress_percent = (TextView) content_view.findViewById(R.id.legislatorTable_progressbar_percent);
                progress_percent.setText(Integer.toString((int)progress) + "%");
                TextView office = (TextView) content_view.findViewById(R.id.legislatorTable_office);
                office.setText(l.office);
                TextView state = (TextView) content_view.findViewById(R.id.legislatorTable_state);
                state.setText(l.state);
                TextView fax = (TextView) content_view.findViewById(R.id.legislatorTable_fax);
                fax.setText(l.fax);
                TextView birthday = (TextView) content_view.findViewById(R.id.legislatorTable_birthday);
                birthday.setText(format.format(birthdayDate));
                ImageButton favorite = (ImageButton) content_view.findViewById(R.id.favorite);
                if (l.favorite) {
                    favorite.setBackgroundResource(R.drawable.star_pressed);
                } else {
                    favorite.setBackgroundResource(R.drawable.star_default);
                }
                favorite.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isFavorite = GlobalData.legislatorForDetails.favorite;
                        if (isFavorite) {
                            GlobalData.legislatorForDetails.favorite = false;
                            v.setBackgroundResource(R.drawable.star_default);
                        } else {
                            GlobalData.legislatorForDetails.favorite = true;
                            v.setBackgroundResource(R.drawable.star_pressed);
                        }
                    }
                });
                ImageView facebook = (ImageView) content_view.findViewById(R.id.facebook);
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (GlobalData.legislatorForDetails.facebook_id.equals("None")) {
                            String message = "Sorry, this legislator does not have a facebook account";
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            String url = "https://m.facebook.com/" + GlobalData.legislatorForDetails.facebook_id;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                });
                ImageView twitter = (ImageView) content_view.findViewById(R.id.twitter);
                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (GlobalData.legislatorForDetails.twitter_id.equals("None")) {
                            String message = "Sorry, this legislator does not have a twitter account";
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            String url = "https://mobile.twitter.com/" + GlobalData.legislatorForDetails.twitter_id;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                });
                ImageView world = (ImageView) content_view.findViewById(R.id.world);
                world.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (GlobalData.legislatorForDetails.website.equals("None")) {
                            String message = "Sorry, this legislator does not have a website";
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            String url = GlobalData.legislatorForDetails.website;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                });
                views.addView(content_view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        super.onBackPressed();
        return true;
    }
}
