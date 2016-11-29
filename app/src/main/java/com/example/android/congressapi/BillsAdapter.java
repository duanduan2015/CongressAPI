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
public class BillsAdapter extends BaseAdapter{
    String [] id;
    Context context;
    String[] title;
    String[] date;
    private static LayoutInflater inflater=null;
    Activity main;
    int index = 0;
    public BillsAdapter(Activity mainActivity, String[] bill_id, String[] bill_title, String[] bill_date, int index) {
        // TODO Auto-generated constructor stub
        this.index = index;
        main = mainActivity;
        id = bill_id;
        title = bill_title;
        date = bill_date;
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
        TextView title;
        TextView date;
        ImageButton button;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.bills_item, null);
        holder.id = (TextView) rowView.findViewById(R.id.bill_label_id);
        holder.title = (TextView) rowView.findViewById(R.id.bills_label_title);
        holder.date = (TextView) rowView.findViewById(R.id.bills_label_date);

        holder.id.setText(id[position]);
        holder.id.setTextColor(Color.BLACK);
        holder.id.setTextSize(17);
        holder.id.setTypeface(null, Typeface.BOLD);

        holder.title.setText(title[position]);
        holder.title.setTextColor(Color.DKGRAY);
        holder.title.setTextSize(14);

        holder.date.setText(date[position]);
        holder.date.setTextColor(Color.DKGRAY);
        holder.date.setTextSize(14);

        holder.button = (ImageButton) rowView.findViewById(R.id.bills_details_button);

        rowView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GlobalData.billIndex = position;
                if (index == 0) {
                    GlobalData.isActiveBills = true;
                    GlobalData.isNewBills = false;
                    GlobalData.isFavoriteBills = false;
                } else if (index == 1) {
                    GlobalData.isNewBills = true;
                    GlobalData.isActiveBills = false;
                    GlobalData.isFavoriteBills = false;
                } else if (index == 2) {
                    GlobalData.isNewBills = false;
                    GlobalData.isActiveBills = false;
                    GlobalData.isFavoriteBills = true;
                }
                Intent intent = new Intent(main, DisplayBillsDetails.class);
                main.startActivity(intent);
            }
        });

        return rowView;
    }

}
