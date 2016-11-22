package com.example.android.congressapi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by duanduan on 11/21/16.
 */
public class LegislatorAdapter extends BaseAdapter{
    String [] texts;
    Context context;
    int [] src;
    private static LayoutInflater inflater=null;
    Activity main;
    public LegislatorAdapter(Activity mainActivity, String[] textsList, int[] imageSrc) {
        // TODO Auto-generated constructor stub
        main = mainActivity;
        texts = textsList;
        context = mainActivity;
        this.src = imageSrc;
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
        TextView text;
        ImageView image;
        ImageButton button;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.legislators_item, null);
        holder.text = (TextView) rowView.findViewById(R.id.rowTextView);
        holder.image = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.text.setText(texts[position]);
        holder.image.setImageResource(src[position]);
        holder.button = (ImageButton) rowView.findViewById(R.id.details_button);
        holder.button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, DisplayLegislatorsDetails.class);
                main.startActivity(intent);
            }
        });

        return rowView;
    }

}
