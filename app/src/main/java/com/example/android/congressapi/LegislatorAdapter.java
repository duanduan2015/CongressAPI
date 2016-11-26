package com.example.android.congressapi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
public class LegislatorAdapter extends BaseAdapter{
    String [] texts;
    Context context;
    String[] src;
    String[] labels;
    private static LayoutInflater inflater=null;
    Activity main;
    public LegislatorAdapter(Activity mainActivity, String[] textsList, String[] imageUrls, String[] labels) {
        // TODO Auto-generated constructor stub
        main = mainActivity;
        texts = textsList;
        context = mainActivity;
        this.src = imageUrls;
        this.labels = labels;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return texts.length;
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
        TextView name;
        TextView label;
        NetworkImageView image;
        ImageButton button;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        ImageLoader mImageLoader;
        rowView = inflater.inflate(R.layout.legislators_item, null);
        holder.name = (TextView) rowView.findViewById(R.id.rowTextViewName);
        holder.label = (TextView) rowView.findViewById(R.id.rowTextViewLabel);

        holder.image = (NetworkImageView) rowView.findViewById(R.id.imageView1);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.image.setImageUrl(src[position], mImageLoader);

        holder.name.setText(texts[position]);
        holder.name.setTextColor(Color.BLACK);
        holder.name.setTextSize(17);
        holder.name.setTypeface(null, Typeface.BOLD);

        holder.label.setText(labels[position]);
        holder.label.setTextColor(Color.GRAY);
        holder.label.setTextSize(15);

        holder.button = (ImageButton) rowView.findViewById(R.id.details_button);
        holder.button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GlobalData.legislatorIndex = position;
                Intent intent = new Intent(main, DisplayLegislatorsDetails.class);
                main.startActivity(intent);
            }
        });

        return rowView;
    }

}
