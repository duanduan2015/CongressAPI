package com.example.android.congressapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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

        Intent intent = getIntent();
        Legislator legislator = GlobalData.legislators[GlobalData.legislatorIndex];
        ImageLoader mImageLoader;
        NetworkImageView image = (NetworkImageView) findViewById(R.id.imageInDetails);
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        image.setImageUrl(legislator.imgUrl, mImageLoader);
        ViewGroup party = (ViewGroup) findViewById(R.id.party);
        ImageView partyLogo = (ImageView) party.findViewById(R.id.partyLogo);
        TextView partyName = (TextView) party.findViewById(R.id.partyName);
        if (legislator.party.equals("R")) {
            partyLogo.setImageResource(R.drawable.republican);
            partyName.setText("Republican");
        } else if (legislator.party.equals("D")) {
            partyLogo.setImageResource(R.drawable.democrat);
            partyName.setText("Democrat");
        }
        View table = findViewById(R.id.legislatorTable);
        addTableRows(table, legislator);
    }
    private void addTableRows(View t, Legislator l) {
        TextView nameRow = (TextView) t.findViewById(R.id.legislatorTable_name);
        String name = l.title + ". " + l.last_name + ", " + l.first_name;
        nameRow.setText(name);
        TextView emailRow = (TextView) t.findViewById(R.id.legislatorTable_email);
        String email = l.oc_email;
        emailRow.setText(email);
        TextView chamberRow = (TextView) t.findViewById(R.id.legislatorTable_chamber);
        String chamber = l.chamber.substring(0,1).toUpperCase() + l.chamber.substring(1).toLowerCase();
        chamberRow.setText(chamber);
        TextView contactRow = (TextView) t.findViewById(R.id.legislatorTable_contact);
        String contact = l.phone;
        contactRow.setText(contact);
        TextView startTermRow = (TextView) t.findViewById(R.id.legislatorTable_start_term);
        String start_term = l.term_start;
        TextView endTermRow = (TextView) t.findViewById(R.id.legislatorTable_end_term);
        String end_term = l.term_end;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date startTermDate = new Date();
        Date endTermDate = new Date();
        Date birthday = new Date();
        try {
            startTermDate = dateFormat.parse(l.term_start);
            endTermDate = dateFormat.parse(l.term_end);
            birthday = dateFormat.parse(l.birthday);
        } catch (Exception e) {

        }
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        start_term = format.format(startTermDate);
        end_term = format.format(endTermDate);
        startTermRow.setText(start_term);
        endTermRow.setText(end_term);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.legislatorTable_progressbar);
        long progress = l.progress;
        progressBar.setProgress((int)progress);
        TextView progress_percent = (TextView) findViewById(R.id.legislatorTable_progressbar_percent);
        progress_percent.setText(Integer.toString((int)progress) + "%");
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
