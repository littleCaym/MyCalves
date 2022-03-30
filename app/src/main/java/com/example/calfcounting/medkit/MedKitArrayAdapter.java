package com.example.calfcounting.medkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calfcounting.R;

import java.util.ArrayList;

public class MedKitArrayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Medicine> objects;

    public MedKitArrayAdapter(Context context, ArrayList<Medicine> objects) {
        this.context = context;
        this.objects = objects;
        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.ware_house_list_layout, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.textViewWareHouseListViewName);
        ImageView imageView = view.findViewById(R.id.imageViewWareHouseListView) ;


        textView.setText(objects.get(position).getName());

        switch (objects.get(position).getStatus()){
            case 0: imageView.setImageResource(R.drawable.not_at_all); break;
            case 1: imageView.setImageResource(R.drawable.not_enough); break;
            case 2: imageView.setImageDrawable(null);
                //case 0: textView.setBackgroundColor(Color.parseColor("FFF44336")); break;
                //case 1: textView.setBackgroundColor(Color.parseColor("FFFFEB3B")); break;
                //case 2: textView.setBackgroundColor(Color.parseColor("FF8BC34A")); break;
        }

        return view;
    }
}
