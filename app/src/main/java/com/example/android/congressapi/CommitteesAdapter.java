package com.example.android.congressapi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.URL;

/**
 * Created by duanduan on 11/21/16.
 */
public class CommitteesAdapter extends BaseAdapter{
    String [] id;
    Context context;
    String[] name;
    String[] chamber;
    private static LayoutInflater inflater=null;
    Activity main;
    int index = 0;
    public CommitteesAdapter(Activity mainActivity, String[] committee_id, String[] committee_name, String[] committee_chamber, int index) {
        // TODO Auto-generated constructor stub
        this.index = index;
        main = mainActivity;
        id = committee_id;
        name = committee_name;
        chamber = committee_chamber;
        context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return id.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView id;
        TextView name;
        TextView chamber;
        ImageButton button;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.committees_item, null);
        holder.id = (TextView) rowView.findViewById(R.id.committees_label_id);
        holder.name = (TextView) rowView.findViewById(R.id.committees_label_name);
        holder.chamber = (TextView) rowView.findViewById(R.id.committees_label_chamber);

        holder.id.setText(id[position]);
        holder.id.setTextColor(Color.BLACK);
        holder.id.setTextSize(17);
        holder.id.setTypeface(null, Typeface.BOLD);

        holder.name.setText(name[position]);
        holder.name.setTextColor(Color.GRAY);
        holder.name.setTextSize(13);

        holder.chamber.setText(chamber[position]);
        holder.chamber.setTextColor(Color.GRAY);
        holder.chamber.setTextSize(13);

        holder.button = (ImageButton) rowView.findViewById(R.id.committees_details_button);

        holder.button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GlobalData.committeeIndex = position;
                if (index == 0) {
                    GlobalData.isHouseCommittees = true;
                    GlobalData.isJointCommittees = false;
                    GlobalData.isSenateCommittees = false;
                    GlobalData.isFavoriteCommittees = false;
                } else if (index == 1) {
                    GlobalData.isSenateCommittees = true;
                    GlobalData.isHouseCommittees = false;
                    GlobalData.isJointCommittees = false;
                    GlobalData.isFavoriteCommittees = false;
                } else if (index == 2) {
                    GlobalData.isJointCommittees = true;
                    GlobalData.isHouseCommittees = false;
                    GlobalData.isSenateCommittees = false;
                    GlobalData.isFavoriteCommittees = false;
                } else if (index == 3) {
                    GlobalData.isJointCommittees = false;
                    GlobalData.isHouseCommittees = false;
                    GlobalData.isSenateCommittees = false;
                    GlobalData.isFavoriteCommittees = true;
                }
                Intent intent = new Intent(main, DisplayCommitteesDetails.class);
                main.startActivity(intent);
            }
        });

        return rowView;
    }

}
